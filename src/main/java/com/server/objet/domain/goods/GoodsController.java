package com.server.objet.domain.goods;

import com.server.objet.domain.goods.dto.*;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.enums.GoodsType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        return goodsService.getPopularGoods(GoodsType.NORMAL);
    }

    @GetMapping("/popular-nft")
    @Operation(summary = "굿즈 홈 NFT 인기굿즈 조회", description = "토큰이 필요하지 않습니다.")
    public MainPageGoods PopularNftGoods() {
        return goodsService.getPopularGoods(GoodsType.NFT);
    }

    @GetMapping("/detail/{goodsId}")
    @Operation(summary = "굿즈 상세보기", description = "토큰이 필요하지 않습니다.")
    public GoodsDetailInfo getGoods(@PathVariable("goodsId") Long goodsId) {
        return goodsService.getGoodsDetail(goodsId);
    }

    @PostMapping("/register/normal")
    @ResponseBody
    @Operation(summary = "일반 굿즈 등록하기", description = "Category에는 NORMAL 또는 NFT를 넣어주세요.")
    public RegisterNormalGoodsResult registerGoods(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody GoodsInfo goodsInfo) {
        return goodsService.doRegisterNormalGoods(goodsInfo, userDetails);
    }

    @PostMapping("/cart/in")
    @Operation(summary = "장바구니에 굿즈를 추가", description = "")
    public ResponseEntity<String> cartIn(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CartRegisterInfo cartRegisterInfo) {
        return ResponseEntity.ok(goodsService.addGoodsToCart(userDetails, cartRegisterInfo));
    }

    @GetMapping("/cart/all")
    @Operation(summary = "장바구니 목록 조회", description = "")
    public CartItems getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return goodsService.getCartItems(userDetails);
    }

    @DeleteMapping("/cart/out/{cartId}")
    @Operation(summary = "장바구니에서 굿즈 삭제", description = "목록 조회시 함께 리턴되는 cartId를 입력해주세요")
    public ResponseEntity<String> cartOut(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("cartId") Long cartId) {
        return ResponseEntity.ok(goodsService.deleteIncartGoods(userDetails, cartId));
    }

}
