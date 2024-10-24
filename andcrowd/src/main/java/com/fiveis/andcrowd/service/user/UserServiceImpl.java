package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserJPARepository userJPARepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DynamicUserAndRepository dynamicUserAndRepository;
    private final DynamicUserFollowRepository dynamicUserFollowRepository;
    private final DynamicUserLikeRepository dynamicUserLikeRepository;
    private final DynamicUserMakerRepository dynamicUserMakerRepository;
    private final DynamicUserOrderRepository dynamicUserOrderRepository;

    @Autowired
    public UserServiceImpl(UserJPARepository userJPARepository,
                           @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
                       DynamicUserAndRepository dynamicUserAndRepository,
                       DynamicUserFollowRepository dynamicUserFollowRepository,
                       DynamicUserLikeRepository dynamicUserLikeRepository,
                       DynamicUserMakerRepository dynamicUserMakerRepository,
                       DynamicUserOrderRepository dynamicUserOrderRepository){
        this.userJPARepository = userJPARepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dynamicUserAndRepository = dynamicUserAndRepository;
        this.dynamicUserFollowRepository = dynamicUserFollowRepository;
        this.dynamicUserLikeRepository = dynamicUserLikeRepository;
        this.dynamicUserMakerRepository = dynamicUserMakerRepository;
        this.dynamicUserOrderRepository = dynamicUserOrderRepository;
    }

    public List<UserDTO.FindAsPublic> findAllAsPublic(){
        List<User> userList = userJPARepository.findAll();
        List<UserDTO.FindAsPublic> userFindDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO.FindAsPublic dto = UserDTO.convertToFindAsPublicDTO(user);
            userFindDTOList.add(dto);
        }
        return userFindDTOList;
    }

    public List<UserDTO.FindAsAdmin> findAllAsAdmin(){
        List<User> userList = userJPARepository.findAll();
        List<UserDTO.FindAsAdmin> userFindDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO.FindAsAdmin dto = UserDTO.convertToFindAsAdminDTO(user);
            userFindDTOList.add(dto);
        }
        return userFindDTOList;
    }

    public List<UserDTO.FindAsUser> findAllByUserKorName(String userKorName){
        return userJPARepository.findAllByUserKorName(userKorName);
    }

    public UserDTO.FindAsAdmin findById(int userId){
        if(userJPARepository.findById(userId).isEmpty()) return null;
        return UserDTO.convertToFindAsAdminDTO(userJPARepository.findById(userId).get());
    }

    public User findByUserEmail(String userEmail){
        if(userJPARepository.findByUserEmail(userEmail).isEmpty()) return null;
        return userJPARepository.findByUserEmail(userEmail).get();
    }

    public UserDTO.FindAsPublic findByUserNickname(String userNickname){
        if(userJPARepository.findByUserNickname(userNickname).isEmpty()) return null;
        return UserDTO.convertToFindAsPublicDTO(userJPARepository.findByUserNickname(userNickname).get());
    }

    @Transactional
    public void deleteByUserEmail(String userEmail) {
        userJPARepository.deleteByUserEmail(userEmail);
    }

    @Transactional
    public void save(User user) throws Exception{
        if(userJPARepository.findByUserEmail(user.getUserEmail()).isPresent()){
            throw new Exception("Exception: Email already exist");
        }
        if(userJPARepository.findByUserNickname(user.getUserNickname()).isPresent()){
            throw new Exception("Exception: Nickname already exist");
        }

        if(user.getUserPassword() != null) user.passwordEncode(bCryptPasswordEncoder);
        System.out.println(user);
        userJPARepository.save(user);

        // User 생성 시 동적 테이블 자동 생성
        String userEmail = User.toTableName(user.getUserEmail());
        dynamicUserAndRepository.createDynamicUserAndTable(userEmail);
        dynamicUserFollowRepository.createDynamicUserFollowTable(userEmail);
        dynamicUserLikeRepository.createDynamicUserLikeTable(userEmail);
        dynamicUserMakerRepository.createDynamicUserMakerTable(userEmail);
        dynamicUserOrderRepository.createDynamicUserOrderTable(userEmail);
    }

    @Transactional
    public void update(UserDTO.Update userUpdateDTO) {
        User user = userJPARepository.findByUserEmail(userUpdateDTO.getUserEmail()).get();

        if(userUpdateDTO.getUserPassword() != null){
            user.setUserPassword(userUpdateDTO.getUserPassword());
            user.passwordEncode(bCryptPasswordEncoder);
        }
        if(userUpdateDTO.getUserNickname() != null){
            user.setUserNickname(userUpdateDTO.getUserNickname());
        }
        if(userUpdateDTO.getUserPhone() != null){
            user.setUserPhone(userUpdateDTO.getUserPhone());
        }
        if(userUpdateDTO.getUserProfileImg() != null){
            user.setUserProfileImg(userUpdateDTO.getUserProfileImg());
        }

        userJPARepository.save(user);
    }

    @Transactional
    public void udpateForSocial(User user){
        userJPARepository.save(user);
    }

    public User getByCredentials(String userEmail){
        if(userJPARepository.findByUserEmail(userEmail).isEmpty()) return null;
        return userJPARepository.findByUserEmail(userEmail).get();
    }
}
