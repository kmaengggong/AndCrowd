package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.InfoBoardDTO;
import com.fiveis.andcrowd.entity.etc.InfoBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InfoBoardServiceTest {

    @Autowired
    InfoBoardService infoBoardService;

    @Test
    @Transactional
    void findAll() {
        List<InfoBoardDTO.Find> infoBoardList = infoBoardService.findAllByIsDeletedFalseOrderByInfoIdDesc();
        assertThat(infoBoardList.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    void findById() {
        // given
        int infoId = 1;
        String infoTitle = "1번공지제목";
        String infoContent = "1번공지본문";
        // when
        Optional<InfoBoardDTO.Find> infoBoard = infoBoardService.findById(infoId);
        // then
        assertEquals(infoId, infoBoard.get().getInfoId());
        assertEquals(infoTitle, infoBoard.get().getInfoTitle());
        assertEquals(infoContent, infoBoard.get().getInfoContent());
    }

    @Test
    @Transactional
    void deleteById() {
        // given
        int infoId = 1;
        // when
        infoBoardService.deleteById(infoId);
        // then
        assertEquals(3, infoBoardService.findAllByIsDeletedFalseOrderByInfoIdDesc().size());
//        InfoBoard deletedInfoBoard = infoBoardService.findById(infoId);
//        assertNull(deletedInfoBoard);
    }

    @Test
    @Transactional
    void save() {
        // given
        int infoId = 5;
        String infoTitle = "5번공지제목";
        String infoContent = "5번공지본문";
        boolean infoType = true;
        int userId = 5;
        // when
        InfoBoard newBoard = InfoBoard.builder()
                .infoTitle(infoTitle)
                .infoContent(infoContent)
                .infoType(infoType)
                .userId(userId)
                .build();
        newBoard.setPublishedAt(LocalDateTime.now());
        newBoard.setUpdatedAt(LocalDateTime.now());

        infoBoardService.save(newBoard);
        // then
        assertEquals(5, infoBoardService.findAllByIsDeletedFalseOrderByInfoIdDesc().size());
        assertEquals(infoTitle, newBoard.getInfoTitle());
    }

    @Test
    @Transactional
    void update() {
        // given
        int infoId = 4;
        String infoTitle = "4번새소식제목수정";
        String infoContent = "4번새소식본문수정";

        InfoBoard updateInfo = InfoBoard.builder()
                .infoId(infoId)
                .infoTitle(infoTitle)
                .infoContent(infoContent)
                .build();
        updateInfo.setUpdatedAt(LocalDateTime.now());

        // when
        infoBoardService.update(updateInfo);
        // then
        InfoBoardDTO.Find updatedInfo = infoBoardService.findById(infoId).get();
        assertEquals(infoId, updatedInfo.getInfoId());
        assertEquals(infoTitle, updatedInfo.getInfoTitle());
        assertEquals(infoContent, updatedInfo.getInfoContent());
    }
}