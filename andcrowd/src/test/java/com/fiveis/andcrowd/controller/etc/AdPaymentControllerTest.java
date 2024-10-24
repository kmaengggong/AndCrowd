package com.fiveis.andcrowd.controller.etc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.etc.AdPaymentDTO;
import com.fiveis.andcrowd.entity.etc.AdPayment;
import com.fiveis.andcrowd.service.etc.AdPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdPaymentControllerTest {
    @Autowired
    private AdPaymentService adPaymentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    @Transactional
    @DisplayName("전체 광고 결제내역 조회시 인덱스 0번의 userId는 1일것이며, projectId는 1일것이다." +
            "또한, 인덱스 2번의 userId는 3번, projectId는 3번일것이다.")
    void findAllTest() throws Exception{
        //given
        int index0UserId = 1;
        int index0ProjectId = 1;
        int index2UserId = 3;
        int index2ProjectId = 3;
        String url = "/ad/payment/all";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(index0UserId))
                .andExpect(jsonPath("$[0].projectId").value(index0ProjectId))
                .andExpect(jsonPath("$[2].userId").value(index2UserId))
                .andExpect(jsonPath("$[2].projectId").value(index2ProjectId));
    }

    @Test
    @Transactional
    @DisplayName("adPaymentId 1번 광고 결제내역을 조회할경우 userId 1, adId 1일것이다.")
    void findByIdTest() throws Exception{
        //given
        int adPaymentId = 1;
        int userId = 1;
        int adId = 1;
        String url = "/ad/payment/findById/1";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(userId))
                .andExpect(jsonPath("adId").value(adId));
    }

    @Test
    @Transactional
    @DisplayName("userId 1번의 결제내역을 불러오면 인덱스 0번의 projectId는 1, " +
            "1번의 projectId는 4일것이다.")
    void findAllByUserIdTest() throws Exception{
        //given
        int userId = 1;
        int index0ProjectId = 1;
        int index1ProjectId = 4;
        String url = "/ad/payment/findByUserId/1";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectId").value(index0ProjectId))
                .andExpect(jsonPath("$[1].projectId").value(index1ProjectId));
    }

    @Test
    @Transactional
    @DisplayName("광고결제내역을 추가할경우 전체결제내역의 4번 인덱스일것이다.")
    void insertTest() throws Exception{
        //given
        int adId = 1;
        int adPaymentStatus = 1;
        int projectId = 1;
        int projectType = 1;
        int userId = 1;
        String url = "/ad/payment/insert";
        String url2 = "/ad/payment/all";

        AdPayment adPayment = AdPayment.builder()
                .adId(adId)
                .adPaymentStatus(adPaymentStatus)
                .projectId(projectId)
                .projectType(projectType)
                .userId(userId)
                .build();

        final String jsonAdPayment = objectMapper.writeValueAsString(adPayment);

        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAdPayment));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[4].adId").value(adId))
                .andExpect(jsonPath("$[4].projectId").value(projectId))
                .andExpect(jsonPath("$[4].userId").value(userId));
    }

    @Test
    @Transactional
    @DisplayName("2번 결제내역이 삭제될경우 전체 결제내역의 1번 인덱스의 adPaymentId는 3일것이다.")
    void deleteById() throws Exception{
        //given
        int adPaymentId = 3;
        String url = "/ad/payment/delete/2";
        String url2 = "/ad/payment/all";

        //when
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].adPaymentId").value(adPaymentId));
    }

    @Test
    @Transactional
    @DisplayName("2번 결제내역의 결제 상태가 수정될경우 수정사항이 반영된다")
    void updateTest() throws Exception{
        //given
        int adPaymentId = 2;
        int adPaymentStatus = 2;
        String url = "/ad/payment/update/2";
        String url2 = "/ad/payment/all";

        AdPaymentDTO.Update updateAdPayment = AdPaymentDTO.Update.builder()
                .adPaymentId(adPaymentId)
                .adPaymentStatus(adPaymentStatus)
                .build();

        final String jsonUpdateAdPayment = objectMapper.writeValueAsString(updateAdPayment);

        //when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdateAdPayment));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].adPaymentStatus").value(adPaymentStatus));
    }
}
