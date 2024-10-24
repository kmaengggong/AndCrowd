package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserFollowDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserFollow;
import com.fiveis.andcrowd.repository.user.DynamicUserFollowRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicUserFollowServiceImpl implements DynamicUserFollowService{
    private final DynamicUserFollowRepository dynamicUserFollowRepository;
    private final UserJPARepository userJPARepository;

    @Autowired
    public DynamicUserFollowServiceImpl(DynamicUserFollowRepository dynamicUserFollowRepository,
                                        UserJPARepository userJPARepository){
        this.dynamicUserFollowRepository = dynamicUserFollowRepository;
        this.userJPARepository = userJPARepository;
    }

    public List<UserDTO.FindAsPublic> findAll(String userEmail){
        List<DynamicUserFollowDTO.Find> findList = dynamicUserFollowRepository.findAll(userEmail);
        List<UserDTO.FindAsPublic> userList = new ArrayList<>();
        for(DynamicUserFollowDTO.Find find : findList){
            if(userJPARepository.findById(find.getUserId()).isEmpty()) continue;
            userList.add(UserDTO.convertToFindAsPublicDTO(userJPARepository.findById(find.getUserId()).get()));
        }
        return userList;
    }

    public DynamicUserFollowDTO.Find findById(String userEmail, int uFollowId){
        return dynamicUserFollowRepository.findById(userEmail, uFollowId);
    }

    @Override
    public DynamicUserFollowDTO.Find findByUserId(String userEmail, int userId) {
        return dynamicUserFollowRepository.findByUserId(userEmail, userId);
    }

    public boolean save(String userEmail, DynamicUserFollow dynamicUserFollow){
        // 존재하지 않는 userId
        if(userJPARepository.findByUserId(dynamicUserFollow.getUserId()).isEmpty()) {System.out.println("먼데씨발"); return false;}
        // user_follow에 이미 존재
        if(dynamicUserFollowRepository.findByUserId(userEmail, dynamicUserFollow.getUserId()) != null) return false;
        // 그 외에는 저장 성공
        dynamicUserFollowRepository.save(userEmail, dynamicUserFollow);
        return true;
    }

    public void deleteById(String userEmail, int uFollowId){
        dynamicUserFollowRepository.deleteById(userEmail, uFollowId);
    }

    public void deleteByUserId(String userEmail, int userId){
        dynamicUserFollowRepository.deleteByUserId(userEmail, userId);
    }

    public void deleteTableByUserEmail(String userEmail){
        dynamicUserFollowRepository.deleteTableByUserEmail(userEmail);
    }
}
