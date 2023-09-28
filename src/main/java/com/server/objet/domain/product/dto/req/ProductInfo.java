package com.server.objet.domain.product.dto.req;

import com.server.objet.global.entity.Content;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductInfo {

    private String title;
    private String category;
    private String description;
    private List<Object> contents;
}
