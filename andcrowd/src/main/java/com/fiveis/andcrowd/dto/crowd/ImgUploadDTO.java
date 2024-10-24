package com.fiveis.andcrowd.dto.crowd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImgUploadDTO {

    private MultipartFile file; // MultipartFile

    private String originFileName; // 파일 원본 이름

    private String transaction; // UUID 를 활용한 랜덤한 파일 위치

    private int crowdId; // 파일이 올라간 crowdId

    private String s3DataUrl; // 파일 링크

    private String fileDir; // S3 파일 경로
}
