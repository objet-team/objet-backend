package com.server.objet.domain.scrap;

import com.server.objet.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.server.objet.global.RequestURI.SCRAP_URI;

@RestController
@RequestMapping(SCRAP_URI)
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;
    @GetMapping("/{productId}")
    @Operation(summary = "스크랩", description = "토큰이 필요합니다.")
    public ResponseEntity<String> Scrap(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(scrapService.scrap(userDetails,productId)+" 전시를 스크랩하였습니다.");
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "스크랩 취소", description = "토큰이 필요합니다.")
    public ResponseEntity<String> UnScrap(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(scrapService.unScrap(userDetails,productId));
    }

    @GetMapping("/list") //남의 계정
    @Operation(summary = "내가 스크랩 한 전시 모음", description = "토큰이 필요합니다.")
    public ResponseEntity<ScrapListResponseDto> isFollow(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(scrapService.getScrapList(userDetails));
    }
}
