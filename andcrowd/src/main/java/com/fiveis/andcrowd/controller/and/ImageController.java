//package com.fiveis.andcrowd.controller.and;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Random;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//public class ImageController {
//
//    String UPLOAD_PATH = "D:\\\\myUpload";
//
//    @GetMapping("/getImage/{fileId}/{fileType}")
//    public ResponseEntity<byte[]> getImageFile(@PathVariable String fileId, @PathVariable String fileType) {
//
//        try {
//            FileInputStream fis = new FileInputStream(UPLOAD_PATH + "\\" + fileId + "." + fileType);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            byte buffer[] = new byte[1024];
//            int length = 0;
//
//            while((length = fis.read(buffer)) != -1) {
//                baos.write(buffer, 0, length);
//            }
//
//            return new ResponseEntity<byte[]>(baos.toByteArray(), HttpStatus.OK);
//
//        } catch(IOException e) {
//            return new ResponseEntity<byte[]>(new byte[] {}, HttpStatus.CONFLICT);
//        }
//    }
//
//
//    @PostMapping("/uploadImage")
//    public ResponseEntity<Object> uploadImage(MultipartFile multipartFiles[]) {
//        try {
//            MultipartFile file = multipartFiles[0];
//
//            String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
//            String originName = file.getOriginalFilename();
//            String fileExtension = originName.substring(originName.lastIndexOf(".") + 1);
//            originName = originName.substring(0, originName.lastIndexOf("."));
//            long fileSize = file.getSize();
//
//            File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension);
//            if(!fileSave.exists()) {
//                fileSave.mkdirs();
//            }
//
//            file.transferTo(fileSave);
//
//            System.out.println("fileId= " + fileId);
//            System.out.println("originName= " + originName);
//            System.out.println("fileExtension= " + fileExtension);
//            System.out.println("fileSize= " + fileSize);
//
//            return new ResponseEntity<Object>("http://localhost:8080/getImage/" + fileId + "/" + fileExtension, HttpStatus.OK);
//        } catch(IOException e) {
//            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
//        }
//    }
//}
