//package com.fiveis.andcrowd.repository.user;
//
//import com.fiveis.andcrowd.dto.user.UserDTO;
//import com.fiveis.andcrowd.entity.user.User;
//import com.fiveis.andcrowd.enums.Role;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//public class UserJPARepositoryTest {
//    @Autowired
//    UserJPARepository userJPARepository;
//
//    @Test
//    @BeforeEach
//    @DisplayName("C: 1번 테스트 유저 저장")
//    public void saveTest(){
//        // Given
//        int userId = 1;
//        String userEmail = "asdf@gmail.com";
//        String userPassword = "qwer";
//        String userKorName = "김명호";
//        String userNickname = "NICK";
//        String userPhone = "010-1234-5678";
//        LocalDateTime userBirth = LocalDateTime.of(1997, 2, 14, 0, 0, 0);
//        LocalDateTime userRegister = LocalDateTime.now();
//        int userTos = 1;
//        int userPrivacy = 1;
//        int userMarketing = 1;
//        Role role = Role.ROLE_ADMIN;
//
//        // When
//        User user = User.builder()
//                .userId(userId)
//                .userEmail(userEmail)
//                .userPassword(userPassword)
//                .userKorName(userKorName)
//                .userNickname(userNickname)
//                .userPhone(userPhone)
//                .userBirth(userBirth)
//                .userRegister(userRegister)
//                .userTos(userTos)
//                .userPrivacy(userPrivacy)
//                .userMarketing(userMarketing)
//                .authority(role)
//                .build();
//
//        userJPARepository.save(user);
//
//        // Then
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("R: userName='김명호' 유저 조회")
//    public void findByUserKorNameTest(){
//        // Given
//        String userName = "김명호";
//
//        // When
//        List<UserDTO.FindAsUser> findList = userJPARepository.findAllByUserKorName(userName);
//
//        // Then
//        for(UserDTO.FindAsUser find : findList) {
//            Assertions.assertEquals(userName, find.getUserKorName());
//        }
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("R: userEmail='asdf@gmail.com' 유저 조회")
//    public void findByUserEmailTest(){
//        // Given
//        String userEmail = "asdf@gmail.com";
//
//        // When
//        User user = userJPARepository.findByUserEmail(userEmail).get();
//
//        // Then
//        Assertions.assertEquals(userEmail, user.getUserEmail());
//    }
//
////    @Test
////    @Transactional
////    @DisplayName("R: userNickname='NICK' 유저 조회")
////    public void findByUserNicknameTest(){
////        // Given
////        String userNickname = "NICK";
////
////        // When
////        UserDTO.FindAsPublic user = userJPARepository.findByUserNickname(userNickname).get();
////
////        // Then
////        Assertions.assertEquals(userNickname, user.getUserNickname());
////    }
//
//    @Test
//    @Transactional
//    @DisplayName("D: userEmail='asdf@gmail.com' 유저 삭제")
//    public void deleteByUserEmailTest(){
//        // Given
//        String userEmail = "asdf@gmail.com";
//
//        // When
//        userJPARepository.deleteByUserEmail(userEmail);
//
//        // Then
//        Assertions.assertEquals(Optional.empty(), userJPARepository.findByUserEmail(userEmail));
//    }
//}
