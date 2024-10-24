package com.fiveis.andcrowd.service.etc;


import com.fiveis.andcrowd.dto.etc.AdDTO;
import com.fiveis.andcrowd.entity.etc.Ad;
import com.fiveis.andcrowd.service.etc.AdService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdServiceTest {
    @Autowired
    AdService adService;

    @Test
    @BeforeEach
    @DisplayName("C: 각 테스트마다 임시 데이터 저장")
    public void saveTest(){
        // Given
        int adId = 1;
        String adName = "광고1";
        int adPrice = 10000;

        Ad ad = Ad.builder()
                .adId(adId)
                .adName(adName)
                .adPrice(adPrice)
                .build();

        // When
        adService.save(ad);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 광고 조회")
    public void findAllTest(){
        // Given
        int size = 1;

        // When
        List<AdDTO.Find> findList = adService.findAll();

        // Then
        Assertions.assertEquals(size, findList.size());
    }

    @Test
    @Transactional
    @DisplayName("R: 1번 광고 조회")
    public void findByIdTest(){
        // Given
        int adId = 1;

        // When
        AdDTO.Find find = adService.findById(adId);

        // Then
        Assertions.assertEquals(adId, find.getAdId());
    }

    @Test
    @Transactional
    @DisplayName("U: 1번 광고 변경 후 조회")
    public void updateTest(){
        // Given
        int adId = 1;
        String adName = "수정된 광고";

        AdDTO.Update update = AdDTO.Update.builder()
                .adId(adId)
                .adName(adName)
                .adPrice(adService.findById(adId).getAdPrice())
                .build();

        // When
        adService.update(update);

        // Then
        Assertions.assertEquals(adName, adService.findById(adId).getAdName());
    }

    @Test
    @Transactional
    @DisplayName("D: 1번 광고 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int adId = 1;

        // When
        adService.deleteById(adId);

        // Given
        Assertions.assertNull(adService.findById(adId));
    }
}
