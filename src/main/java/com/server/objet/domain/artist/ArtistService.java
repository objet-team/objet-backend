package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.Role;
import com.server.objet.global.repository.ArtistRepository;
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

    @Transactional
    public ArtistInfoResponseDto setNewInfo(ArtistInfoRequestDto artistInfoRequestDto, CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));


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

//        ArtistInfoResponseDto result = new ArtistInfoResponseDto(artist);

        return ArtistInfoResponseDto
                .builder()
                .name(user.getUsername())
                .categoryList(artist.getCategory())
                .comment(artist.getComment())
                .build();
    }

    @Transactional
    public ArtistInfoResponseDto getInfo(Long artistID){
        Artist artist = artistRepository.findById(artistID)
                .orElseThrow(() ->new UsernameNotFoundException("아티스트를 찾을 수 없습니다"));

        //        System.out.println(user.getId()+user.getId().getClass().getName());
//        System.out.println(userDetails.getUser().getId()+userDetails.getUser().getId().getClass().getName());
//        Artist artist = artistRepository.findByUserId(userDetails.getUser().getId());


//        ArtistInfoResponseDto result = new ArtistInfoResponseDto(artist);

        return ArtistInfoResponseDto
                .builder()
                .name(artist.getUser().getUsername())
                .categoryList(artist.getCategory())
                .comment(artist.getComment())
                .build();
    }
}
