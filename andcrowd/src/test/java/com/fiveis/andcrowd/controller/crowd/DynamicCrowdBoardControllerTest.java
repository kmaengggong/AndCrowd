package com.fiveis.andcrowd.controller.crowd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdBoardDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdBoardRepository;
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
public class DynamicCrowdBoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;
    // 원래는 service 레이어를 호출해야하나, 추후 service레이어 에서 기능추가 될 경우를 대비하여 Repository 호출
    @Autowired
    private DynamicCrowdBoardRepository dynamicCrowdBoardRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


//    findAll 기능 제외로 인한 주석처리
//    @Test
//    @Transactional
//    @DisplayName("crowd 1번글의 board 전체글 조회시 2번째 요소의 title은 1번글제목, content는 1번글본문 이다.")
//    void findAll() throws Exception {
//        // given
//        int crowdId = 1;
//        String title = "1번글제목";
//        String content = "1번글본문";
//        String url = "/crowd/1/board/all";
//
//        // when
//        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
//
//        result
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[2].crowdBoardTitle").value(title))
//                .andExpect(jsonPath("$[2].crowdBoardContent").value(content));
//    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 board 전체글 조회시 2번째 요소의 title은 1번글제목, content는 1번글본문 이다.")
    void findAllByIsDeletedFalseTest() throws Exception {
        // given
        int crowdId = 1;
        String title = "1번글제목";
        String content = "1번글본문";
        String url = "/crowd/1/board/all";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].crowdBoardTitle").value(title))
                .andExpect(jsonPath("$[1].crowdBoardContent").value(content));
    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 crowd_board_id가 2번인글 조회시 title은 2번글제목, content는 2번글본문 이다.")
    void findByIdTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdBoardId = 2;
        String title = "2번글제목";
        String content = "2번글본문";
        String url = "/crowd/1/board/2";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("crowdBoardTitle").value(title))
                .andExpect(jsonPath("crowdBoardContent").value(content));
    }

    @Test
    @Transactional
    @DisplayName("crwodId 1번글에 새글 insert시 crowdBoardId 4번이며, 입력한 데이터와 일치할것이다.")
    void insertTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdBoardTag = 1;
        String title = "새로입력한제목";
        String content = "새로입력한본문";
        String img = "이미지주소";
        String url = "/crowd/1/board";
        String url2 = "/crowd/1/board/all";

        DynamicCrowdBoardDTO.Save newBoard = DynamicCrowdBoardDTO.Save.builder()
                .crowdId(crowdId)
                .crowdBoardTag(crowdBoardTag)
                .crowdBoardTitle(title)
                .crowdBoardContent(content)
                .crowdImg(img)
                .build();

        final String jsonBoard = objectMapper.writeValueAsString(newBoard);

        // when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBoard));

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crowdBoardTitle").value(title))
                .andExpect(jsonPath("$[0].crowdBoardContent").value(content));

    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowdBoardId 2번글에 업데이트시 입력한 데이터와 일치할것이다.")
    void updateTest() throws Exception {
        // given
        int crowdId = 1;
        int tag = 1;
        int crowdBoardId = 2;
        String title = "수정한제목";
        String content = "수정한본문";
        String img = "수정한이미지주소";
        String url = "/crowd/1/board/2";

        DynamicCrowdBoardDTO.Update newBoard = DynamicCrowdBoardDTO.Update.builder()
                .crowdId(crowdId)
                .crowdBoardTag(tag)
                .crowdBoardId(crowdBoardId)
                .crowdBoardTitle(title)
                .crowdBoardContent(content)
                .crowdImg(img)
                .build();

        final String jsonBoard = objectMapper.writeValueAsString(newBoard);

        // when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBoard));

        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("crowdBoardTitle").value(title))
                .andExpect(jsonPath("crowdBoardContent").value(content))
                .andExpect(jsonPath("crowdImg").value(img));

    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowdBoardId 2번글 삭제시 idDeleted가 true가 될것이다.")
    void deletedTest() throws Exception {
        // given
        int crowdId = 1;
        int crowdBoardId = 2;
        String url = "/crowd/1/board/2/delete";
        String url2 = "/crowd/1/board/2";

        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN));

        // then
        assertThat(dynamicCrowdBoardRepository.findById(crowdId, crowdBoardId).isDeleted()).isTrue();
    }


}
