package com.server.objet.product;

import com.server.objet.product.dto.MainPageProducts;
import com.server.objet.product.dto.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
