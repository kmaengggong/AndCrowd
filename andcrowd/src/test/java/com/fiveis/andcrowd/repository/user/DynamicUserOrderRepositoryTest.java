package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserOrderDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserOrder;
import com.fiveis.andcrowd.repository.user.DynamicUserOrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DynamicUserOrderRepositoryTest {
    @Autowired
    DynamicUserOrderRepository dynamicUserOrderRepository;

    String userEmail = "asdf@gmail.com".replace('@', '_').replace('.', '_');

    @BeforeEach
    public void createDynamicUserOrderTableTest(){
        // Given
        // When
        dynamicUserOrderRepository.createDynamicUserOrderTable(userEmail);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("CR: 1번 주문 후 조회")
    public void saveTest(){
        // Given
        int uOrderId = 1;
        int orderId = 1;
        DynamicUserOrder dynamicUserOrder = DynamicUserOrder.builder()
                .uOrderId(uOrderId)
                .orderId(orderId)
                .build();

        // When
        dynamicUserOrderRepository.save(userEmail, dynamicUserOrder);
        DynamicUserOrderDTO.Find find = dynamicUserOrderRepository.findById(userEmail, uOrderId);

        // Then
        Assertions.assertEquals(uOrderId, find.getUOrderId());
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 주문내역 조회")
    public void findAllTest(){
        // Given
        // When
        List<DynamicUserOrderDTO.Find> findList = dynamicUserOrderRepository.findAll(userEmail);

        // Then
        Assertions.assertTrue(findList.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("CRD: 1번 주문 후 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int uOrderId = 1;
        int orderId = 1;
        DynamicUserOrder dynamicUserOrder = DynamicUserOrder.builder()
                .uOrderId(uOrderId)
                .orderId(orderId)
                .build();

        // When
        dynamicUserOrderRepository.save(userEmail, dynamicUserOrder);
        dynamicUserOrderRepository.deleteById(userEmail, uOrderId);

        // Then
        Assertions.assertNull(dynamicUserOrderRepository.findById(userEmail, uOrderId));
    }
}
