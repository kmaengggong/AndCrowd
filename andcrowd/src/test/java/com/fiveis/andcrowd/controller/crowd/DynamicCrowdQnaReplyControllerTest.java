package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaReplyDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaReplyRepository;
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
public class DynamicCrowdQnaReplyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    // 원래는 service 레이어를 호출해야하나, 추후 service레이어 에서 기능추가 될 경우를 대비하여 Repository 호출
    @Autowired
    private DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


//    findAll 기능 제외로 인한 주석처리
//    @Test
//    @Transactional
//    @DisplayName("crowd 1번글의 Qna 1번글의 전체 reply 조회시 2번요소의 데이터와 given의 데이터가 일치할것이다.")
//    void findAll() throws Exception {
//        // given
//        int crowdId = 1;
//        int crowdQnaId = 1;
//        String content = "3번댓글";
//        String url = "/crowd/1/qna/1/qnareply/all";
//
//        // when
//        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
//
//        result
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[2].crowdQnaId").value(crowdQnaId))
//                .andExpect(jsonPath("$[2].qnaReplyContent").value(content));
//    }`

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 Qna 1번글의 전체 reply 조회시 2번요소의 데이터와 given의 데이터가 일치할것이다.")
    void findAllfindAllByIsDeletedFalseTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdQnaId = 1;
        String content = "3번댓글";
        String url = "/crowd/1/qna/1/qnareply/all";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].crowdQnaId").value(crowdQnaId))
                .andExpect(jsonPath("$[1].qnaReplyContent").value(content));
    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 qna_reply_id 1번글을 조회시 given의 데이터와 일치할 것이다.")
    void findByIdTest() throws Exception {
        // given
        int crowdId = 1;
        int qnaReplyId = 1;
        int crowdQnaId = 1;
        String content = "1번댓글";
        String url = "/crowd/1/qna/1/qnareply/1";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("crowdQnaId").value(crowdQnaId))
                .andExpect(jsonPath("qnaReplyContent").value(content));
    }

//    @Test
//    @Transactional
//    @DisplayName("crowdId 1번글의 1번 Qna에 새댓글 insert시 qna_reply_id 4번이며, 입력한 데이터와 일치할것이다.")
//    void insertTest() throws Exception {
//        // given
//        int crowdId = 1;
//        int crowdQnaId = 1;
//        String content = "새로입력한댓글";
//        String url = "/crowd/1/qna/1/qnareply";
//        String url2 = "/crowd/1/qna/1/qnareply/all";
//
//        DynamicCrowdQnaReplyDTO.Save newReply = DynamicCrowdQnaReplyDTO.Save.builder()
//                .crowdId(crowdId)
//                .crowdQnaId(crowdQnaId)
//                .qnaReplyContent(content)
//                .build();
//
//        final String jsonQnaReply = objectMapper.writeValueAsString(newReply);
//
//        // when
//        mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonQnaReply));
//
//        final ResultActions result = mockMvc.perform(get(url2)
//                .accept(MediaType.APPLICATION_JSON));
//
//        result
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[3].crowdQnaId").value(crowdQnaId))
//                .andExpect(jsonPath("$[3].qnaReplyContent").value(content));
//
//    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowdQnaId 1번글의 1번 댓글 업데이트시 입력한 데이터와 일치할것이다.")
    void updateTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdQnaId = 1;
        int qnaReplyId = 1;
        String content = "수정한댓글";
        String url = "/crowd/1/qna/1/qnareply/1";

        DynamicCrowdQnaReplyDTO.Update updateQnaReply = DynamicCrowdQnaReplyDTO.Update.builder()
                .crowdId(crowdId)
                .crowdQnaId(crowdQnaId)
                .qnaReplyId(qnaReplyId)
                .qnaReplyContent(content)
                .build();

        final String jsonQnaReply = objectMapper.writeValueAsString(updateQnaReply);

        // when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQnaReply));

        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("qnaReplyContent").value(content));

    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowdQnaId 1번글의 2번 댓글 삭제시 idDeleted가 true가 될것이다.")
    void deletedTest() throws Exception {
        // given
        int crowdId = 1;
        int qnaReplyId = 1;
        String url = "/crowd/1/qna/1/qnareply/1/delete";

        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));

        // then
        assertThat(dynamicCrowdQnaReplyRepository.findById(crowdId, qnaReplyId).isDeleted()).isTrue();
    }

}
