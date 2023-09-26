package com.server.objet.domain.artist;

import com.server.objet.domain.artist.dto.ArtistInfoRequestDto;
import com.server.objet.domain.artist.dto.ArtistInfoResponseDto;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.Role;
import com.server.objet.global.exception.UserNotFoundException;
import com.server.objet.global.repository.ArtistRepository;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    ArtistRepository artistRepository;
    UserRepository userRepository;

    @Transactional
    public void setNewInfo(ArtistInfoRequestDto artistInfoRequestDto, CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail());
//                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 없습니다."));


        if(user.getRole()!=Role.ARTIST){
            user.update(Role.ARTIST);

            Artist artist = Artist.builder()
                    .user(user)
                    .comment(artistInfoRequestDto.getComment())
                    .build();

            artistRepository.save(artist);
        }
    }

//    public ArtistInfoResponseDto getMyInfo(CustomUserDetails userDetails){
//        Optional<Artist> artist = artistRepository.findById(userDetails.getUser().getId());
//        ArtistInfoResponseDto result = new ArtistInfoResponseDto(artist);
//
//        return result;
//
//    }
}
