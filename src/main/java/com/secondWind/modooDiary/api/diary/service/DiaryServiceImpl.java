package com.secondWind.modooDiary.api.diary.service;


import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.diary.domain.entity.link.Sticker;
import com.secondWind.modooDiary.api.diary.domain.entity.link.StickerCount;
import com.secondWind.modooDiary.api.diary.domain.request.*;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import com.secondWind.modooDiary.api.diary.repository.DiaryRecommendRepository;
import com.secondWind.modooDiary.api.diary.repository.DiaryRepository;
import com.secondWind.modooDiary.api.diary.repository.StickerCountRepository;
import com.secondWind.modooDiary.api.diary.repository.StickerRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

        Diary diary = diaryRepository.save(WriteDiaryRequest.createDiary(writeDiaryRequest, member));

        return DiaryResponseToSlack.of()
                .diaryId(diary.getMember().getId())
                .nickName(diary.getMember().getNickName())
                .title(diary.getTitle())
                .content(diary.getContent())
                .build();
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

        Member member = memberRepository.findById(updateDiaryRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

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

        diary.deleteDiary();
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
