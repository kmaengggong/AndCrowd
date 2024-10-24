package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndBoardDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndBoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicAndBoardRepositoryTest {

    @Autowired
    private DynamicAndBoardRepository dynamicAndBoardRepository;

    @BeforeEach
    public void setUpAndBoardTable() {
        dynamicAndBoardRepository.createAndTestBoardTable();
        dynamicAndBoardRepository.insertTestData();
    }

    @AfterEach
    public void dropAndBoardTable() {
        dynamicAndBoardRepository.dropAndBoardTable();
    }

    @Test
    @DisplayName("R: findAll를 통해 전체 글 조회")
    public void findAllTest() {
        // given
        int andId = 1111;

        // when
        List<DynamicAndBoardDTO.FindById> findAllList = dynamicAndBoardRepository.findAll(andId);

        // then
        assertEquals(3, findAllList.size());
    }

    @Test
    @Transactional
    @DisplayName("R: findByAndBoardId를 통해 2번 글 조회")
    public void findByAndBoardIdTest() {
        // given
        int andBoardId = 2;
        int andId = 1111;
        int userId = 2;

        // when
        DynamicAndBoardDTO.FindById andBoard = dynamicAndBoardRepository.findByAndBoardId(andId, andBoardId);
        System.out.println("test : "+andBoard);
        // then
        assertEquals(andBoardId, andBoard.getAndBoardId());
        assertEquals("Board Title 2", andBoard.getAndBoardTitle());
        assertEquals("Board Content 2", andBoard.getAndBoardContent());
        assertEquals(1111, andBoard.getAndId());
    }

    @Test
    @DisplayName("C: save를 통해 새로운 글 저장")
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
        dynamicAndBoardRepository.save(andBoardInsertDTO);
        DynamicAndBoardDTO.FindById savedAndBoard = dynamicAndBoardRepository.findByAndBoardId(andId, andBoardId);

        // then
        assertEquals(andBoardTitle, savedAndBoard.getAndBoardTitle());
        assertEquals(andBoardContent, savedAndBoard.getAndBoardContent());
    }

    @Test
    @DisplayName("U: update를 통해 글 수정")
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
        dynamicAndBoardRepository.update(andBoardUpdateDTO);
        DynamicAndBoardDTO.FindById updatedAndBoard = dynamicAndBoardRepository.findByAndBoardId(andId, andBoardId);

        // then
        assertEquals(andBoardTitle, updatedAndBoard.getAndBoardTitle());
        assertEquals(andBoardContent, updatedAndBoard.getAndBoardContent());
    }

    @Test
    @DisplayName("D: deleteByAndBoardId를 통해 글 삭제")
    public void deleteByAndBoardIdTest() {
        // given
        int andBoardId = 3;
        int andId = 1111;

        // when
        dynamicAndBoardRepository.deleteByAndBoardId(andId, andBoardId);
        DynamicAndBoardDTO.FindById andBoard = dynamicAndBoardRepository.findByAndBoardId(andId, andBoardId);

        // then
        assertThat(andBoard.isDeleted()).isTrue();
    }
}
