package com.fiveis.andcrowd.service.and;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.fiveis.andcrowd.dto.and.FileUploadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileService implements FileService {

    // AmazonS3 주입받기
    private final AmazonS3Client amazonS3Client;

    // S3 bucket 이름
    @Value("${cloud.aws.s3.chat-bucket}")
    private String chatBucket;

    // S3 base URL
    @Value("${cloud.aws.s3.endpoint}")
    private String baseUrl;


    // MultipartFile 과 transcation, roomId 를 전달받는다.
    // 이때 transcation 는 파일 이름 중복 방지를 위한 UUID 를 의미한다.
    @Override
    public FileUploadDTO uploadFile(MultipartFile file, String transaction, String roomId) {
        try{

            String filename = file.getOriginalFilename(); // 파일원본 이름
            String key = roomId+"/"+transaction+"/"+filename; // S3 파일 경로

            // 매개변수로 넘어온 multipartFile 을 File 객체로 변환 시켜서 저장하기 위한 메서드
            File convertedFile = convertMultipartFileToFile(file, transaction + filename);

            // 아마존 S3 에 파일 업로드를 위해 사용하는 TransferManagerBuilder
            TransferManager transferManager = TransferManagerBuilder
                    .standard()
                    .withS3Client(amazonS3Client)
                    .build();

            PutObjectRequest putObjectRequest = new PutObjectRequest(chatBucket, key, convertedFile);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // ACL 설정

            // chatBucket 에 key 와 convertedFile 을 이용해서 파일 업로드
            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForUploadResult();

            // 변환된 File 객체 삭제
            removeFile(convertedFile);

            // uploadDTO 객체 빌드
            FileUploadDTO uploadReq = FileUploadDTO.builder()
                    .transaction(transaction)
                    .chatRoom(roomId)
                    .originFileName(filename)
                    .fileDir(key)
                    .s3DataUrl(baseUrl+"/"+chatBucket+"/"+key)
//                    .s3DataUrl(" https://"+chatBucket+"."+baseUrl+"/"+key)
                    .build();


            // uploadDTO 객체 리턴
            return uploadReq;

        } catch (Exception e) {
            log.error("fileUploadException {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteFileDir(String path) {
        for (S3ObjectSummary summary : amazonS3Client.listObjects(chatBucket, path).getObjectSummaries()) {
            amazonS3Client.deleteObject(chatBucket, summary.getKey());
        }
    }

    // byte 배열 타입을 return 한다.

    @Override
    public ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException {
        // chatBucket 와 fileDir 을 사용해서 S3 에 있는 객체 - object - 를 가져온다.
        S3Object object = amazonS3Client.getObject(new GetObjectRequest(chatBucket, fileDir));

        // object 를 S3ObjectInputStream 형태로 변환
        S3ObjectInputStream objectInputStream = object.getObjectContent();

        // 이후 다시 byte 배열 형태로 변환
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        // 여기는 httpHeader 에 파일 다운로드 요청을 하기 위한내용
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 지정된 fileName 으로 파일이 다운로드
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        log.info("HttpHeader : [{}]", httpHeaders);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

}
