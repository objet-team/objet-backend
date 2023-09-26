package com.server.objet.domain.product;

import com.server.objet.domain.product.dto.req.ProductInfo;
import com.server.objet.domain.product.dto.res.MainPageProducts;
import com.server.objet.domain.product.dto.ProductDetail;
import com.server.objet.domain.product.dto.res.RegisterProductResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/popular")
    public MainPageProducts PopularProducts() {
        return productService.getPopularProducts();
    }

    @GetMapping("/new")
    public MainPageProducts NewProducts() {
        return productService.getNewProducts();
    }

    @GetMapping("/{id}")
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
