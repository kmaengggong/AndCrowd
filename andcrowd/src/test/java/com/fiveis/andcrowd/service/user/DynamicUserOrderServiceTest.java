package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserOrderDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserOrder;
import com.fiveis.andcrowd.service.user.DynamicUserOrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserOrderServiceTest {
    @Autowired
    DynamicUserOrderService dynamicUserOrderService;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @Test
    @Transactional
    @DisplayName("R")
    public void findAllTest(){
        // Given
        // When
        List<?> findList = dynamicUserOrderService.findAll(userEmail);

        // Then
//        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CR")
    public void saveTest(){
        // Given
        int uOrderId = 1;
        int orderId = 1;
        DynamicUserOrder dynamicUserOrder = DynamicUserOrder.builder()
                .uOrderId(uOrderId)
                .orderId(orderId)
                .build();

        // When
        dynamicUserOrderService.save(userEmail, dynamicUserOrder);
        DynamicUserOrderDTO.Find find = dynamicUserOrderService.findById(userEmail, uOrderId);


        // Then
        Assertions.assertEquals(uOrderId, find.getUOrderId());
    }

    @Test
    @Transactional
    @DisplayName("CRD")
    public void deleteByIdTest(){
        // Given
        int uOrderId = 1;
        int orderId = 1;
        DynamicUserOrder dynamicUserOrder = DynamicUserOrder.builder()
                .uOrderId(uOrderId)
                .orderId(orderId)
                .build();

        // When
        dynamicUserOrderService.save(userEmail, dynamicUserOrder);
        dynamicUserOrderService.deleteById(userEmail, uOrderId);

        // Then
        Assertions.assertNull(dynamicUserOrderService.findById(userEmail, uOrderId));
    }
}
