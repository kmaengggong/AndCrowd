package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.entity.etc.Ad;
import com.fiveis.andcrowd.repository.etc.AdJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdJPARepositoryTest {
    @Autowired
    AdJPARepository adJPARepository;

    @Test
    @BeforeEach
    @DisplayName("C: ")
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
        adJPARepository.save(ad);

        // Then
    }

    // JPA 이기 때문에 기타 기능 테스트 생략. 후에 기능 생기면 추가할 예정
}
