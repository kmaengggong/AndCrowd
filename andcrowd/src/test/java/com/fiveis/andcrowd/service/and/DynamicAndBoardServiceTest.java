package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndBoardDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DynamicAndBoardServiceTest {

    @Autowired
    private DynamicAndBoardService dynamicAndBoardService;

    @Test
    @Transactional
    public void findAllTest() {
        // given
        int andId = 1111;

        // when
        List<DynamicAndBoardDTO.FindById> findAllList = dynamicAndBoardService.findAll(andId);

        // then
        assertEquals(3, findAllList.size());
    }

    @Test
    @Transactional
    public void findByAndBoardIdTest() {
        // given
        int andBoardId = 2;
        int andId = 1111;
        int userId = 2;

        // when
        DynamicAndBoardDTO.FindById andBoard = dynamicAndBoardService.findByAndBoardId(andId, andBoardId);
        System.out.println("test : "+andBoard);
        // then
        assertEquals(andBoardId, andBoard.getAndBoardId());
        assertEquals("Board Title 2", andBoard.getAndBoardTitle());
        assertEquals("Board Content 2", andBoard.getAndBoardContent());
        assertEquals(1111, andBoard.getAndId());
    }

    @Test
    @Transactional
    public void saveTest() {
        // given
        int andBoardId = 4;
        int andId = 1111;
        String andBoardTitle = "새로 추가된 글 제목";
        String andBoardContent = "새로 추가된 글 내용";

        DynamicAndBoardDTO.Update andBoardInsertDTO = DynamicAndBoardDTO.Update.builder()
                .andBoardId(andBoardId)
                .andId(andId)
                .andBoardTitle(andBoardTitle)
                .andBoardContent(andBoardContent)
                .build();

        // when
        dynamicAndBoardService.save(andBoardInsertDTO);
        DynamicAndBoardDTO.FindById savedAndBoard = dynamicAndBoardService.findByAndBoardId(andId, andBoardId);

        // then
        assertEquals(andBoardTitle, savedAndBoard.getAndBoardTitle());
        assertEquals(andBoardContent, savedAndBoard.getAndBoardContent());
    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        int andBoardId = 1;
        int andId = 1111;
        String andBoardTitle = "수정된 글 제목";
        String andBoardContent = "수정된 글 내용";

        DynamicAndBoardDTO.Update andBoardUpdateDTO = DynamicAndBoardDTO.Update.builder()
                .andBoardId(andBoardId)
                .andId(andId)
                .andBoardTitle(andBoardTitle)
                .andBoardContent(andBoardContent)
                .build();

        // when
        dynamicAndBoardService.update(andBoardUpdateDTO);
        DynamicAndBoardDTO.FindById updatedAndBoard = dynamicAndBoardService.findByAndBoardId(andId, andBoardId);

        // then
        assertEquals(andBoardTitle, updatedAndBoard.getAndBoardTitle());
        assertEquals(andBoardContent, updatedAndBoard.getAndBoardContent());
    }

    @Test
    @Transactional
    public void deleteByAndBoardIdTest() {
        // given
        int andBoardId = 3;
        int andId = 1111;

        // when
        dynamicAndBoardService.deleteByAndBoardId(andId, andBoardId);
        DynamicAndBoardDTO.FindById andBoard = dynamicAndBoardService.findByAndBoardId(andId, andBoardId);

        // then
        assertThat(andBoard.isDeleted()).isTrue();
    }
}