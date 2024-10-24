package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserAndDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import com.fiveis.andcrowd.repository.user.DynamicUserAndRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserAndRepositoryTest {
    @Autowired
    DynamicUserAndRepository dynamicUserAndRepository;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @BeforeEach
    public void createDyanamicUserAndTableTest(){
        // Given
        // When
        dynamicUserAndRepository.createDynamicUserAndTable(userEmail);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("CR: 1번 모임글 추가 후 조회")
    public void saveTest(){
        // Given
        int uAndId = 1;
        int andId = 1;
        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
                .uAndId(uAndId)
                .andId(andId)
                .build();

        // When
        dynamicUserAndRepository.save(userEmail, dynamicUserAnd);
        DynamicUserAndDTO.Find find = dynamicUserAndRepository.findById(userEmail, uAndId);

        // Then
        Assertions.assertEquals(uAndId, find.getUAndId());
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 참여 모임글 조회")
    public void findAllTest(){
        // Given
        // When
        List<DynamicUserAndDTO.Find> findList = dynamicUserAndRepository.findAll(userEmail);

        // Then
        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CRD: 1번 글 추가 후 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int uAndId = 1;
        int andId = 1;
        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
                .uAndId(uAndId)
                .andId(andId)
                .build();

        // When
        dynamicUserAndRepository.save(userEmail, dynamicUserAnd);
        dynamicUserAndRepository.deleteById(userEmail, uAndId);

        // Then
        Assertions.assertNull(dynamicUserAndRepository.findById(userEmail, uAndId));
    }
}
