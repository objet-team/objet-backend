package com.server.objet.domain.hiring;

import com.server.objet.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.server.objet.global.RequestURI.HIRING_URI;

@RequestMapping(HIRING_URI)
@RestController
@RequiredArgsConstructor
public class HiringController {
    //productId 넣어서 리스트 하나 -> 기업명, 날짜만 여기서 hiringid 넘기기

    HiringService hiringService;


    @PostMapping("/{productId}")
    @Operation(summary = "고용 제안하기", description = "{작업 중} 토큰이 필요합니다.")
    public ResponseEntity<HiringDetailResponseDto> Hiring(@PathVariable("productId") Long productId,
                             @RequestBody HiringRequestDto hiringRequestDto,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(hiringService.postHiring(userDetails, productId, hiringRequestDto));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "특정 작품에 대한 고용 제의", description = "{작업 중} 토큰이 필요합니다.")
    public HiringListResponseDto HiringList(@PathVariable("productId") Long productId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        //본인이 아니면 못
        return hiringService.getHiringList(userDetails, productId);
    }


    //hiringid로 상세
    @GetMapping("/more/{hiringId}")
    @Operation(summary = "특정 제의에 대한 상세 정보", description = "{작업 중} 토큰이 필요합니다.")
    public HiringDetailResponseDto HiringDetails(@PathVariable("hiringId") Long hiringId,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        return hiringService.getHiringInfo(userDetails, hiringId);
    }

}