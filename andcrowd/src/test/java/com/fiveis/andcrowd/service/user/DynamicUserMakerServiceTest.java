package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserMakerDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserMaker;
import com.fiveis.andcrowd.service.user.DynamicUserMakerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserMakerServiceTest {
    @Autowired
    DynamicUserMakerService dynamicUserMakerService;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @Test
    @Transactional
    @DisplayName("R")
    public void findAllTest() {
        // Given
        // When
        List<?> findList = dynamicUserMakerService.findAll(userEmail);

        // Then
//        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CR")
    public void saveTest(){
        // Given
        int uMakerId = 1;
        int projectId = 1;
        int projectType = 1;
        DynamicUserMaker dynamicUserMaker = DynamicUserMaker.builder()
                .uMakerId(uMakerId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserMakerService.save(userEmail, dynamicUserMaker);
        DynamicUserMakerDTO.Find find = dynamicUserMakerService.findById(userEmail, uMakerId);

        // Then
        Assertions.assertEquals(uMakerId, find.getUMakerId());
    }

    @Test
    @Transactional
    @DisplayName("CRD")
    public void deleteByIdTest(){
        // Given
        int uMakerId = 1;
        int projectId = 1;
        int projectType = 1;
        DynamicUserMaker dynamicUserMaker = DynamicUserMaker.builder()
                .uMakerId(uMakerId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserMakerService.save(userEmail, dynamicUserMaker);
        dynamicUserMakerService.deleteById(userEmail, uMakerId);

        // Then
        Assertions.assertNull(dynamicUserMakerService.findById(userEmail, uMakerId));
    }
}
