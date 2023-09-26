package com.server.objet.domain.hiring;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.product.dto.res.MainPageProducts;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class HiringController {
    //productId 넣어서 리스트 하나 -> 기업명, 날짜만 여기서 hiringid 넘기기

    HiringService hiringService;


    @PostMapping("/hiring")
    @Operation(summary = "제안하기", description = "토큰이 필요합니다?")
    public HiringCall Hiring(@PathVariable("hiringId") Long hiringId,
                             @RequestBody HiringRequestDto hiringRequestDto,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        return hiringService.getHiringInfo(userDetails, hiringId);
    }

    @GetMapping("/hiring/{productId}")
    @Operation(summary = "특정 작품에 대한 고용 제의", description = "토큰이 필요합니다.")
    public HiringListResponseDto HiringList(@PathVariable("productId") Long productId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        //본인이 아니면 못
        return hiringService.getHiringList(userDetails, productId);
    }


    //hiringid로 상세
    @GetMapping("/hiring/more/{hiringId}")
    @Operation(summary = "특정 제의에 대한 상세 정보", description = "토큰이 필요합니다.")
    public HiringCall HiringDetails(@PathVariable("hiringId") Long hiringId,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        return hiringService.getHiringInfo(userDetails, hiringId);
    }

}
