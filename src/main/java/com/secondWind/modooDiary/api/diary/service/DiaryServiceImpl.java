package com.secondWind.modooDiary.api.diary.service;


import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.Drawing;
import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.diary.domain.entity.link.Sticker;
import com.secondWind.modooDiary.api.diary.domain.entity.link.StickerCount;
import com.secondWind.modooDiary.api.diary.domain.request.*;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import com.secondWind.modooDiary.api.diary.repository.*;
import com.secondWind.modooDiary.api.member.auth.enumerate.PublicRegion;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.common.component.OpenWeatherMapSubscriber;
import com.secondWind.modooDiary.common.component.PublicWeatherSubscriber;
import com.secondWind.modooDiary.common.enumerate.Yn;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.DiaryErrorCode;
import com.secondWind.modooDiary.common.exception.code.MemberErrorCode;
import com.secondWind.modooDiary.common.exception.code.WeatherErrorCode;
import com.secondWind.modooDiary.common.filter.badword.BadWords;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final PublicWeatherSubscriber publicWeatherSubscriber;
    private final OpenWeatherMapSubscriber openWeatherMapSubscriber;
    private final DiaryRecommendRepository diaryRecommendRepository;
    private final StickerRepository stickerRepository;
    private final StickerCountRepository stickerCountRepository;
    private final DrawingRepository drawingRepository;

    @Override
    @Transactional
    public Page<DiaryResponse> getDiaries(SearchDiary searchDiary) {
        PageRequest pageRequest = PageRequest.of(searchDiary.getOffset(), searchDiary.getLimit(), searchDiary.getDirection(), searchDiary.getOrderBy());

        Page<DiaryResponse> diaryResponses = diaryRepository.searchDiary(searchDiary, pageRequest);
        for (DiaryResponse diaryResponse : diaryResponses) {
            List<Long> memberIds = new ArrayList<>();
            List<DiaryRecommend> diaryRecommends = diaryRecommendRepository.findByDiaryId(diaryResponse.getId());
            for (DiaryRecommend diaryRecommend : diaryRecommends) {
                memberIds.add(diaryRecommend.getMember().getId());
            }
            diaryResponse.setRecommendedMemberIds(memberIds);

            if (diaryResponse.getDrawing() != null) {
                Optional<Drawing> optionalDrawing = drawingRepository.findById(diaryResponse.getDrawing().getId());
                if (optionalDrawing.isPresent()) {
                    Drawing drawing = optionalDrawing.get();
                    diaryResponse.setDrawing(drawing);
                }

            }
        }
        return diaryResponses;
    }

    @Override
    @Transactional
    public DiaryResponseToSlack writeDiary(WriteDiaryRequest writeDiaryRequest) {
        Member member = memberRepository.findById(writeDiaryRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());


        Weather weatherStatus = getWeather(writeDiaryRequest, member);
        writeDiaryRequest.setWeather(weatherStatus.getStatusId().toString());

        if (writeDiaryRequest.getContent() != null) {
            filteringAbuseInContent(writeDiaryRequest);
        }
        Drawing drawing = null;

        if (writeDiaryRequest.getDrawing() != null) {
            drawing = drawingRepository.save(writeDiaryRequest.getDrawing());
        }
        Diary newDiaryRequest = WriteDiaryRequest.createDiary(writeDiaryRequest, member, drawing);
        Diary diary = diaryRepository.save(newDiaryRequest);

        return DiaryResponseToSlack.of()
                .diary(diary)
                .build();
    }

    private static void filteringAbuseInContent(WriteDiaryRequest writeDiaryRequest) {
        String content = writeDiaryRequest.getContent();
        String filteredContent = content;

        String[] badWords = new BadWords().badWords;

        for (String badWord : badWords) {
            if (content.contains(badWord)) {
                filteredContent  = content.replace(badWord, "**");
            }
        }

        writeDiaryRequest.setContent(filteredContent);
    }

    private Weather getWeather(WriteDiaryRequest writeDiaryRequest, Member member) {
        Weather weatherStatus = null;
        if (writeDiaryRequest.getWeather() != null && !writeDiaryRequest.getWeather().isBlank()) {
            weatherStatus = Weather.of()
                    .statusId(
                            switch (writeDiaryRequest.getWeather()) {
                                case "맑음" -> 800L;
                                case "구름 많음" -> 804L;
                                case "비" -> 501L;
                                case "눈" -> 601L;
                                default -> throw ApiException.builder()
                                        .errorCode(WeatherErrorCode.NOT_FOUND_WEATHER.getCode())
                                        .errorMessage(WeatherErrorCode.NOT_FOUND_WEATHER.getMessage())
                                        .status(HttpStatus.BAD_REQUEST)
                                        .build();})
                    .build();
        } else {
            Float nx = 0F;
            Float ny = 0F;
            if (writeDiaryRequest.getNx() == null || writeDiaryRequest.getNy() == null) {
                nx = member.getRegion().getNx();
                ny = member.getRegion().getNy();
            } else {
                nx = writeDiaryRequest.getNx();
                ny = writeDiaryRequest.getNy();
            }
            weatherStatus = openWeatherMapSubscriber.getWeatherStatus(nx, ny);
            if (weatherStatus == null) {
                PublicRegion publicRegion = PublicRegion.toPublicRegion(member);
                weatherStatus = publicWeatherSubscriber.getWeatherStatus(publicRegion);
            }
        }
        return weatherStatus;
    }

    @Override
    @Transactional
    public Long updateDiary(Long diaryId, UpdateDiaryRequest updateDiaryRequest) {

        Diary diary = findDiary(diaryId);

        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!Objects.equals(memberId, diary.getMember().getId().toString())) {
            throw ApiException.builder()
                    .errorMessage(DiaryErrorCode.NOT_AUTHORIZATION_DIARY.getMessage())
                    .errorCode(DiaryErrorCode.NOT_AUTHORIZATION_DIARY.getCode())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        diary.updateDiaryBuilder()
                .title(updateDiaryRequest.getTitle())
                .content(updateDiaryRequest.getContent())
                .build();

        if (diary.getContent().isBlank()) {
            diary.updateDiaryBuilder()
                    .content("제곧내")
                    .build();
        }
        return diary.getId();
    }

    @Override
    @Transactional
    public void deleteDiary(Long diaryId) {
        Diary diary = findDiary(diaryId);

        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!Objects.equals(memberId, diary.getMember().getId().toString())) {
            throw ApiException.builder()
                    .errorMessage(DiaryErrorCode.NOT_AUTHORIZATION_DIARY.getMessage())
                    .errorCode(DiaryErrorCode.NOT_AUTHORIZATION_DIARY.getCode())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        diary.deleteDiary();
        if (diary.getDrawing() != null) {
            Optional<Drawing> optionalDrawing = drawingRepository.findById(diary.getDrawing().getId());
            if (optionalDrawing.isPresent()) {
                Drawing drawing = optionalDrawing.get();
                drawing.deleteDrawing();
            }
        }
    }

    @Override
    public DiaryResponse getDiary(Long id) {
        return diaryRepository.getDiary(id).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(DiaryErrorCode.NOT_FOUND_DIARY.getMessage())
                        .errorCode(DiaryErrorCode.NOT_FOUND_DIARY.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }

    @Override
    @Transactional
    public void updateDiaryRecommend(DiaryRecommendRequest diaryRecommendRequest) {
        Member member = findMember(diaryRecommendRequest.getMemberId());
        Diary diary = findDiary(diaryRecommendRequest.getDiaryId());

        DiaryRecommend findDiaryRecommend = diaryRecommendRepository.findByMemberAndDiary(member, diary);

        if (findDiaryRecommend == null) {
            DiaryRecommend diaryRecommend = DiaryRecommend.createDiaryRecommendBuilder()
                    .member(member)
                    .diary(diary)
                    .build();

            diaryRecommendRepository.save(diaryRecommend);
            diary.plusRecommendCount();
        } else {
            findDiaryRecommend.changeRecommendYn(diaryRecommendRequest.getRecommendYn());
            if (Yn.Y.equals(diaryRecommendRequest.getRecommendYn())) {
                diary.plusRecommendCount();
            } else {
                diary.minusRecommendCount();
            }
        }
    }

    @Override
    @Transactional
    public void updateSticker(StickerRequest stickerRequest) {
        Member member = findMember(stickerRequest.getMemberId());
        Diary diary = findDiary(stickerRequest.getDiaryId());

        Sticker findSticker = stickerRepository.findByMemberAndDiary(member, diary);

        if (findSticker == null) {
            Sticker sticker = Sticker.createStickerBuilder()
                    .member(member)
                    .diary(diary)
                    .recommendYn(stickerRequest.getRecommendYn())
                    .unlikeYn(stickerRequest.getUnlikeYn())
                    .build();

            findSticker = stickerRepository.save(sticker);
        }

        findSticker.changeSticker(stickerRequest);

        StickerCount findStickerCount = stickerCountRepository.findByDiaryId(stickerRequest.getDiaryId());
        if (findStickerCount == null) {
            StickerCount stickerCount = StickerCount.createStickerCountBuilder()
                    .diary(diary)
                    .build();

            findStickerCount = stickerCountRepository.save(stickerCount);
        }

        findStickerCount.plusSticker(stickerRequest);
    }

    @Override
    @Transactional
    public void updateStickerV2(StickerRequestV2 stickerRequest) {
        Diary diary = findDiary(stickerRequest.getDiaryId());

        StickerCount findStickerCount = stickerCountRepository.findByDiaryId(stickerRequest.getDiaryId());
        if (findStickerCount == null) {
            StickerCount stickerCount = StickerCount.createStickerCountBuilder()
                    .diary(diary)
                    .build();

            findStickerCount = stickerCountRepository.save(stickerCount);
        }

        Integer recommend = stickerRequest.getRecommend();
        if (recommend != null && recommend > 0) {
            findStickerCount.plusRecommend(findStickerCount.getRecommendCount() + recommend);
        }
        Integer unlike = stickerRequest.getUnlike();
        if (unlike != null && unlike > 0) {
            findStickerCount.plusUnlike(findStickerCount.getUnlikeCount() + unlike);
        }
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> ApiException.builder()
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }

    private Diary findDiary(Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(DiaryErrorCode.NOT_FOUND_DIARY.getMessage())
                        .errorCode(DiaryErrorCode.NOT_FOUND_DIARY.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }
}
