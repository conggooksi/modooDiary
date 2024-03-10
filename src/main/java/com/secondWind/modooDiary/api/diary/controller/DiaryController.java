package com.secondWind.modooDiary.api.diary.controller;

import com.secondWind.modooDiary.api.diary.domain.request.*;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import com.secondWind.modooDiary.api.diary.service.DiaryService;
import com.secondWind.modooDiary.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "diary", description = "일기 API")
@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "일기 조회 API")
    @GetMapping("")
    public ResponseEntity<?> getDiaries(SearchDiary searchDiary) {
        Page<DiaryResponse> contents = diaryService.getDiaries(searchDiary);
        return ResponseHandler.generate()
                .data(contents)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "일기 상세 조회 API")
    @GetMapping("/{diary_id}")
    public ResponseEntity<?> getDiary(@PathVariable(value = "diary_id") Long id) {
        DiaryResponse diaryDetail = diaryService.getDiary(id);
        return ResponseHandler.generate()
                .data(diaryDetail)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "일기 작성 API")
    @PostMapping("")
    public ResponseEntity<?> writeDiary(@RequestBody WriteDiaryRequest writeDiaryRequest) {
        DiaryResponseToSlack diaryResponseToSlack = diaryService.writeDiary(writeDiaryRequest);

        return ResponseHandler.generate()
                .data(diaryResponseToSlack.getDiaryId())
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "일기 수정 API")
    @PatchMapping("/{diary_id}")
    public ResponseEntity<?> updateDiary(@PathVariable(value = "diary_id") Long diaryId, @Valid @RequestBody UpdateDiaryRequest updateDiaryRequest) {
        Long updatedDiaryId = diaryService.updateDiary(diaryId, updateDiaryRequest);

        return ResponseHandler.generate()
                .data(updatedDiaryId)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "일기 삭제 API")
    @DeleteMapping("/{diary_id}")
    public ResponseEntity<?> deleteDiary(@PathVariable(value = "diary_id") Long diaryId) {
        diaryService.deleteDiary(diaryId);

        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 추천 하나만 있을 줄 알고 만들었던 거
    @Operation(summary = "일기 좋아요 API")
    @PutMapping("/recommend")
    public ResponseEntity<?> updateDiaryRecommend(DiaryRecommendRequest diaryRecommendRequest) {
        diaryService.updateDiaryRecommend(diaryRecommendRequest);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "스티커 API")
    @PutMapping("/sticker")
    public ResponseEntity<?> updateSticker(StickerRequest stickerRequest) {
        diaryService.updateSticker(stickerRequest);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "스티커 API")
    @PutMapping("/sticker/v2")
    public ResponseEntity<?> updateStickerV2(StickerRequestV2 stickerRequest) {
        diaryService.updateStickerV2(stickerRequest);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }
}
