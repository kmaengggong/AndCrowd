package com.fiveis.andcrowd.controller.etc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.dto.etc.InfoBoardDTO;
import com.fiveis.andcrowd.entity.etc.InfoBoard;
import com.fiveis.andcrowd.repository.etc.InfoBoardJPARepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InfoBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InfoBoardJPARepository infoBoardJPARepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    void getlist() throws Exception {
        // given
        String url = "/infoboard/list";
        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].infoTitle").value("4번새소식제목"))
                .andExpect(jsonPath("$[1].infoTitle").value("3번공지제목"))
                .andExpect(jsonPath("$[2].infoTitle").value("2번새소식제목"));
    }

    @Test
    @Transactional
    void detail() throws Exception {
        // given
        int infoId = 1;
        String url = "/infoboard/detail/1";
        // when
        final ResultActions resultInfo = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then
        resultInfo.andExpect(status().isOk())
                .andExpect(jsonPath("infoId").value(infoId));
    }

    @Test
    @Transactional
    void createInfoBoard() throws Exception {
        // given
        int infoId = 5;
        String infoContent = "5번추가공지";
        String infoTitle = "5번추가공지제목";
        boolean infoType = true;
        int userId = 1000;
        InfoBoard newInfo = InfoBoard.builder()
                .infoId(infoId)
                .infoContent(infoContent)
                .infoTitle(infoTitle)
                .infoType(infoType)
                .userId(userId)
                .build();
        newInfo.setPublishedAt(LocalDateTime.now());
        System.out.println(newInfo);

        String url = "/infoboard/create";
        String url2 = "/infoboard/list";
        // when
        final String jsonInfo = objectMapper.writeValueAsString(newInfo);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInfo));

        // then
        final ResultActions result = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));
        result
                .andExpect(jsonPath("$[0].infoContent").value(infoContent))
                .andExpect(jsonPath("$[0].infoTitle").value(infoTitle));
    }

    @Test
    @Transactional
    void updateInfoBoard() throws Exception {
        // given
        int infoId = 1;
        String infoTitle = "1번수정공지제목";
        String infoContent = "1번수정공지본문";

        InfoBoardDTO.Update updateInfo = InfoBoardDTO.Update.builder()
                .infoId(infoId)
                .infoTitle(infoTitle)
                .infoContent(infoContent)
                .build();
        System.out.println(updateInfo);
        String url = "/infoboard/1/update";
        String url2 = "/infoboard/detail/1";
        // when
        final String requestInfo = objectMapper.writeValueAsString(updateInfo);
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestInfo));
        // then
        final ResultActions resultActions = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("infoTitle").value(infoTitle))
                .andExpect(jsonPath("infoContent").value(infoContent));

    }

    @Test
    @Transactional
    void deleteInfoBoard() throws Exception {
        // given
        int infoId = 4;
        String url = "/infoboard/4";
        // when
        mockMvc.perform(patch(url).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
        // then
        Optional<InfoBoard> deletedInfoBoard = infoBoardJPARepository.findById(infoId);
        assertTrue(deletedInfoBoard.isPresent());
        assertTrue(deletedInfoBoard.get().isDeleted());
        System.out.println(deletedInfoBoard);
    }
}