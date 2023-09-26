package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.auth.kakao.req.KakaoLoginRequest;
import com.server.objet.global.entity.Artist;
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

//    @GetMapping("/{id}")
//    public MainPageProducts PopularProducts() {
//        return productService.getPopularProducts();
//    }
//
//    @GetMapping("/new")
//    public MainPageProducts NewProducts() {
//        return productService.getNewProducts();
//    }
//
//    @GetMapping("/{id}")
//    public ProductDetail getProduct(@PathVariable("id") Long id) {
//        return productService.getProductDetail(id);
//    }

    @PostMapping("/info")
    @Operation(summary = "나의 아티스트 정보 등록", description = "토큰이 필요합니다. 현재 프로필 이미지 업로드는 지원하지 않습니다.")
    public ResponseEntity<ArtistInfoResponseDto> ArtistRegister(@RequestBody @Valid ArtistInfoRequestDto artistInfoRequestDto,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(artistService.setNewInfo(artistInfoRequestDto, userDetails));
    }


    @GetMapping("/info") //내 계정
    @Operation(summary = "나의 아티스트 정보 조회", description = "토큰이 필요합니다. 현재 프로필 이미지는 지원하지 않습니다.")
    public ResponseEntity<ArtistInfoResponseDto> MyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(artistService.getMyInfo(userDetails));

    }


    @GetMapping("/info/public/{artistId}") //남의 계정
    @Operation(summary = "타 아티스트 정보 조회", description = "토큰이 필요하지 않습니다. 현재 프로필 이미지는 지원하지 않습니다.")
    public ResponseEntity<ArtistInfoResponseDto> Info(@PathVariable Long artistId) {
        return ResponseEntity.ok(artistService.getInfo(artistId));
    }
}
