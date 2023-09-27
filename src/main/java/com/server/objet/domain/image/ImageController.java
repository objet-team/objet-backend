package com.server.objet.domain.image;

import com.server.objet.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.server.objet.global.RequestURI.IMAGE_URI;

@RestController
@RequestMapping(IMAGE_URI)
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 업로드 하기", description = "작품에 사용 될 이미지를 S3에 업로드하는 기능입니다.")
    public String uploadImage(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("file") MultipartFile file) throws IOException {
        return imageService.saveFile(file);
    }

}
