package com.fiveis.andcrowd.controller.etc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.etc.AdDTO;
import com.fiveis.andcrowd.entity.etc.Ad;
import com.fiveis.andcrowd.service.etc.AdService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdControllerTest {

    @Autowired
    private AdService adService;

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
    @DisplayName("광고의 전체 목록 조회시 0번째 인덱스의 광고이름은 광고1일것이고," +
            "2번째 인덱스의 광고이름은 광고3일 것이다.")
    void findAllTest() throws Exception {
        //given
        String index0Name = "광고1";
        String index2Name = "광고3";
        String url = "/ad/all";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adName").value(index0Name))
                .andExpect(jsonPath("$[2].adName").value(index2Name));
    }

    @Test
    @Transactional
    @DisplayName("광고번호 2번을 조회시 이름은 광고2 일것이고 가격은 200000일것이다.")
    void findByIdTest() throws Exception {
        //given
        String adName = "광고2";
        int adPrice = 200000;
        String url = "/ad/2";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("adName").value(adName))
                .andExpect(jsonPath("adPrice").value(adPrice));
    }

    @Test
    @Transactional
    @DisplayName("광고를 추가할경우 전체광고의 3번인덱스 번호일것이다.")
    void insertTest() throws Exception{
        //given
        String adName = "광고4";
        int adPrice = 400000;
        String url = "/ad";
        String url2 = "/ad/all";

        Ad ad = Ad.builder()
                .adName(adName)
                .adPrice(adPrice)
                .build();

        final String jsonAd = objectMapper.writeValueAsString(ad);

        //when
        mockMvc.perform(post(url)
               .contentType(MediaType.APPLICATION_JSON)
               .content(jsonAd));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[3].adName").value(adName))
                .andExpect(jsonPath("$[3].adPrice").value(adPrice));

    }

    @Test
    @Transactional
    @DisplayName("adId 2번의 광고를 수정할경우 전체 광고중 1번인댁스에 수정사항이 반영된다")
    void updateTest() throws Exception{
        //given
        int adId = 2;
        String updateAdName = "수정된광고이름";
        int updateAdPrice = 900000;
        String url = "/ad/2";
        String url2 = "/ad/all";

        AdDTO.Update updateAd = AdDTO.Update.builder()
                .adId(adId)
                .adName(updateAdName)
                .adPrice(updateAdPrice)
                .build();

        final String jsonUpdateAd = objectMapper.writeValueAsString(updateAd);

        //when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdateAd));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].adName").value(updateAdName))
                .andExpect(jsonPath("$[1].adPrice").value(updateAdPrice));
    }

    @Test
    @Transactional
    @DisplayName("2번 광고가 삭제될경우 전체글 조회시 1번인덱스의 광고이름은 광고3," +
            "광고가격은 300000일것이다.")
    void deleteTest() throws Exception {
        //given
        int adId = 2;
        String url = "/ad/2";
        String url2 = "/ad/all";

        //when
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].adName").value("광고3"))
                .andExpect(jsonPath("$[1].adPrice").value(300000));
    }
}
