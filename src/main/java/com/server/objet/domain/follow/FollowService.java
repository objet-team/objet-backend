package com.server.objet.domain.follow;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.Follow;
import com.server.objet.global.entity.User;
import com.server.objet.global.repository.ArtistRepository;
import com.server.objet.global.repository.FollowingRepository;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;


    @Transactional
    public String follow(CustomUserDetails userDetails, Long artistId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //Todo 있으면 안하는 걸로

        //테이블에 없으면 추가
        Follow followEntity = followingRepository.findByUserIdAndArtistId(user.getId(), artistId)
                .orElseGet(() -> saveFollow(userDetails, artistId)
                );

        return artistRepository.findById(artistId).get().getUser().getUsername();
    }

    private Follow saveFollow(CustomUserDetails userDetails, Long artistId) {
        Follow follow = Follow.builder()
                .artistId(artistId)
                .userId(userDetails.getUser().getId())
                .build();
        followingRepository.save(follow);

        return follow;
    }


    @Transactional
    public String unFollow(CustomUserDetails userDetails, Long artistId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //테이블에 없으면 에러
        Follow followEntity = followingRepository.findByUserIdAndArtistId(user.getId(), artistId)
                .orElseThrow(() ->new UsernameNotFoundException("해당 작가를 팔로우 하지 않은 상태입니다."));
//
//        Follow follow = Follow.builder()
//                .artistId(artistId)
//                .userId(userDetails.getUser().getId())
//                .build();
        followingRepository.delete(followEntity);

        Artist artist= artistRepository.findById(artistId).get();
        return artist.getUser().getUsername();
    }
}
