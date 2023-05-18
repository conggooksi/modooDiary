package com.secondWind.modooDiary.api.member.controller;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import com.secondWind.modooDiary.api.member.domain.dto.request.NickNameUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.request.RegionUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.response.MemberDetail;
import com.secondWind.modooDiary.api.member.service.MemberService;
import com.secondWind.modooDiary.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "member", description = "회원 API")
@RequestMapping("/api/members")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "회원 조회 API")
    @GetMapping("")
    public ResponseEntity<?> getMembers(MemberSearch memberSearch) {
        Page<MemberResponseDTO> members = memberService.getMembers(memberSearch);
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(members)
                .build();
    }

    @Operation(summary = "회원 상세 조회 API")
    @GetMapping("/{member_id}")
    public ResponseEntity<?> getMember(@PathVariable(value = "member_id") Long memberId) {
        MemberDetail member = memberService.getMember(memberId);
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(member)
                .build();
    }

    @Operation(summary = "닉네임 변경 API")
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickName(@RequestBody NickNameUpdateRequest nickNameUpdateRequest) {
        Long memberId = memberService.updateNickName(nickNameUpdateRequest);
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(memberId)
                .build();

    }

    @Operation(summary = "지역 변경 API")
    @PatchMapping("/region")
    public ResponseEntity<?> updateRegion(@RequestBody RegionUpdateRequest regionUpdateRequest) {
        Long memberId = memberService.updateRegion(regionUpdateRequest);
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(memberId)
                .build();
    }
}
