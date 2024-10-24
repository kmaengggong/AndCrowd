package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.repository.crowd.CrowdOrderDetailsJPARepository;
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
class CrowdOrderDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CrowdOrderDetailsJPARepository crowdOrderDetailsJPARepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    void findAllListTest() throws Exception {
        // given
        String url = "/crowd_order/list";
        String purchaseName = "김자바";
        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].purchaseName").value(purchaseName));

        System.out.println(resultActions);
    }

//    @Test
//    @Transactional
//    void insertOrderTest() throws Exception {
//        // given
//        int crowdId = 4;
//        int purchaseId = 4;
//        int userId = 4;
//        int rewardId = 4;
//        int sponsorId = 4;
//        String purchaseName = "임토비";
//        String purchasePhone = "01012345678";
//        String purchaseAddress = "광주 광산구";
//        String purchaseStatus = "계좌송금";
//
//        CrowdOrderDetails newOrder = CrowdOrderDetails.builder()
//                .crowdId(crowdId)
//                .purchaseId(purchaseId)
//                .userId(userId)
//                .rewardId(rewardId)
//                .sponsorId(sponsorId)
//                .purchaseName(purchaseName)
//                .purchasePhone(purchasePhone)
//                .purchaseAddress(purchaseAddress)
//                .purchaseStatus(purchaseStatus)
//                .build();
//        newOrder.setPurchaseDate(LocalDateTime.now());
//
//        System.out.println(newOrder);
//
//        String url = "/crowd_order/successorder";
//        String url2 = "/crowd_order/list";
//        // when
//        final String jsonOrder = objectMapper.writeValueAsString(newOrder);
//        mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonOrder));
//        // then
//        final ResultActions result = mockMvc.perform(get(url2)
//                .accept(MediaType.APPLICATION_JSON));
//        result.andExpect(jsonPath("$[3].crowdId").value(crowdId))
//                .andExpect(jsonPath("$[3].purchaseId").value(purchaseId));
//    } 테스트는 통과했으나 DB무한 증식으로 주석 처리

    @Test
    @Transactional
    void findByPurchaseIdTest() throws Exception {
        // given
        int purchaseId = 3;
        String purchaseName = "김코드";
        String url = "/crowd_order/3";
        // when
        final ResultActions getResult = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        getResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseName").value(purchaseName));
    }

    @Test
    @Transactional
    void updateOrder() throws Exception {
        // given
        int purchaseId = 2;
        int userId = 2;
        int crowdId = 2;
        int rewardId = 2;
        int sponsorId = 2;
        String purchaseName = "김동동";
        String purchaseAddress = "경기도 남양주시";
        String purchasePhone = "01078903456";
        CrowdOrderDetailsDTO.Update updateOrder = CrowdOrderDetailsDTO.Update.builder()
                .purchaseId(purchaseId)
                .userId(userId)
                .crowdId(crowdId)
                .rewardId(rewardId)
                .sponsorId(sponsorId)
                .purchaseName(purchaseName)
                .purchaseAddress(purchaseAddress)
                .purchasePhone(purchasePhone)
                .build();
        updateOrder.setPurchaseDate(LocalDateTime.now());

        System.out.println(updateOrder);

        String url = "/crowd_order/2/update";
        String url2 = "/crowd_order/2";
        final String requestBody = objectMapper.writeValueAsString(updateOrder);
        // when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        // then
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("purchaseName").value(purchaseName))
                .andExpect(jsonPath("purchaseAddress").value(purchaseAddress));
    }

    @Test
    @Transactional
    void deleteOrder() throws Exception {
        // given
        int purchaseId = 1;
        String url = "/crowd_order/" + purchaseId;
        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));
        // then
        assertTrue(crowdOrderDetailsJPARepository.findById(purchaseId).get().isDeleted());
    }
}