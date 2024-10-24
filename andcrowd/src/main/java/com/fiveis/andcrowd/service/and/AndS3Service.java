package com.fiveis.andcrowd.service.and;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fiveis.andcrowd.dto.and.AndS3DTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AndS3Service {

    private final AmazonS3Client amazonS3Client;
    private final AndJPARepository andJPARepository;

    @Value("${cloud.aws.s3.and-bucket}")
    private String andBucket;

    @Transactional
    public List<AndS3DTO> uploadFiles(int andId, String fileType, List<MultipartFile> multipartFiles){
        List<AndS3DTO> s3files = new ArrayList<>();
        String uploadFilePath = andId + "/" + fileType;

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {

                String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

                // S3에 폴더 및 파일 업로드
                amazonS3Client.putObject(
                    new PutObjectRequest(andBucket, keyName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = amazonS3Client.getUrl(andBucket, keyName).toString();

                // headerImg 저장
                And and = andJPARepository.findById(andId).get();
                and.setAndHeaderImg(uploadFileUrl);
                System.out.println("headerImg 저장" + and.getAndHeaderImg());

            } catch (IOException e) {
                e.printStackTrace();
                log.error("Filed upload failed", e);
            }

            s3files.add(
                    AndS3DTO.builder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uploadFileName)
                            .uploadFilePath(uploadFilePath)
                            .uploadFileUrl(uploadFileUrl)
                            .build());
        }

        return s3files;
    }

    @Transactional
    public AndS3DTO uploadFile(int andId, String fileType, MultipartFile multipartFile) {
        String uploadFilePath = andId + "/" + fileType;
        AndS3DTO s3file = null;

        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {

            String keyName = uploadFilePath + "/" + uploadFileName;

            // S3에 폴더 및 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(andBucket, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            // S3에 업로드한 폴더 및 파일 URL
            uploadFileUrl = amazonS3Client.getUrl(andBucket, keyName).toString();

            s3file = AndS3DTO.builder()
                    .originalFileName(originalFileName)
                    .uploadFileName(uploadFileName)
                    .uploadFilePath(uploadFilePath)
                    .uploadFileUrl(uploadFileUrl)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("File upload failed", e);
        }

        return s3file;
    }


    /**
     * S3에 업로드된 파일 삭제
     */
    public String deleteFile(String uploadFilePath, String uuidFileName) {

        String result = "success";

        try {
            String keyName = uploadFilePath + "/" + uuidFileName; // ex) 구분/년/월/일/파일.확장자
            boolean isObjectExist = amazonS3Client.doesObjectExist(andBucket, keyName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(andBucket, keyName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File failed", e);
        }

        return result;
    }

    /**
     * UUID 파일명 반환
     */
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

//    /**
//     * 년/월/일 폴더명 반환
//     */
//    private String getFolderName() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        Date date = new Date();
//        String str = sdf.format(date);
//        return str.replace("-", "/");
//    }

}
