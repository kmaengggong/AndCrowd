package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicCrowdRewardRepositoryTest {

    @Autowired
    DynamicCrowdRewardRepository dynamicCrowdRewardRepository;

    @BeforeEach
    public void setCrowdRewardTable() {
        dynamicCrowdRewardRepository.testCreateDynamicCrowdRewardTable();
        dynamicCrowdRewardRepository.insertTestData();
    }

    @AfterEach
    public void dropCrowdRewardTable(){
        dynamicCrowdRewardRepository.dropCrowdRewardTable();
    }

    @Test
    @DisplayName("Crowd의 Reward 전체 데이터 조회")
    public void findAllTest(){
        // given
        int crowdId = 123;
        // when
        List<DynamicCrowdRewardDTO.FindAllById> findAllByIdList = dynamicCrowdRewardRepository.findAll(crowdId);
        // then
        assertEquals(3, findAllByIdList.size());
    }

    @Test
    @DisplayName("전체 Reward 중 1개의 Reward 조회")
    public void findByRewardIdTest() {
        // given
        int crowdId = 123;
        int rewardId = 1;
        String rewardTitle = "슈퍼얼리버드1";
        // when
        DynamicCrowdRewardDTO.FindAllById crowdReward = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);

        // then
        assertThat(crowdReward.getCrowdId()).isEqualTo(crowdId);
        assertThat(crowdReward.getRewardId()).isEqualTo(rewardId);
        assertThat(crowdReward.getRewardTitle()).isEqualTo(rewardTitle);
    }

    @Test
    @DisplayName("추가된 Reward의 crowdId는 123번이고, crowdId 123번의 Reward 데이터는 총 4개")
    @Transactional
    public void saveTest() {
        // given
        int rewardId = 4;
        int crowdId = 123;
        String rewardTitle = "추가 리워드제목";
        String rewardContent = "추가 리워드본문";
        int rewardAmount = 10000;
        int rewardLimit = 5;

        // when
        DynamicCrowdRewardDTO.Update newReward = DynamicCrowdRewardDTO.Update.builder()
                .rewardId(rewardId)
                .crowdId(crowdId)
                .rewardTitle(rewardTitle)
                .rewardContent(rewardContent)
                .rewardAmount(rewardAmount)
                .rewardLimit(rewardLimit)
                .build();

        dynamicCrowdRewardRepository.save(newReward);

        // then
        DynamicCrowdRewardDTO.FindAllById crowdReward = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);
        assertThat(crowdReward.getCrowdId()).isEqualTo(crowdId);
        assertThat(crowdReward.getRewardId()).isEqualTo(rewardId);
        assertThat(crowdReward.getRewardTitle()).isEqualTo(rewardTitle);
        List<DynamicCrowdRewardDTO.FindAllById> rewardList = dynamicCrowdRewardRepository.findAll(crowdId);
        assertEquals(4, rewardList.size());
    }

    @Test
    @DisplayName("crowdId 123번의 1번째 reward를 수정하면 입력된 내용으로 수정되는지 확인")
    public void updateTest() {
        // given
        int crowdId = 123;
        int rewardId = 1;
        String rewardTitle = "리워드제목수정";
        String rewardContent = "리워드본문수정";

        DynamicCrowdRewardDTO.Update crowdRewardUpdate = DynamicCrowdRewardDTO.Update.builder()
                .crowdId(crowdId)
                .rewardId(rewardId)
                .rewardTitle(rewardTitle)
                .rewardContent(rewardContent)
                .build();

        // when
        dynamicCrowdRewardRepository.update(crowdRewardUpdate);
        DynamicCrowdRewardDTO.FindAllById updatedCrowdReward = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);

        // then
        assertEquals(rewardTitle, updatedCrowdReward.getRewardTitle());
        assertEquals(rewardContent, updatedCrowdReward.getRewardContent());
    }

    @Test
    @Transactional
    @DisplayName("특정 RewardId를 소프트딜리트 후 조회되지 않는지 확인")
    public void deleteRewardTest() {
        // given
        int crowdId = 123;
        int rewardId = 1;

        // when
        dynamicCrowdRewardRepository.deleteByCrowdRewardId(crowdId, rewardId);

        // then
        DynamicCrowdRewardDTO.FindAllById deletedReward = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);
        System.out.println(deletedReward);
        assertTrue(deletedReward.isDeleted());
    }


}