package com.fiveis.andcrowd.dto.user;

import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

public class UserDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo{
        private int userId;
        private String userKorName;
        private String userNickname;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAsPublic{
        private String userNickname;
        private String userProfileImg;
        private LocalDateTime userRegister;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAsUser{
        private int userId;
        private String userEmail;
        private String userKorName;
        private String userNickname;
        private String userPhone;
        private String userProfileImg;
        private LocalDateTime userBirth;
        private LocalDateTime userRegister;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAsAdmin{
        private int userId;
        private String userEmail;
        private String userPassword;
        private String userKorName;
        private String userNickname;
        private String userPhone;
        private String userProfileImg;
        private LocalDateTime userBirth;
        private LocalDateTime userRegister;
        private int userTos;
        private int userPrivacy;
        private int userMarketing;
        private Role role;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{
        private String userEmail;
        private String userPassword;
        private String userNickname;
        private String userPhone;
        private String userProfileImg;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login{
        private String userEmail;
        private String userPassword;
    }

    // Entity -> DTO Converter
    public static UserDTO.FindAsPublic convertToFindAsPublicDTO(User user){
        return UserDTO.FindAsPublic.builder()
                .userNickname(user.getUserNickname())
                .userProfileImg(user.getUserProfileImg())
                .build();
    }

    public static UserDTO.FindAsUser convertToFindAsUserDTO(User user){
        return UserDTO.FindAsUser.builder()
                .userEmail(user.getUserEmail())
                .userKorName(user.getUserKorName())
                .userNickname(user.getUserNickname())
                .userPhone(user.getUserPhone())
                .userProfileImg(user.getUserProfileImg())
                .userBirth(user.getUserBirth())
                .userRegister(user.getUserRegister())
                .build();
    }

    public static UserDTO.FindAsAdmin convertToFindAsAdminDTO(User user){
        return UserDTO.FindAsAdmin.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userKorName(user.getUserKorName())
                .userNickname(user.getUserNickname())
                .userPhone(user.getUserPhone())
                .userProfileImg(user.getUserProfileImg())
                .userBirth(user.getUserBirth())
                .userRegister(user.getUserRegister())
                .userTos(user.getUserTos())
                .userPrivacy(user.getUserPrivacy())
                .userMarketing(user.getUserMarketing())
                .role(user.getRole())
                .build();
    }
}
