package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserLikeDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserLike;
import com.fiveis.andcrowd.repository.user.DynamicUserLikeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserLikeRepositoryTest {
    @Autowired
    DynamicUserLikeRepository dynamicUserLikeRepository;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @BeforeEach
    public void createDynamicUserLikeTableTest(){
        // Given
        // When
        dynamicUserLikeRepository.createDynamicUserLikeTable(userEmail);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("CR: 1번 모임글 좋아요 후 조회")
    public void saveTest(){
        // Given
        int uLikeId = 1;
        int projectId = 1;
        int projectType = 0;
        DynamicUserLike dynamicUserLike = DynamicUserLike.builder()
                .uLikeId(uLikeId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserLikeRepository.save(userEmail, dynamicUserLike);
        DynamicUserLikeDTO.Find find = dynamicUserLikeRepository.findById(userEmail, uLikeId);

        // Then
        Assertions.assertEquals(uLikeId, find.getULikeId());
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 좋아요 한 글 조회")
    public void findAllTest(){
        // Given
        // When
        List<DynamicUserLikeDTO.Find> findList = dynamicUserLikeRepository.findAll(userEmail);

        // Then
        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CRD: 1번 모임글 좋아요 후 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int uLikeId = 1;
        int projectId = 1;
        int projectType = 0;
        DynamicUserLike dynamicUserLike = DynamicUserLike.builder()
                .uLikeId(uLikeId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserLikeRepository.save(userEmail, dynamicUserLike);
        dynamicUserLikeRepository.deleteById(userEmail, uLikeId);

        // Then
        Assertions.assertNull(dynamicUserLikeRepository.findById(userEmail, uLikeId));
    }
}
