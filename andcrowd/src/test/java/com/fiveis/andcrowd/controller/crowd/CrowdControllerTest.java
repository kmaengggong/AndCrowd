package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.repository.crowd.CrowdJPARepository;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CrowdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CrowdJPARepository crowdJPARepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    void getlistTest() throws Exception {
        // given
        String url = "/crowd/list";
        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crowdTitle").value("1번펀딩"))
                .andExpect(jsonPath("$[1].crowdTitle").value("2번펀딩"))
                .andExpect(jsonPath("$[2].crowdTitle").value("3번펀딩"));
    }

    @Test
    @Transactional
    void createCrowdTest() throws Exception {
        // given
        int crowdId = 123;
        int adId = 4;
        int andId = 4;
        String crowdContent = "4번 본문";
        int crowdGoal = 4000000;
        String crowdTitle = "4번펀딩";
        String headerImg = "4번헤더";
        String url = "/crowd/create";
        LocalDateTime publishedAt = LocalDateTime.now();

        Crowd newCrowd = Crowd.builder()
                .crowdId(crowdId)
                .adId(adId)
                .andId(andId)
                .crowdContent(crowdContent)
                .crowdGoal(crowdGoal)
                .crowdTitle(crowdTitle)
                .headerImg(headerImg)
                .publishedAt(publishedAt)
                .build();
        newCrowd.setCrowdEndDate(LocalDateTime.of(2023,12,31,0,0));

        String url2 = "/crowd/list";
        // when
        final String jsonCrowd = objectMapper.writeValueAsString(newCrowd);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCrowd));
        // then
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$[3].crowdTitle").value(crowdTitle))
                .andExpect(jsonPath("$[3].crowdContent").value(crowdContent));
    }

    @Test
    @Transactional
    void getCrowdTest() throws Exception {
        // given
        int crowdId = 3;
        String url = "/crowd/detail/3";
        // when
        final ResultActions resultCrowdId = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultCrowdId.andExpect(status().isOk())
                .andExpect(jsonPath("crowdId").value(crowdId));
    }

    @Test
    @Transactional
    void updateCrowdTest() throws Exception {
        // given
        int crowdId = 3;
        String crowdTitle = "수정된 3번펀딩";
        String crowdContent = "수정된 3번 본문";

        CrowdDTO.Update updateCrowd = CrowdDTO.Update.builder()
                .crowdId(crowdId)
                .crowdTitle(crowdTitle)
                .crowdContent(crowdContent)
                .build();

        String url = "/crowd/3/update";
        String url2 = "/crowd/detail/3";
        // when
        final String requestCrowd = objectMapper.writeValueAsString(updateCrowd);
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestCrowd));
        // then
        final ResultActions resultActions = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("crowdTitle").value(crowdTitle))
                .andExpect(jsonPath("crowdContent").value(crowdContent));
    }

    @Test
    @Transactional
    void deleteCrowdTest() throws Exception {
        // given
        int crowdId = 3;
        String url = "/crowd/3/delete";
        String url2 = "/crowd/detail/3";

        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));

        // then
        assertTrue(crowdJPARepository.findById(crowdId).get().isDeleted());
    }
}