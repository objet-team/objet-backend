package com.server.objet.domain.hiring;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class HiringListResponseDto {

    List<HiringDetailResponseDto> hiringInfoList;


}
