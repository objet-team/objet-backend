package com.server.objet.domain.Goods;

import com.server.objet.domain.Goods.dto.*;
import com.server.objet.domain.product.dto.req.ProductInfo;
import com.server.objet.domain.product.dto.res.RegisterProductResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
        return goodsService.getPopularNormalGoods();
    }

    @GetMapping("/popular-nft")
    @Operation(summary = "굿즈 홈 NFT 인기굿즈 조회", description = "토큰이 필요하지 않습니다.")
    public MainPageGoods PopularNftGoods() {
        return goodsService.getPopularNftGoods();
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "굿즈 상세보기", description = "토큰이 필요하지 않습니다.")
    public GoodsDetail getGoods(@PathVariable("id") Long id) {
        return goodsService.getGoodsDetail(id);
    }

    @PostMapping("/register/normal")
    @ResponseBody
    public RegisterNormalGoodsResult registerGoods(@RequestBody ProductInfo productInfo) {
        Long artistId = (Long) 1L; // 이 부분은 추후 토큰을 이용해서 유저 ID 를 Resolve 하는 코드로 변경 예정
        return goodsService.doRegisterNormalGoods(productInfo, artistId);
    }
}
