package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.auth.kakao.req.KakaoLoginRequest;
import com.server.objet.global.entity.Artist;
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
    public ResponseEntity<ArtistInfoResponseDto> ArtistRegister(@RequestBody @Valid ArtistInfoRequestDto artistInfoRequestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(artistService.setNewInfo(artistInfoRequestDto,userDetails));
    }


    @GetMapping("/info") //내 계정
    public ResponseEntity<ArtistInfoResponseDto> Info(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(artistService.getMyInfo(userDetails));

    }



//    @GetMapping("/info/{id}") //남의 계정
//    public ArtistInfoResponseDto Info(@PathVariable("id") Long id){
//        return artistService.getInfo(id);
//    }



}
