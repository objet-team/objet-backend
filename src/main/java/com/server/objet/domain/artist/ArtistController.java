package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.artist.dto.MyArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.server.objet.global.RequestURI.ARTIST_URI;

@RestController
@RequestMapping(ARTIST_URI)
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;


    @PostMapping("/info")
    @Operation(summary = "나의 아티스트 정보 등록", description = "토큰이 필요합니다. 현재 프로필 이미지 업로드는 지원하지 않습니다. " +
            "카테고리 enum은 UX_UI, GRAPHIC_DESIGN, VIDEO_AND_MOTION_GRAPHICS, ILLUSTRATION, CHARACTER_DESIGN, PHOTOGRAPHY, CRAFTS, CERAMICS_AND_GLASS 입니다.")
    public ResponseEntity<MyArtistInfoResponseDto> ArtistRegister(@RequestBody @Valid ArtistInfoRequestDto artistInfoRequestDto,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(artistService.setNewInfo(artistInfoRequestDto, userDetails));
    }


    @GetMapping("/info") //내 계정
    @Operation(summary = "나의 아티스트 정보 조회", description = "토큰이 필요합니다. 현재 프로필 이미지는 지원하지 않습니다.")
    public ResponseEntity<MyArtistInfoResponseDto> MyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(artistService.getMyInfo(userDetails));

    }


    @GetMapping("/info/public/{artistId}") //남의 계정
    @Operation(summary = "타 아티스트 정보 조회", description = "토큰이 필요하지 않습니다.")
    public ResponseEntity<ArtistInfoResponseDto> Info(@PathVariable Long artistId) {
        return ResponseEntity.ok(artistService.getInfo(artistId));
    }

    @PatchMapping("/info")
    @Operation(summary = "아티스트 마이페이지 수정", description = "토큰이 필요합니다.\n\n" +
            "카테고리 enum은 UX_UI, GRAPHIC_DESIGN, VIDEO_AND_MOTION_GRAPHICS, ILLUSTRATION, CHARACTER_DESIGN, PHOTOGRAPHY, CRAFTS, CERAMICS_AND_GLASS 입니다.\n\n" +
            "리퀘스트 바디 중에 profilePicUrl의 경우 [1.이미지 수정 안함 -> 기존에 있던 url] [2.이미지 수정 함 -> 새 url] [3.기본 이미지로 -> null 던지기]")
    public ResponseEntity<MyArtistInfoResponseDto> ChangeInfo(@RequestBody @Valid ArtistInfoChangeRequest artistInfoChangeRequest,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(artistService.changeMyInfo(artistInfoChangeRequest, userDetails));
    }

}
