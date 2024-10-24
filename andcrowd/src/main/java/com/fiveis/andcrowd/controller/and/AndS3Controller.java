package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.AndS3DTO;
import com.fiveis.andcrowd.service.and.AndS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/and/s3")

public class AndS3Controller {

    private final AndS3Service andS3Service;

    @PostMapping("/uploads")
    public ResponseEntity<Object> uploadFiles(
            @RequestParam(value = "andId") int andId,
            @RequestParam(value = "fileType") String fileType,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles) {
        List<AndS3DTO> uploadedFiles = andS3Service.uploadFiles(andId, fileType, multipartFiles);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadedFiles);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(
            @RequestParam(value = "andId") int andId,
            @RequestParam(value = "fileType") String fileType,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        AndS3DTO uploadedFile = andS3Service.uploadFile(andId, fileType, multipartFile);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadedFile);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFile(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestParam(value = "uuidFileName") String uuidFileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(andS3Service.deleteFile(uploadFilePath, uuidFileName));
    }
}
