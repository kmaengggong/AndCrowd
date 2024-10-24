package com.fiveis.andcrowd.controller.and;


import com.fiveis.andcrowd.dto.and.AndS3DTO;
import org.springframework.http.HttpStatus;
import com.fiveis.andcrowd.service.and.AndS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/and/editer")
public class S3ImageController {

    private final AndS3Service andS3Service;

    @Autowired
    public S3ImageController(AndS3Service andS3Service) {
        this.andS3Service = andS3Service;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<AndS3DTO> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            AndS3DTO uploadedImage = andS3Service.uploadFile(0, "details", image);
            return ResponseEntity.ok(uploadedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

