package com.modoodiary.modooDiary.api.diary.controller;

import com.modoodiary.modooDiary.api.diary.domain.request.SearchDiary;
import com.modoodiary.modooDiary.api.diary.domain.request.WriteDiary;
import com.modoodiary.modooDiary.api.diary.domain.response.DiaryResponse;
import com.modoodiary.modooDiary.api.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/")
    public ResponseEntity<?> getDiaries(SearchDiary searchDiary) {
        List<DiaryResponse> contents = diaryService.getDiaries(searchDiary);
        return ResponseEntity.ok(contents);
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeDiary(@RequestBody WriteDiary writeDiary) {
        diaryService.writeDiary();

        return null;
    }
}
