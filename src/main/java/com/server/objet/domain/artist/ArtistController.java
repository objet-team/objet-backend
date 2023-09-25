package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.user.CustomUserDetails;
import com.server.objet.product.dto.MainPageProducts;
import com.server.objet.product.dto.ProductDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("artist")
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
    public void ArtistRegister(@RequestBody @Valid ArtistInfoRequestDto artistInfoRequestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        artistService.setNewInfo(artistInfoRequestDto, userDetails);
    }


//    @GetMapping("/info/{id}")
//    public ArtistInfo Info(@PathVariable("id") Long id){ return artistService.getInfo(id);}


}
