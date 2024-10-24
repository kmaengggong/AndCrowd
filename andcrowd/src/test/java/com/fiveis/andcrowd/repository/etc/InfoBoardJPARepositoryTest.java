package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.entity.etc.InfoBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class InfoBoardJPARepositoryTest {

    @Autowired
    InfoBoardJPARepository infoBoardJPARepository;

    @Test
    @Transactional
    public void FindAllTest() {
        List<InfoBoard> infoBoardList = infoBoardJPARepository.findAllByIsDeletedFalseOrderByInfoIdDesc();
        assertEquals(4, infoBoardList.size());
    }

    @Test
    @Transactional
    public void insertTest() {
        //given
        int infoId = 5;
        int userId = 1000;
        String infoTitle = "5번새소식제목";
        String infoContent = "5번새소식본문";
        InfoBoard newInfo = InfoBoard.builder()
                .infoId(infoId)
                .userId(userId)
                .infoTitle(infoTitle)
                .infoContent(infoContent)
                .build();
        newInfo.setPublishedAt(LocalDateTime.now());
        newInfo.setUpdatedAt(LocalDateTime.now());
        // when
        infoBoardJPARepository.save(newInfo);
        System.out.println(newInfo);
        // then

    }

    @Test
    @Transactional
    public void FindByInfoIdTest() {
        //given
        int infoId = 3;
        // when
        InfoBoard infoBoard = infoBoardJPARepository.findById(infoId).orElse(null);
        // then
        assertNotNull(infoBoard);
        assertEquals(infoId, infoBoard.getInfoId());
    }

    @Test
    @Transactional
    public void updateInfoTest() {
        //given
        int infoId = 2;
        String infoTitle = "2번수정제목";
        // When
        InfoBoard infoBoard = infoBoardJPARepository.findById(infoId).orElse(null);
        assertNotNull(infoBoard);

        infoBoard.setInfoTitle(infoTitle);
        infoBoardJPARepository.save(infoBoard);

        // Then
        InfoBoard updatedInfoBoard = infoBoardJPARepository.findById(infoId).orElse(null);
        assertNotNull(updatedInfoBoard);
        assertEquals(infoTitle, updatedInfoBoard.getInfoTitle());
    }

    @Test
    @Transactional
    public void deleteInfoTest() {
        //given
        int infoId = 4;
        // when
        infoBoardJPARepository.deleteById(infoId);
        // then
        assertFalse(infoBoardJPARepository.existsById(infoId));
    }

}
