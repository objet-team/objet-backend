package com.server.objet.domain.artist;

import com.server.objet.global.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ArtistInfoChangeRequest {
    String comment;
    List<Category> categoryList;
    String profilePicUrl;
}