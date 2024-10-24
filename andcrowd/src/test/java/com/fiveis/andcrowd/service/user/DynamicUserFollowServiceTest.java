package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserFollowDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserFollow;
import com.fiveis.andcrowd.service.user.DynamicUserFollowService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserFollowServiceTest {
    @Autowired
    DynamicUserFollowService dynamicUserFollowService;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @Test
    @Transactional
    @DisplayName("CR")
    public void findAllTest(){
        String userNickname = "NICK";
        int uFollowId = 1;
        int userId = 1;
        DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
                .uFollowId(uFollowId)
                .userId(userId)
                .build();

        // When
        dynamicUserFollowService.save(userEmail, dynamicUserFollow);

        List<UserDTO.FindAsPublic> findList = dynamicUserFollowService.findAll(userEmail);

        // Then
        for(UserDTO.FindAsPublic find : findList){
            Assertions.assertEquals(userNickname, find.getUserNickname());
        }
    }

    @Test
    @Transactional
    @DisplayName("CR")
    public void saveTest(){
        // Given
        int uFollowId = 1;
        int userId = 1;
        DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
                .uFollowId(uFollowId)
                .userId(userId)
                .build();

        // When
        dynamicUserFollowService.save(userEmail, dynamicUserFollow);
        DynamicUserFollowDTO.Find find = dynamicUserFollowService.findById(userEmail, uFollowId);

        // Then
        Assertions.assertEquals(uFollowId, find.getUFollowId());
    }

    @Test
    @Transactional
    @DisplayName("CRD")
    public void deleteByIdTest(){
        // Given
        int uFollowId = 1;
        int userId = 1;
        DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
                .uFollowId(uFollowId)
                .userId(userId)
                .build();

        // When
        dynamicUserFollowService.save(userEmail, dynamicUserFollow);
        dynamicUserFollowService.deleteById(userEmail, uFollowId);

        // Then
        Assertions.assertNull(dynamicUserFollowService.findById(userEmail, uFollowId));
    }
}
