package com.fiveis.andcrowd.service.crowd;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fiveis.andcrowd.dto.crowd.CrowdS3DTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.repository.crowd.CrowdJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrowdS3Service {

    private final AmazonS3Client amazonS3Client;
    private final CrowdJPARepository crowdJPARepository;

    @Value("${cloud.aws.s3.crowd-bucket}")
    private String crowdBucket;

    @Transactional
    public List<CrowdS3DTO> uploadFiles(int crowdId, String fileType, List<MultipartFile> files) {
        List<CrowdS3DTO> fileUrls = new ArrayList<>();
        String uploadFilePath = crowdId + "/" + fileType;

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {

                String keyName = uploadFilePath + "/" + uniqueFileName; // ex) 구분/년/월/일/파일.확장자

                // S3에 폴더 및 파일 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(crowdBucket, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = amazonS3Client.getUrl(crowdBucket, keyName).toString();

                // headerImg 저장
                Crowd crowd = crowdJPARepository.findById(crowdId).get();
                crowd.setHeaderImg(uploadFileUrl);
                System.out.println("headerImg 저장" + crowd.getHeaderImg());

            } catch (IOException e) {
                e.printStackTrace();
                log.error("Filed upload failed", e);
            }

            fileUrls.add(
                    CrowdS3DTO.builder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uniqueFileName)
                            .uploadFilePath(uploadFilePath)
                            .uploadFileUrl(uploadFileUrl)
                            .build());
        }
        return fileUrls;
    }

    @Transactional
    public CrowdS3DTO uploadFile(int crowdId, String fileType, MultipartFile multipartFile) {
        String uploadFilePath = crowdId + "/" + fileType;
        CrowdS3DTO s3file = null;

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
                    new PutObjectRequest(crowdBucket, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
            log.debug("S3 업로드 완료. 파일 경로: {}", keyName);
            log.debug("S3 업로드 완료. 파일 URL: {}", uploadFileUrl);

            // S3에 업로드한 폴더 및 파일 URL
            uploadFileUrl = amazonS3Client.getUrl(crowdBucket, keyName).toString();

            s3file = CrowdS3DTO.builder()
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

    public String deleteFiles(String uploadFilePath, String uuidFileName) {
        String result = "success";

        try{
            String keyName = uploadFilePath + "/" + uuidFileName;
            boolean isObjectExist = amazonS3Client.doesObjectExist(crowdBucket, keyName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(crowdBucket, keyName);
            } else {
                result = "file not found";
            }
        } catch(Exception e){
            log.debug("Delete File failed", e);
        }
        return result;
    }

    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }
}

