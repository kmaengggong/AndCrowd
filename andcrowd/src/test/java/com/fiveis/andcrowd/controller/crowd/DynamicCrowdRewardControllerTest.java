package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdRewardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DynamicCrowdRewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DynamicCrowdRewardRepository dynamicCrowdRewardRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    void findAllTest() throws Exception {
        // given
        int crowdId = 123;
        String url = "/crowd/123/reward/list";
        // when
        final ResultActions resultList = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultList.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rewardTitle").value("슈퍼얼리버드1"))
                .andExpect(jsonPath("$[1].rewardTitle").value("슈퍼얼리버드2"))
                .andExpect(jsonPath("$[2].rewardTitle").value("슈퍼얼리버드3"));
    }

    @Test
    @Transactional
    void findByRewardIdTest() throws Exception {
        //given
        int crowdId = 123;
        int rewardId = 1;
        String rewardTitle = "슈퍼얼리버드1";
        String rewardContent = "기본후원1";
        String url = "/crowd/123/reward/1";
        // when
        final ResultActions resultRewardId = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultRewardId.andExpect(status().isOk())
                .andExpect(jsonPath("rewardTitle").value(rewardTitle))
                .andExpect(jsonPath("rewardContent").value(rewardContent));
    }

    @Test
    @Transactional
    void insertCrowdRewardTest() throws Exception {
        // given
        int crowdId = 123;
        String rewardTitle = "슈퍼슈퍼얼리버드4";
        String rewardContent = "슈퍼후원4";
        int rewardAmount = 100000;
        int rewardLimit = 5;
        String url = "/crowd/123/reward";
        String url2 = "/crowd/123/reward/list";

        DynamicCrowdRewardDTO.Update newReward = DynamicCrowdRewardDTO.Update.builder()
                .crowdId(crowdId)
                .rewardTitle(rewardTitle)
                .rewardContent(rewardContent)
                .rewardAmount(rewardAmount)
                .rewardLimit(rewardLimit)
                .build();

        // when
        final String jsonReward = objectMapper.writeValueAsString(newReward);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReward));
        // then
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[3].rewardTitle").value(rewardTitle))
                .andExpect(jsonPath("$[3].rewardContent").value(rewardContent));
    }

    @Test
    @Transactional
    void updateDynamicCrowdReward() throws Exception {
        // given
        int crowdId = 123;
        int rewardId = 3;
        String rewardTitle = "수정된얼리버드3";
        String rewardContent = "수정된후원3";

        DynamicCrowdRewardDTO.Update updateReward = DynamicCrowdRewardDTO.Update.builder()
                .crowdId(crowdId)
                .rewardId(rewardId)
                .rewardTitle(rewardTitle)
                .rewardContent(rewardContent)
                .build();

        String url = "/crowd/123/reward/3/update";
        String url2 = "/crowd/123/reward/3";
        // when
        final String requestReward = objectMapper.writeValueAsString(updateReward);
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestReward));
        // then
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("rewardTitle").value(rewardTitle))
                .andExpect(jsonPath("rewardContent").value(rewardContent));
    }

    @Test
    @Transactional
    void deleteDynamicReward() throws Exception {
        // given
        int crowdId = 123;
        int rewardId = 1;
        String url = "/crowd/123/reward/1";
        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));
        // then
        DynamicCrowdRewardDTO.FindAllById deletedReward = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);
        assertTrue(deletedReward.isDeleted());
    }
}