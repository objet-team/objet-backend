package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.artist.dto.MyArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.Role;
import com.server.objet.global.repository.ArtistRepository;
import com.server.objet.global.repository.FollowingRepository;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    @Transactional
    public MyArtistInfoResponseDto setNewInfo(ArtistInfoRequestDto artistInfoRequestDto, CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //Todo role을 검사하는게 나을까, 아니면 아티스트 테이블에서 찾았는데 분기하는게 나을까
        if(user.getRole()!=Role.ARTIST){
            user.update(Role.ARTIST);

            Artist artist = Artist.builder()
                    .user(user)
                    .category(artistInfoRequestDto.getCategoryList())
                    .comment(artistInfoRequestDto.getComment())
                    .build();

            artistRepository.save(artist);
            return MyArtistInfoResponseDto
                    .builder()
                    .name(user.getUsername())
                    .categoryList(artistInfoRequestDto.getCategoryList())
                    .comment(artistInfoRequestDto.getComment())
                    .build();
        }
        return null;
    }

    @Transactional
    public MyArtistInfoResponseDto getMyInfo(CustomUserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
        System.out.println(user.getId()+user.getId().getClass().getName());
        System.out.println(userDetails.getUser().getId()+userDetails.getUser().getId().getClass().getName());
        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());


        return MyArtistInfoResponseDto.builder()
                .name(user.getUsername())
                .comment(artist.getComment())
                .categoryList(artist.getCategory())
                .profilePrcUrl(artist.getProfilePicUrl())
                .productNum(artist.getProducts().size())
                .followingNum(followingRepository.countByUserId(user.getId()))
                .followerNum(followingRepository.countByArtistId(artist.getId()))
                .build();
    }

    @Transactional
    public ArtistInfoResponseDto getInfo(Long artistID){
        Artist artist = artistRepository.findById(artistID).get();

        return ArtistInfoResponseDto
                .builder()
                .id(artistID)
                .name(artist.getUser().getUsername())
                .comment(artist.getComment())
                .categoryList(artist.getCategory())
                .profilePrcUrl(artist.getProfilePicUrl())
                .build();
    }
    @Transactional
    public MyArtistInfoResponseDto changeMyInfo(ArtistInfoChangeRequest artistInfoChangeRequest, CustomUserDetails userDetails){
        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());

        artist.update(artistInfoChangeRequest.getComment(), artistInfoChangeRequest.getCategoryList(), artistInfoChangeRequest.getProfilePicUrl());

        return MyArtistInfoResponseDto
                .builder()
                .name(userDetails.getUser().getUsername())
                .profilePrcUrl(artistInfoChangeRequest.getProfilePicUrl())
                .categoryList(artistInfoChangeRequest.getCategoryList())
                .comment(artistInfoChangeRequest.getComment())
                .followerNum(artist.getFollows().size())
                .build();
        }
}
