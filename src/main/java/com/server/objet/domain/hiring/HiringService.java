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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

public class HiringService {

    UserRepository userRepository;

    ArtistRepository artistRepository;
    ProductRepository productRepository;

    HiringRepository hiringRepository;



    @Transactional
    public HiringListResponseDto getHiringList(CustomUserDetails userDetails, Long productId){
//        User user = userRepository.findByEmail(userDetails.getEmail())
//                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        Product product = productRepository.findById(productId)
                        .orElseThrow(() ->new NoSuchElementException("해당 전시를 찾을 수 없습니다."));

//        Hiring hiring = hiringRepository.findByProductId(product.getId());
//        Hiring hiringInfoList = hiringRepository.findByProductId(product.getId());


        List<HiringCall> hiringInfoList = hiringRepository.findByProductId(product.getId()); // User 파라미터를 추가해야 함
        //.orElseThrow(() -> new WeatherNotFoundException("해당 아이디로 조회되는 날씨 객체가 존재하지 않습니다."));

        HiringListResponseDto result = new HiringListResponseDto(hiringInfoList);

//        WeatherMonthlyResponseDto result = new WeatherMonthlyResponseDto(weatherList);


//        System.out.println(user.getId()+user.getId().getClass().getName());
//        System.out.println(userDetails.getUser().getId()+userDetails.getUser().getId().getClass().getName());
//        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());

        return result;
    }

    @Transactional
    public HiringCall getHiringInfo(CustomUserDetails userDetails, Long hiringId){
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

//
//        Product product = productRepository.findById(hiringId)
//                .orElseThrow(() ->new NoSuchElementException("해당 전시를 찾을 수 없습니다."));

        Hiring hiring = hiringRepository.findById(hiringId)
                .orElseThrow(() ->new NoSuchElementException("해당 제안를 찾을 수 없습니다."));

        HiringCall hiringCall = new HiringCall(hiring);
//        Hiring hiringInfoList = hiringRepository.findByProductId(product.getId());




//        System.out.println(user.getId()+user.getId().getClass().getName());
//        System.out.println(userDetails.getUser().getId()+userDetails.getUser().getId().getClass().getName());
//        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());

        return hiringCall;
    }


}
