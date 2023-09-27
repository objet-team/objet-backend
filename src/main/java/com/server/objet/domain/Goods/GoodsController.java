package com.server.objet.domain.Goods;

import com.server.objet.domain.Goods.dto.*;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.product.dto.req.ProductInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.server.objet.global.RequestURI.GOODS_URI;

@RestController
@RequestMapping(GOODS_URI)
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/popular-normal")
    @Operation(summary = "굿즈 홈 일반 인기굿즈 조회", description = "토큰이 필요하지 않습니다.")
    public MainPageGoods PopularNormalGoods() {
        return goodsService.getPopularGoods("Normal");
    }

    @GetMapping("/popular-nft")
    @Operation(summary = "굿즈 홈 NFT 인기굿즈 조회", description = "토큰이 필요하지 않습니다.")
    public MainPageGoods PopularNftGoods() {
        return goodsService.getPopularGoods("NFT");
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "굿즈 상세보기", description = "토큰이 필요하지 않습니다.")
    public GoodsDetailInfo getGoods(@PathVariable("id") Long id) {
        return goodsService.getGoodsDetail(id);
    }

    @PostMapping("/register/normal")
    @ResponseBody
    @Operation(summary = "일반 굿즈 등록하기", description = "Category에는 Normal 또는 NFT를 넣어주세요.")
    public RegisterNormalGoodsResult registerGoods(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody GoodsInfo goodsInfo) {
        return goodsService.doRegisterNormalGoods(goodsInfo, userDetails);
    }
}
