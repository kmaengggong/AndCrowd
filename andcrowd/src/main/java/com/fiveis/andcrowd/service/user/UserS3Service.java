package com.fiveis.andcrowd.service.user;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fiveis.andcrowd.dto.user.UserS3DTO;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserS3Service {
    private final AmazonS3Client amazonS3Client;
    private final UserJPARepository userJPARepository;

    @Value("${cloud.aws.s3.user-bucket}")
    private String userBucket;

    @Transactional
    public UserS3DTO uploadFile(int userId, String fileType, MultipartFile multipartFile){
        String uploadFilePath = userId + "/" + fileType;
        UserS3DTO s3file = null;

        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()){
            String keyName = uploadFilePath + uploadFileName;

            amazonS3Client.putObject(
                    new PutObjectRequest(userBucket, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            uploadFileUrl = amazonS3Client.getUrl(userBucket, keyName).toString();

            User user = userJPARepository.findByUserId(userId).get();
            user.setUserProfileImg(uploadFileUrl);

            s3file = UserS3DTO.builder()
                    .originalFilename(originalFileName)
                    .uploadFileName(uploadFileName)
                    .uploadFilePath(uploadFilePath)
                    .uploadFileUrl(uploadFileUrl)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return s3file;
    }

    public String getUuidFileName(String fileName){
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }
}
