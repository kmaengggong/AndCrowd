package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    List<UserDTO.FindAsUser> findAllByUserKorName(String userKorName);
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(int userId);
    Optional<User> findByUserNickname(String userNickname);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
    void deleteByUserEmail(String userEmail);

//    @Query("SELECT u.userNickname FROM User u WHERE u.userId = :userId")
//    String findUserNicknameByUserId(@Param("userId") int userId);
}