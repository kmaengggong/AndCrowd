package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDTO.FindAsPublic> findAllAsPublic();
    List<UserDTO.FindAsAdmin> findAllAsAdmin();
    List<UserDTO.FindAsUser> findAllByUserKorName(String userKorName);
    UserDTO.FindAsAdmin findById(int userId);
    User findByUserEmail(String userEmail);
    UserDTO.FindAsPublic findByUserNickname(String userNickname);
    void deleteByUserEmail(String userEmail);
    void save(User user) throws Exception;
    void update(UserDTO.Update user);
    void udpateForSocial(User user);
    User getByCredentials(String userEmail);
}