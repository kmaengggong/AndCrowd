package com.fiveis.andcrowd.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserS3DTO {
    private String originalFilename;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;
}
