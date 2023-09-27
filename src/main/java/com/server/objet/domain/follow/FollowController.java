package com.server.objet.domain.follow;

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
    @GetMapping("/{artistId}") //내 계정
    @Operation(summary = "팔로우", description = "토큰이 필요합니다.")
    public ResponseEntity<String> Follow(
            @PathVariable Long userId,
            @PathVariable Long artistId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(followService.follow(userDetails,artistId)+"작가님을 팔로우하였습니다.");
    }

    @DeleteMapping("/{artistId}") //내 계정
    @Operation(summary = "팔로우", description = "토큰이 필요합니다.")
    public ResponseEntity<String> UnFollow(
            @PathVariable Long userId,
            @PathVariable Long artistId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        followService.unFollow(userDetails,artistId);
        return ResponseEntity.ok(followService.unFollow(userDetails,artistId)+"작가님을 팔로우 취소하였습니다.");
    }
}
