package com.server.objet.domain.follow;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.Follow;
import com.server.objet.global.entity.Product;
import com.server.objet.global.entity.User;
import com.server.objet.global.repository.ArtistRepository;
import com.server.objet.global.repository.FollowingRepository;
import com.server.objet.global.repository.ProductRepository;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;
    private final ProductRepository productRepository;


    @Transactional
    public String follow(CustomUserDetails userDetails, Long artistId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //Todo 있으면 안하는 걸로
        //테이블에 없으면 추가
        Follow followEntity = followingRepository.findByUserIdAndArtistId(user.getId(), artistId)
                .orElseGet(
                        () -> saveFollow(userDetails, artistId)
                );

        return artistRepository.findById(followEntity.getArtistId()).get().getUser().getUsername();
    }

    private Follow saveFollow(CustomUserDetails userDetails, Long artistId) {
        Follow follow = Follow.builder()
                .artistId(artistId)
                .userId(userDetails.getUser().getId())
                .build();
        followingRepository.saveAndFlush(follow);
        System.out.println("팔로우 디비에 저장!");

        return follow;
    }


    public String unFollow(CustomUserDetails userDetails, Long artistId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //테이블에 없으면 에러는 되는데
        //TODO 누구를 언팔로우 했는지 이름 반환되게 수정
        Follow followEntity = followingRepository.findByUserIdAndArtistId(user.getId(), artistId)
                .orElseThrow(() ->new UsernameNotFoundException("해당 작가를 팔로우 하지 않은 상태입니다."));

        followingRepository.delete(followEntity);

        return "팔로우 취소";
    }

    public Boolean isFollow(CustomUserDetails userDetails, Long artistId) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        Optional<Follow> followEntityOpt = followingRepository.findByUserIdAndArtistId(user.getId(), artistId);

        return followEntityOpt.isEmpty(); // empty가 true -> 테이블에 없으므로 팔로우 가능!
    }


    @Transactional
    public FollowListResponseDto getFollowList(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        List<FollowItem> followItemList = new ArrayList<>();
        List<Follow> followList = followingRepository.findByUserId(user.getId());

        for (Follow follow : followList) {

            Artist artist = artistRepository.findById(follow.getArtistId()).get();

            FollowItem followItem = FollowItem.builder()
                    .artistId(artist.getId())
                    .artistName(artist.getUser().getName())
                    .profileUrl(artist.getUser().getProfilePicUrl())
                    .category(artist.getCategory())
//                    .productNum(artist.getProducts().size()) //Todo 맞나?
                    .productNum(productRepository.countByArtistId(artist.getId()))
                    .followerNum(followingRepository.countByArtistId(artist.getId()))
                    .build();

            followItemList.add(followItem);
        }


        return new FollowListResponseDto(followItemList.size(), followItemList);
    }
}
