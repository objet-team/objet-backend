package com.server.objet.domain.product;

import com.server.objet.domain.product.dto.req.ProductInfo;
import com.server.objet.domain.product.dto.res.MainPageProducts;
import com.server.objet.domain.product.dto.ProductDetail;
import com.server.objet.domain.product.dto.res.RegisterProductResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    @ResponseBody
    public RegisterProductResult registerProduct(@RequestBody ProductInfo productInfo) {
        Long artistId = (Long) 1L; // 이 부분은 추후 토큰을 이용해서 유저 ID 를 Resolve 하는 코드로 변경 예정
        return productService.doRegisterProduct(productInfo, artistId);
    }

}
