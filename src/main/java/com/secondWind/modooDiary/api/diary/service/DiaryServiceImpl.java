package com.secondWind.modooDiary.api.diary.service;


import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.diary.domain.request.DiaryRecommendRequest;
import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import com.secondWind.modooDiary.api.diary.domain.request.UpdateDiaryRequest;
import com.secondWind.modooDiary.api.diary.domain.request.WriteDiaryRequest;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryDetail;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import com.secondWind.modooDiary.api.diary.repository.DiaryRecommendRepository;
import com.secondWind.modooDiary.api.diary.repository.DiaryRepository;
import com.secondWind.modooDiary.api.member.auth.enumerate.OpenweatherRegion;
import com.secondWind.modooDiary.api.member.auth.enumerate.PublicRegion;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.common.component.OpenWeatherMapSubscriber;
import com.secondWind.modooDiary.common.component.PublicWeatherSubscriber;
import com.secondWind.modooDiary.common.component.SlackSender;
import com.secondWind.modooDiary.common.enumerate.Yn;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.DiaryErrorCode;
import com.secondWind.modooDiary.common.exception.code.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final SlackSender slackSender;

    @Override
    @Transactional
    public Page<DiaryResponse> getDiaries(SearchDiary searchDiary) {
        PageRequest pageRequest = PageRequest.of(searchDiary.getOffset(), searchDiary.getLimit(), searchDiary.getDirection(), searchDiary.getOrderBy());

        return diaryRepository.searchDiary(searchDiary, pageRequest);
    }

    @Override
    @Transactional
    public Long writeDiary(WriteDiaryRequest writeDiaryRequest) {
        Member member = memberRepository.findById(writeDiaryRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
        if (writeDiaryRequest.getWeather() == null) {
            Weather weatherStatus = openWeatherMapSubscriber.getWeatherStatus(member.getRegion());
            if (weatherStatus == null) {
                PublicRegion publicRegion = PublicRegion.toPublicRegion(member);
                weatherStatus = publicWeatherSubscriber.getWeatherStatus(publicRegion);
            }

            writeDiaryRequest.setWeather(weatherStatus);
        }

        Long diaryId = diaryRepository.save(WriteDiaryRequest.createDiary(writeDiaryRequest, member)).getId();

        slackSender.slackSender(member.getNickName(), writeDiaryRequest.getTitle());

        return diaryId;
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
