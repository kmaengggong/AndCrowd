package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserAndDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import com.fiveis.andcrowd.service.user.DynamicUserAndService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserAndServiceTest {
    @Autowired
    DynamicUserAndService dynamicUserAndService;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @Test
    @Transactional
    @DisplayName("R")
    public void findAllTest(){
        // Given
        // When
        List<?> findList = dynamicUserAndService.findAll(userEmail);

        // Then
//        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CR")
    public void saveTest(){
        // Given
        int uAndId = 1;
        int andId = 1;
        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
                .uAndId(uAndId)
                .andId(andId)
                .build();

        // When
        dynamicUserAndService.save(userEmail, dynamicUserAnd);
        DynamicUserAndDTO.Find find = dynamicUserAndService.findById(userEmail, uAndId);


        // Then
        Assertions.assertEquals(uAndId, find.getUAndId());
    }

    @Test
    @Transactional
    @DisplayName("CRD")
    public void deleteByIdTest(){
        // Given
        int uAndId = 1;
        int andId = 1;
        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
                .uAndId(uAndId)
                .andId(andId)
                .build();

        // When
        dynamicUserAndService.save(userEmail, dynamicUserAnd);
        dynamicUserAndService.deleteById(userEmail, uAndId);

        // Then
        Assertions.assertNull(dynamicUserAndService.findById(userEmail, uAndId));
    }
}
