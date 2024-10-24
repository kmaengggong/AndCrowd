package com.fiveis.andcrowd.service.crowd;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.fiveis.andcrowd.dto.crowd.ImgUploadDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class CrowdS3FileService implements CrowdFileService{

    private final AmazonS3Client amazonS3Client; // Amazon S3 주입

    @Value("${cloud.aws.s3.crowd-bucket}")
    private String crowdBucket;

    @Value("${cloud.aws.s3.endpoint}")
    private String baseUrl;

    @Override
    public ImgUploadDTO uploadFile(MultipartFile file, String transaction, int crowdId) {
        try {
            String fileName = file.getOriginalFilename(); // 파일원본명
            String key = crowdId + "/" + transaction + "/" + fileName; // crowdId 자동 형변환(s3파일 경로)

            File convertedFile = convertMultipartFileToFile(file, transaction + fileName);

            TransferManager transferManager = TransferManagerBuilder
                    .standard()
                    .withS3Client(amazonS3Client)
                    .build();

            PutObjectRequest putObjectRequest = new PutObjectRequest(crowdBucket, key, convertedFile);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // ACL(Access Control List) 설정

            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForUploadResult();

            removeFile(convertedFile);

            ImgUploadDTO uploadReq = ImgUploadDTO.builder()
                    .transaction(transaction)
                    .crowdId(crowdId)
                    .originFileName(fileName)
                    .fileDir(key)
                    .s3DataUrl(baseUrl + "/" + crowdBucket + "/" + key)
                    .build();

            return uploadReq;
        } catch (Exception e){
            log.error("fileUploadException: ", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteFileDir(String path) {
        for(S3ObjectSummary summary : amazonS3Client.listObjects(crowdBucket, path).getObjectSummaries()){
            amazonS3Client.deleteObject(crowdBucket, summary.getKey());
        }
    }

    @Override
    public ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(crowdBucket, fileDir));

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        httpHeaders.setContentDispositionFormData("attachment", fileName);
        log.info("HttpHeader: [{}]", httpHeaders);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
}
