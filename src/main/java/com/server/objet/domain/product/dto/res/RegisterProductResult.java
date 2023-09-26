package com.server.objet.domain.product.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterProductResult {
    private final String message;
    private final Long productId;
}
