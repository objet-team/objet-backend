package com.server.objet.domain.follow;

import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.server.objet.global.RequestURI.FOLLOW_URI;

@RestController
@RequestMapping(FOLLOW_URI)
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    @GetMapping("/{artistId}")
    @Operation(summary = "팔로우", description = "토큰이 필요합니다. 현재 자신이 자신을 팔로우하는 것이 막혀있지 않습니다.")
    public ResponseEntity<String> Follow(
            @PathVariable Long artistId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(followService.follow(userDetails,artistId)+" 작가님을 팔로우하였습니다.");
    }

    @DeleteMapping("/{artistId}")
    @Operation(summary = "팔로우 취소", description = "토큰이 필요합니다.")
    public ResponseEntity<String> UnFollow(
            @PathVariable Long artistId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(followService.unFollow(userDetails,artistId));
    }

    @GetMapping("/availability/{artistId}") //남의 계정
    @Operation(summary = "팔로우 여부", description = "토큰이 필요합니다. 반환값 true일 시 현재 팔로우 하고 있지 않음 (=팔로우 가능)")
    public ResponseEntity<Boolean> isFollow(@PathVariable Long artistId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(followService.isFollow(userDetails,artistId));
    }

    @GetMapping("/list") //남의 계정
    @Operation(summary = "팔로우한 작가 목록", description = "토큰이 필요합니다.")
    public ResponseEntity<FollowListResponseDto> FollowList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(followService.getFollowList(userDetails));
    }
}
