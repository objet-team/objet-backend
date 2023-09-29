package com.server.objet.domain.product.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ActionResult {
    private final String action;
    private final String status;
}
