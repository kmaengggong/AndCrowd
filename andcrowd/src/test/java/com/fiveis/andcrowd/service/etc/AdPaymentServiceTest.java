package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.AdPaymentDTO;
import com.fiveis.andcrowd.entity.etc.AdPayment;
import com.fiveis.andcrowd.service.etc.AdPaymentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdPaymentServiceTest {
    @Autowired
    AdPaymentService adPaymentService;

    @Test
    @BeforeEach
    @DisplayName("C: 각 테스트마다 임시 데이터 저장")
    public void saveTest(){
        // Given
        int adPaymentId = 1;
        int adId = 1;
        int userId = 1;
        int projectId = 1;
        int projectType = 0;
        LocalDateTime purchasedAt = LocalDateTime.now();
        LocalDateTime expiredAt = LocalDateTime.now();
        int adPaymentStatus = 0;

        AdPayment adPayment = AdPayment.builder()
                .adPaymentId(adPaymentId)
                .adId(adId)
                .userId(userId)
                .projectId(projectId)
                .projectType(projectType)
                .purchasedAt(purchasedAt)
                .expiredAt(expiredAt)
                .adPaymentStatus(adPaymentStatus)
                .build();

        // When
        adPaymentService.save(adPayment);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 광고 결제 내역 조회")
    public void findAllTest(){
        // Given
        int size = 1;

        // When
        List<AdPaymentDTO.Find> findList = adPaymentService.findAll();

        // Then
        Assertions.assertEquals(size, findList.size());
    }

    @Test
    @Transactional
    @DisplayName("R: 1번 광고 결제 내역 조회")
    public void findByIdTest(){
        // Given
        int adPaymentId = 1;

        // When
        AdPaymentDTO.Find find = adPaymentService.findById(adPaymentId);

        // Then
        Assertions.assertEquals(adPaymentId, find.getAdPaymentId());
    }

    @Test
    @Transactional
    @DisplayName("U: 1번 광고 결제 내역 변경 후 조회")
    public void updateTest(){
        // Given
        int adPaymentId = 1;
        LocalDateTime expiredAt = LocalDateTime.now();
        int adPaymentStatus = 1;

        AdPaymentDTO.Update update = AdPaymentDTO.Update.builder()
                .adPaymentId(1)
                .expiredAt(expiredAt)
                .adPaymentStatus(adPaymentStatus)
                .build();

        // When
        adPaymentService.update(update);
        AdPaymentDTO.Find find = adPaymentService.findById(adPaymentId);

        // Then
        Assertions.assertEquals(expiredAt, find.getExpiredAt());
        Assertions.assertEquals(adPaymentStatus, find.getAdPaymentStatus());
    }

    @Test
    @Transactional
    @DisplayName("D: 1번 광고 결제 내역 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int adPaymentId = 1;

        // When
        adPaymentService.deleteById(adPaymentId);

        // Given
        Assertions.assertNull(adPaymentService.findById(adPaymentId));
    }

    @Test
    @Transactional
    @DisplayName("1번 유저의 결제내역을 조회할경우 2개의 결제내역이 조회될것이다.")
    public void findAllByUserId(){
        //given
        int userId = 1;

        //when
        List<AdPaymentDTO.Find> adPaymentListByUserId = adPaymentService.findAllByUserId(userId);

        //then
        assertThat(adPaymentListByUserId.size()).isEqualTo(2);
    }
}
