package com.server.objet.domain.scrap;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.*;
import com.server.objet.global.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final ProductRepository productRepository;
    private final ArtistRepository artistRepository;
    private final ContentRepository contentRepository;


    @Transactional
    public String scrap(CustomUserDetails userDetails, Long productId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //Todo 있으면 안하는 걸로
        //테이블에 없으면 추가
        Scrap scrap = scrapRepository.findByUserIdAndProductId(user.getId(), productId)
                .orElseGet(
                        () -> saveFollow(userDetails, productId)
                );

        return productRepository.findById(productId).get().getTitle();
    }

    private Scrap saveFollow(CustomUserDetails userDetails, Long productId) {
        Scrap scrap = Scrap.builder()
                .productId(productId)
                .userId(userDetails.getUser().getId())
                .build();
        scrapRepository.saveAndFlush(scrap);
        System.out.println("스크랩 디비에 저장!");

        return scrap;
    }


    public String unScrap(CustomUserDetails userDetails, Long productId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //테이블에 없으면 에러는 되는데
        //TODO 어떤 전시를 스크랩 취소 했는지 이름 반환되게 수정
        Scrap scrap= scrapRepository.findByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() ->new UsernameNotFoundException("해당 전시를 스크랩 하지 않은 상태입니다."));

        scrapRepository.delete(scrap);

        return "스크랩 취소";
    }


    @Transactional
    public ScrapListResponseDto getScrapList(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        List<ScrapItem> scrapItemList = new ArrayList<>();

        List<Scrap> scrapList= scrapRepository.findByUserId(user.getId());

        for(Scrap scrap : scrapList){
            Product product = productRepository.findById(scrap.getProductId()).get();
            Artist artist = artistRepository.findById(product.getArtistId()).get();
            Content content = contentRepository
                    .findTop1ByProductIdAndTypeOrderByContentOrderAsc(product.getId(), "image").get();

            //Todo productSevice 단에서 썸네일을 뽑아내는 메소드를 따로 빼길 바람
            // 아님 프로덕트 테이블에 썸네일을 빠로 빼놓는 것은..? 아님.. 말구~
            ScrapItem scrapItem = ScrapItem.builder()
                    .productName(product.getTitle())
                    .productId(product.getId())
                    .artistId(product.getArtistId())
                    .artistName(artist.getUser().getName())
                    .category(Collections.singletonList(product.getCategory()))
                    .LikeNum(product.getLikeCount())
                    .thumbnail(content.getUrl())
                    .build();

            scrapItemList.add(scrapItem);
        }
        return new ScrapListResponseDto(scrapItemList.size(),scrapItemList);
    }
}
