package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdS3DTO;
import com.fiveis.andcrowd.service.crowd.CrowdS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/crowd/s3")
//@CrossOrigin(origins = "http://localhost:3000")
public class CrowdS3Controller {

    private final CrowdS3Service crowdS3Service;

    @PostMapping("/uploads")
    public ResponseEntity<Object> uploadImages(
            @RequestParam(value = "crowdId") int crowdId,
            @RequestParam(value = "fileType") String fileType,
            @RequestPart(value = "files") List<MultipartFile> files) {
        List<CrowdS3DTO> uploadedFiles = crowdS3Service.uploadFiles(crowdId, fileType, files);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadedFiles);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImage(
            @RequestParam(value = "crowdId") int crowdId,
            @RequestParam(value = "fileType") String fileType,
            @RequestPart(value = "file") MultipartFile file) {
        CrowdS3DTO uploadedFile = crowdS3Service.uploadFile(crowdId, fileType, file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadedFile);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteImage(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestParam(value = "uuidFileName") String uuidFileName) {
        String result = crowdS3Service.deleteFiles(uploadFilePath, uuidFileName);
        if ("success".equals(result)) {
            return ResponseEntity.ok("파일 삭제 성공");
        } else if ("file not found".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패");
        }
    }

}
