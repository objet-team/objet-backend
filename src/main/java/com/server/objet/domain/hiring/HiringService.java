package com.server.objet.domain.hiring;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.Hiring;
import com.server.objet.global.entity.Product;
import com.server.objet.global.entity.User;
import com.server.objet.global.repository.ArtistRepository;
import com.server.objet.global.repository.HiringRepository;
import com.server.objet.global.repository.ProductRepository;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HiringService {

    private final UserRepository userRepository;

    private final ArtistRepository artistRepository;

    private final ProductRepository productRepository;

    private final HiringRepository hiringRepository;


    @Transactional
    public HiringDetailResponseDto postHiring(CustomUserDetails userDetails, Long productId, HiringRequestDto hiringRequestDto){
        //고용 제의 보내는 사람 식별
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        //프로덕트 찾기
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->new NoSuchElementException("해당 전시를 찾을 수 없습니다."));

        LocalDateTime currentDateTime = LocalDateTime.now();

        Hiring hiring = Hiring.builder()
                .company(hiringRequestDto.company)
                .contact(hiringRequestDto.contact)
                .comment(hiringRequestDto.comment)
                .localDateTime(currentDateTime) //그냥 현재로
                .userId(user.getId())
                .productId(product.getId())
                .build();

        hiringRepository.save(hiring);

        return HiringDetailResponseDto.builder()
                .comment(hiringRequestDto.comment)
                .company(hiringRequestDto.company)
                .contact(hiringRequestDto.contact)
                .localDateTime(currentDateTime) //그냥 현재로
                .build();
    }

    @Transactional
    public HiringListResponseDto getHiringList(CustomUserDetails userDetails, Long productId){
        Product product = productRepository.findById(productId)
                        .orElseThrow(() ->new NoSuchElementException("해당 전시를 찾을 수 없습니다."));


        List<HiringDetailResponseDto> hiringInfoList = hiringRepository.findByProductId(product.getId()); // User 파라미터를 추가해야 함

        HiringListResponseDto result = new HiringListResponseDto(hiringInfoList);


        return HiringListResponseDto.builder()
                .hiringInfoList(result.getHiringInfoList())
                .build();
    }

    @Transactional
    public HiringDetailResponseDto getHiringInfo(CustomUserDetails userDetails, Long hiringId){
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        Hiring hiring = hiringRepository.findById(hiringId)
                .orElseThrow(() ->new NoSuchElementException("해당 제안를 찾을 수 없습니다."));

        return HiringDetailResponseDto.builder()
                .comment(hiring.getComment())
                .company(hiring.getCompany())
                .contact(hiring.getContact())
                .localDateTime(hiring.getLocalDateTime()) //그냥 현재로
                .build();
    }
}
