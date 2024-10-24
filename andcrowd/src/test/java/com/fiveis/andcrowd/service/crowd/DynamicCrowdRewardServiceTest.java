//package com.fiveis.andcrowd.service.crowd;
//
//import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class DynamicCrowdRewardServiceTest {
//    @Autowired
//    DynamicCrowdRewardService dynamicCrowdRewardService;
//
//    @Test
//    @Transactional
//    @DisplayName("Reward글 정상등록 확인")
//    public void createDynamicCrowdRewardTable(){
//        // given
//        int crowdId = 456;
//        int rewardId = 4;
//        String rewardTitle = "추가제목";
//        String rewardContent = "추가본문";
//        int rewardAmount = 10000;
//        int rewardLimit = 5;
//
//        // when
//        dynamicCrowdRewardService.createDynamicCrowdRewardTable();
//        DynamicCrowdRewardDTO.Update newReward = new DynamicCrowdRewardDTO.Update();
//        newReward.setCrowdId(crowdId);
//        newReward.setRewardId(rewardId);
//        newReward.setRewardTitle(rewardTitle);
//        newReward.setRewardContent(rewardContent);
//        newReward.setRewardAmount(rewardAmount);
//        newReward.setRewardLimit(rewardLimit);
//        dynamicCrowdRewardService.save(newReward);
//
//        // then
//        List<DynamicCrowdRewardDTO.FindAllById> crowdRewardList = dynamicCrowdRewardService.findAll(crowdId);
//        System.out.println(crowdRewardList);
//        assertThat(crowdRewardList.get(0).getCrowdId()).isEqualTo(crowdId);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("crowdId 123번의 reward 전체 조회시 3개일 것이다.") // 테스트 실패 원인 찾기
//    public void findAllRewardTest() {
//        // given
//        int crowdId = 123;
//        int rewardId = 1;
//        // when
//        List<DynamicCrowdRewardDTO.FindAllById> findAllByIdList = dynamicCrowdRewardService.findAll(crowdId);
//        System.out.println(findAllByIdList);
//        // then
//        assertEquals(3, findAllByIdList.size());
//    }
//
//    @Test
//    @Transactional
//    public void findByRewardIdTest() {
//        // given
//        int rewardId = 1;
//        int crowdId = 123;
//        // when
//
//        DynamicCrowdRewardDTO.FindAllById crowdReward = dynamicCrowdRewardService.findByRewardId(crowdId, rewardId);
//        // then
//        assertEquals(1, crowdReward.getRewardId());
//        assertEquals(123, crowdReward.getCrowdId());
//    }
//}
