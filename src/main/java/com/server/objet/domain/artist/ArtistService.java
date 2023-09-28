package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    @Transactional
    public ArtistInfoResponseDto setNewInfo(ArtistInfoRequestDto artistInfoRequestDto, CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


        //Todo role을 검사하는게 나을까, 아니면 아티스트 테이블에서 찾았는데 분기하는게 나을까
        if(user.getRole()!=Role.ARTIST){
            user.update(Role.ARTIST);

            Artist artist = Artist.builder()
                    .user(user)
                    .comment(artistInfoRequestDto.getComment())
                    .category(artistInfoRequestDto.getCategoryList())
                    .build();

            artistRepository.save(artist);
            return ArtistInfoResponseDto
                    .builder()
                    .name(user.getUsername())
                    .categoryList(artistInfoRequestDto.getCategoryList())
                    .comment(artistInfoRequestDto.getComment())
                    .build();
        }
        return null;
    }

    @Transactional
    public ArtistInfoResponseDto getMyInfo(CustomUserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
        System.out.println(user.getId()+user.getId().getClass().getName());
        System.out.println(userDetails.getUser().getId()+userDetails.getUser().getId().getClass().getName());
        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());


        return ArtistInfoResponseDto
                .builder()
                .name(user.getUsername())
                .categoryList(artist.getCategory())
                .comment(artist.getComment())
                .followingNum(followingRepository.countByUserId(user.getId()))
                .followerNum(followingRepository.countByArtistId(artist.getId()))
                .productNum(artist.getProducts().size())
                .build();
    }

    @Transactional
    public ArtistInfoResponseDto getInfo(Long artistID){
        Artist artist = artistRepository.findById(artistID).get();

        return ArtistInfoResponseDto
                .builder()
                .name(artist.getUser().getUsername())
                .categoryList(artist.getCategory())
                .comment(artist.getComment())
                .build();
    }
    @Transactional
    public ArtistInfoResponseDto chagneMyInfo(ArtistInfoRequestDto artistInfoRequestDto, CustomUserDetails userDetails){
        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());

        artist.update(artistInfoRequestDto.getComment(), artistInfoRequestDto.getCategoryList());

        return ArtistInfoResponseDto
                .builder()
                .name(userDetails.getUser().getUsername())
                .categoryList(artistInfoRequestDto.getCategoryList())
                .comment(artistInfoRequestDto.getComment())
                .build();
        }
}
