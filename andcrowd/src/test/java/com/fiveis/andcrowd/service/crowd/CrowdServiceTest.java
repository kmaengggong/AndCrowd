package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CrowdServiceTest {
    @Autowired
    CrowdService crowdService;

    @Test
    @Transactional
    @DisplayName("crowdId 3번 조회시 3번 crowd글이 조회됨")
    public void findByCrowdIdTest() {
        // given
        int crowdId = 3;
        String crowdTitle = "2번펀딩";
        String crowdContent = "2번 본문";
        // when
        CrowdDTO.FindById findById = crowdService.findByCrowdId(crowdId).get();

        // then
        assertEquals(crowdId, findById.getCrowdId());
    }

    @Test
    @Transactional
    @DisplayName("crowd 전체데이터 조회시 3개가 조회될 것이다.")
    public void findAllTest() {
        // when
        List<CrowdDTO.FindById> findAllList = crowdService.findAll();
        // then
//        assertEquals(3, findAllList.size());
        assertThat(findAllList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("새로운 crowd를 업로드하고, 조회시 총 4개의 글이 조회됨")
    public void saveTest() {
        // given
        int crowdId = 5;
        int adId = 4;
        int andId = 4;
        String crowdContent = "4번 본문";
        int crowdGoal = 4000000;
        String crowdTitle = "4번펀딩";
        String headerImg = "4번헤더";
        Crowd saveCrowd = Crowd.builder()
                .crowdId(crowdId)
                .adId(adId)
                .andId(andId)
                .crowdContent(crowdContent)
                .crowdGoal(crowdGoal)
                .crowdTitle(crowdTitle)
                .headerImg(headerImg)
                .build();
        saveCrowd.setCrowdEndDate(LocalDateTime.now());

        // when
        crowdService.save(saveCrowd);
        List<CrowdDTO.FindById> findAllList = crowdService.findAll();

        // then
        assertEquals(crowdContent, saveCrowd.getCrowdContent());
        assertEquals(4, findAllList.size());
    }

}
