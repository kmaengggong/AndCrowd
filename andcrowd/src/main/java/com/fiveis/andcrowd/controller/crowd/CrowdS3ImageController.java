package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdS3DTO;
import com.fiveis.andcrowd.service.crowd.CrowdS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crowd/editor")
public class CrowdS3ImageController {
    private final CrowdS3Service crowdS3Service;

    @PostMapping("/uploadImage")
    public ResponseEntity<CrowdS3DTO> uploadImage(@RequestParam("image") MultipartFile image) {
        System.out.println(image);
        try {
            CrowdS3DTO uploadedImage = crowdS3Service.uploadFile(0, "details", image);
            return ResponseEntity.ok(uploadedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


