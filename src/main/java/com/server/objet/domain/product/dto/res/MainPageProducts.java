package com.server.objet.domain.product.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MainPageProducts {

    private List<MainPageProductInfo> productInfos;

}
