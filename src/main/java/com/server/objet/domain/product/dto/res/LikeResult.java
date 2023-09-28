package com.server.objet.domain.product.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LikeResult {
    private final String action;
    private final String status;
}
