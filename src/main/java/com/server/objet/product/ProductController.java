package com.server.objet.product;

import com.server.objet.global.entity.Like;
import com.server.objet.global.entity.Product;
import com.server.objet.product.dto.PopularProducts;
import com.server.objet.product.dto.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public PopularProducts getAllProduct() {
        return productService.getPopularProducts();
    }

    @GetMapping("/{id}")
    public ProductDetail getProduct(@PathVariable("id") Long id) {
        return productService.getProductDetail(id);
    }
}
