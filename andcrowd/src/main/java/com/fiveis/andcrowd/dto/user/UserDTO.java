package com.fiveis.andcrowd.dto.user;

import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.enums.Authority;
import lombok.*;

import java.time.LocalDateTime;

public class UserDTO {
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
        private String userEmail;
        private String userPassword;
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
        private Authority authority;
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

    // Entity -> DTO Converter
    public static UserDTO.FindAsPublic toFindAsPublicDTO(User user){
        return UserDTO.FindAsPublic.builder()
                .userNickname(user.getUserNickname())
                .userProfileImg(user.getUserProfileImg())
                .build();
    }

    public static UserDTO.FindAsUser toFindAsUserDTO(User user){
        return UserDTO.FindAsUser.builder()
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userKorName(user.getUserKorName())
                .userNickname(user.getUserNickname())
                .userPhone(user.getUserPhone())
                .userProfileImg(user.getUserProfileImg())
                .userBirth(user.getUserBirth())
                .userRegister(user.getUserRegister())
                .build();
    }

    public static UserDTO.FindAsAdmin toFindAsAdminDTO(User user){
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
                .authority(user.getAuthority())
                .build();
    }

    public static UserDTO.Update toUpdateDTO(User user){
        return UserDTO.Update.builder()
                .userPassword(user.getUserPassword())
                .userNickname(user.getUserNickname())
                .userPhone(user.getUserPhone())
                .userProfileImg(user.getUserProfileImg())
                .build();
    }
}
