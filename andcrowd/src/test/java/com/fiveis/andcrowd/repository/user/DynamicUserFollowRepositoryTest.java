package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserFollowDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserFollow;
import com.fiveis.andcrowd.repository.user.DynamicUserFollowRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserFollowRepositoryTest {
    @Autowired
    DynamicUserFollowRepository dynamicUserFollowRepository;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @BeforeEach
    @DisplayName("C: 테이블 생성")
    public void createDynamicUserFollowTableTest() {
        // Given
        // When
        dynamicUserFollowRepository.createDynamicUserFollowTable(userEmail);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 팔로우 유저 조회")// 후 프로필 이미지, 닉네임 가져옴")
    public void findAllTest(){
        // Given
        // When
        List<DynamicUserFollowDTO.Find> findList = dynamicUserFollowRepository.findAll(userEmail);

        // Then
        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CR: 1번 유저 팔로우 후 조회")
    public void saveTest(){
        // Given
        int uFollowId = 1;
        int userId = 1;
        DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
                .uFollowId(uFollowId)
                .userId(userId)
                .build();

        // When
        dynamicUserFollowRepository.save(userEmail, dynamicUserFollow);
        DynamicUserFollowDTO.Find find = dynamicUserFollowRepository.findById(userEmail, uFollowId);

        // Then
        Assertions.assertEquals(uFollowId, find.getUFollowId());
    }

    @Test
    @Transactional
    @DisplayName("CRD: 1번 유저 팔로우 후 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int uFollowId = 1;
        int userId = 1;
        DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
                .uFollowId(uFollowId)
                .userId(userId)
                .build();

        // When
        dynamicUserFollowRepository.save(userEmail, dynamicUserFollow);
        dynamicUserFollowRepository.deleteById(userEmail, uFollowId);

        // Then
        Assertions.assertNull(dynamicUserFollowRepository.findById(userEmail, uFollowId));
    }
}
