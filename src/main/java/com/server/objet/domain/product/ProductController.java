package com.server.objet.domain.product;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.product.dto.req.ProductInfo;
import com.server.objet.domain.product.dto.res.LikeResult;
import com.server.objet.domain.product.dto.res.MainPageProducts;
import com.server.objet.domain.product.dto.ProductDetail;
import com.server.objet.domain.product.dto.res.RegisterProductResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.server.objet.global.RequestURI.PRODUCT_URI;

@RestController
@RequestMapping(PRODUCT_URI)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/popular")
    @Operation(summary = "작품 홈 인기순", description = "토큰이 필요하지 않습니다.")
    public MainPageProducts PopularProducts() {
        return productService.getPopularProducts();
    }

    @GetMapping("/new")
    @Operation(summary = "작품 홈 최신순", description = "토큰이 필요하지 않습니다.")
    public MainPageProducts NewProducts() {
        return productService.getNewProducts();
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "작품 상세보기", description = "토큰이 필요하지 않습니다.")
    public ProductDetail getProduct(@PathVariable("id") Long id) {
        return productService.getProductDetail(id);
    }

    @GetMapping("/weekly")
    @Operation(summary = "금주의 모브제", description = "토큰이 필요하지 않습니다.")
    public MainPageProducts WeeklyPopular() {
        return productService.getWeeklyPopularProducts();
    }

    @PostMapping("/register")
    @ResponseBody
    @Operation(summary = "작품 등록하기", description = "이미지 업로드를 먼저 진행한 뒤 호출해주세요.")
    public RegisterProductResult registerProduct(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ProductInfo productInfo) {
        return productService.doRegisterProduct(productInfo, userDetails);
    }

    @PostMapping("/like/{productId}")
    @ResponseBody
    @Operation(summary = "작품 좋아요", description = "해당 유저가 작품에 좋아요를 누르지 않은 상태일 때만 사용 가능합니다.")
    public LikeResult addLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("productId") Long productId) {
        return productService.addLike(userDetails, productId);
    }

    @DeleteMapping("/unlike/{productId}")
    @ResponseBody
    @Operation(summary = "작품 좋아요 취소", description = "해당 유저가 작품에 좋아요를 누른 상태일 때만 사용 가능합니다.")
    public LikeResult unLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("productId") Long productId) {
        return productService.deleteLike(userDetails, productId);
    }

}
