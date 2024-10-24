package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.ImgUploadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public interface CrowdFileService {
    ImgUploadDTO uploadFile(MultipartFile file, String transaction, int crowdId);

    void deleteFileDir(String path);

    default File convertMultipartFileToFile(MultipartFile mfile, String tmpPath) throws IOException {
        File file = new File(tmpPath);

        if(file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(mfile.getBytes());
            }
            return file;
        }
        throw new IOException();
    }
    default void removeFile(File file) {
        file.delete();
    }
    ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException;
}
