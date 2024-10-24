package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserMakerDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserMaker;
import com.fiveis.andcrowd.repository.user.DynamicUserMakerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserMakerRepositoryTest {
    @Autowired
    DynamicUserMakerRepository dynamicUserMakerRepository;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @BeforeEach
    public void createDynamicUserMakerTableTest(){
        // Given
        // When
        dynamicUserMakerRepository.createDynamicUserMakerTable(userEmail);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("CR: 1번 모임글 생성 후 조회")
    public void saveTest(){
        // Given
        int uMakerId = 1;
        int projectId = 1;
        int projectType = 0;
        DynamicUserMaker dynamicUserMaker = DynamicUserMaker.builder()
                .uMakerId(uMakerId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserMakerRepository.save(userEmail, dynamicUserMaker);
        DynamicUserMakerDTO.Find find = dynamicUserMakerRepository.findById(userEmail, uMakerId);

        // Then
        Assertions.assertEquals(uMakerId, find.getUMakerId());
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 생성글 조회")
    public void findAllTest(){
        // Given
        // When
        List<DynamicUserMakerDTO.Find> findList = dynamicUserMakerRepository.findAll(userEmail);

        // Then
        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CRD: 1번 모임글 생성 후 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int uMakerId = 1;
        int projectId = 1;
        int projectType = 0;
        DynamicUserMaker dynamicUserMaker = DynamicUserMaker.builder()
                .uMakerId(uMakerId)
                .projectId(projectId)
                .projectType(projectType)
                .build();

        // When
        dynamicUserMakerRepository.save(userEmail, dynamicUserMaker);
        dynamicUserMakerRepository.deleteById(userEmail, uMakerId);

        // Then
        Assertions.assertNull(dynamicUserMakerRepository.findById(userEmail, uMakerId));
    }
}
