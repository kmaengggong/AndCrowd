package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaRepository;
import jakarta.transaction.Transactional;
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
import org.springframework.web.context.WebApplicationContext;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DynamicCrowdQnaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    // 원래는 service 레이어를 호출해야하나, 추후 service레이어 에서 기능추가 될 경우를 대비하여 Repository 호출
    @Autowired
    private DynamicCrowdQnaRepository dynamicCrowdQnaRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

//    findAll 기능 제외로 인한 주석처리
//    @Test
//    @Transactional
//    @DisplayName("crowd 1번글의 Qna 전체글 조회시 2번째 요소의 title은 1번글제목, content는 1번글본문 이다.")
//    void findAll() throws Exception {
//        // given
//        int crowdId = 1;
//        String title = "1번글제목";
//        String content = "1번글본문";
//        String url = "/crowd/1/qna/all";
//
//        // when
//        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
//
//        result
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[2].qnaTitle").value(title))
//                .andExpect(jsonPath("$[2].qnaContent").value(content));
//    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 Qna 전체글 조회시 2번째 요소의 title은 1번글제목, content는 1번글본문 이다.")
    void findAllByIsDeletedFalseTest() throws Exception {
        // given
        int crowdId = 1;
        String title = "1번글제목";
        String content = "1번글본문";
        String url = "/crowd/1/qna/all";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].qnaTitle").value(title))
                .andExpect(jsonPath("$[1].qnaContent").value(content));
    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 crowd_qna_id가 2번인글 조회시 title은 2번글제목, content는 2번글본문 이다.")
    void findByIdTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdBoardId = 2;
        String title = "2번글제목";
        String content = "2번글본문";
        String url = "/crowd/1/qna/2";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("qnaTitle").value(title))
                .andExpect(jsonPath("qnaContent").value(content));
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글에 새글 insert시 crowdQnaId 4번이며, 입력한 데이터와 일치할것이다.")
    void insertTest() throws Exception {
        // given
        int crowdId = 1;
        String title = "새로입력한제목";
        String content = "새로입력한본문";
        String url = "/crowd/1/qna";
        String url2 = "/crowd/1/qna/all";

        DynamicCrowdQnaDTO.Save newQna = DynamicCrowdQnaDTO.Save.builder()
                .crowdId(crowdId)
                .qnaTitle(title)
                .qnaContent(content)
                .build();

        final String jsonQna = objectMapper.writeValueAsString(newQna);

        // when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQna));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].qnaTitle").value(title))
                .andExpect(jsonPath("$[0].qnaContent").value(content));

    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowdQnaId 2번글에 업데이트시 입력한 데이터와 일치할것이다.")
    void updateTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdQnaId = 2;
        String title = "수정한제목";
        String content = "수정한본문";
        String url = "/crowd/1/qna/2";

        DynamicCrowdQnaDTO.Update updateQna = DynamicCrowdQnaDTO.Update.builder()
                .crowdId(crowdId)
                .crowdQnaId(crowdQnaId)
                .qnaTitle(title)
                .qnaContent(content)
                .build();

        final String jsonQna = objectMapper.writeValueAsString(updateQna);

        // when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQna));

        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("qnaTitle").value(title))
                .andExpect(jsonPath("qnaContent").value(content));

    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowdQnaId 2번글 삭제시 idDeleted가 true가 될것이다.")
    void deletedTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdQnaId = 2;
        String url = "/crowd/1/qna/2/delete";

        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));

        // then
        assertThat(dynamicCrowdQnaRepository.findById(crowdId, crowdQnaId).isDeleted()).isTrue();
    }


}
