package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdSponsorDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdSponsorRepository;
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
class DynamicCrowdSponsorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DynamicCrowdSponsorRepository dynamicCrowdSponsorRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    void findAllTest() throws Exception {
        // given
        int crowdId = 456;
        String url = "/crowd/456/sponsor/list";
        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].purchaseId").value(100))
                .andExpect(jsonPath("$[1].purchaseId").value(200))
                .andExpect(jsonPath("$[2].purchaseId").value(300));
    }

    @Test
    @Transactional
    void findBySponsorId() throws Exception {
        // given
        int crowdId = 456;
        int sponsorId = 1;
        int purchaseId = 100;
        String url = "/crowd/456/sponsor/1";
        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.sponsorId").value(sponsorId));
    }

    @Test
    @Transactional
    void insertCrowdSponsorTest() throws Exception {
        // given
        int crowdId = 456;
        int sponsorId = 4;
        int purchaseId = 500;
        String url = "/crowd/456/sponsor";
        String url2 = "/crowd/456/sponsor/list";

        DynamicCrowdSponsorDTO.Update newSponsor = DynamicCrowdSponsorDTO.Update.builder()
                .crowdId(crowdId)
                .sponsorId(sponsorId)
                .purchaseId(purchaseId)
                .build();
        // when
        final String jsonSponsor = objectMapper.writeValueAsString(newSponsor);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSponsor));
        // then
        final ResultActions resultActions = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[3].sponsorId").value(sponsorId))
                .andExpect(jsonPath("$[3].purchaseId").value(purchaseId));
    }

    @Test
    @Transactional
    void deleteCrowdSponsorTest() throws Exception {
        // given
        int crowdId = 456;
        int sponsorId = 3;
        String url = "/crowd/456/sponsor/3";
        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));
        // then
        DynamicCrowdSponsorDTO.FindById deletedSponsor = dynamicCrowdSponsorRepository.findBySponsorId(crowdId, sponsorId);
        assertTrue(deletedSponsor.isDeleted());
        System.out.println(deletedSponsor);
    }
}