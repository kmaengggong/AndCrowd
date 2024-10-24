package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.ImgUploadDTO;
import com.fiveis.andcrowd.service.crowd.CrowdS3FileService;
import com.fiveis.andcrowd.service.crowd.CrowdS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/_s3")
@Slf4j
//@CrossOrigin(origins = "http://localhost:3000")
public class CrowdFileController {
    @Autowired
    private CrowdS3FileService crowdS3FileService;

    @PostMapping("/upload")
    public ImgUploadDTO uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("crowdId") int crowdId){

        ImgUploadDTO fileReq = crowdS3FileService.uploadFile(file, UUID.randomUUID().toString(), crowdId);
        log.info("최종 upload Data {}", fileReq);

        // fileReq 객체 리턴
        return fileReq;
    }

    // get 으로 요청이 오면 아래 download 메서드를 실행한다.
    // fileName 과 파라미터로 넘어온 fileDir 을 getObject 메서드에 매개변수로 넣는다.
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName, @RequestParam("fileDir")String fileDir) {
        log.info("fileDir : fileName [{} : {}]", fileDir, fileName);
        try {
            // 변환된 byte, httpHeader 와 HttpStatus 가 포함된 ResponseEntity 객체를 return 한다.
            return crowdS3FileService.getObject(fileDir, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
