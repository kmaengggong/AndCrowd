package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserLikeDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserLike;
import com.fiveis.andcrowd.service.user.DynamicUserLikeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserLikeServiceTest {
    @Autowired
    DynamicUserLikeService dynamicUserLikeService;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @Test
    @Transactional
    @DisplayName("R")
    public void findAllTest() {
        // Given


        // When
        List<ProjectDTO.Find> findList = dynamicUserLikeService.findAll(userEmail);

        // Then
//        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CR")
    public void saveTest(){
        // Given
        int uLikeId = 1;
        int projectId = 1;
        int projectType = 1;
        DynamicUserLike dynamicUserLike = DynamicUserLike.builder()
                .uLikeId(uLikeId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserLikeService.save(userEmail, dynamicUserLike);
        DynamicUserLikeDTO.Find find = dynamicUserLikeService.findById(userEmail, uLikeId);

        // Then
        Assertions.assertEquals(uLikeId, find.getULikeId());
    }

    @Test
    @Transactional
    @DisplayName("CRD")
    public void deleteByIdTest(){
        // Given
        int uLikeId = 1;
        int projectId = 1;
        int projectType = 1;
        DynamicUserLike dynamicUserLike = DynamicUserLike.builder()
                .uLikeId(uLikeId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserLikeService.save(userEmail, dynamicUserLike);
        dynamicUserLikeService.deleteById(userEmail, uLikeId);

        // Then
        Assertions.assertNull(dynamicUserLikeService.findById(userEmail, uLikeId));
    }
}
