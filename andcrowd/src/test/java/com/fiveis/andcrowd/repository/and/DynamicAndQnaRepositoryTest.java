package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndQnaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicAndQnaRepositoryTest {

    @Autowired
    DynamicAndQnaRepository dynamicAndQnaRepository;

    @BeforeEach
    public void setAndQnaTable(){
        dynamicAndQnaRepository.createAndQnaTable();
        dynamicAndQnaRepository.insertTestData();
    }

    @AfterEach
    public void dropAndQnaTable() {
        dynamicAndQnaRepository.dropAndQnaTable();
    }
    @Test
    @DisplayName("R: findAll를 통해 전체 질문글 조회")
    public void findAllTest(){
        //given
        int andId = 123;

        // when
        List<DynamicAndQnaDTO.FindById> findAllList= dynamicAndQnaRepository.findAll(andId);

        // then
        assertEquals(3, findAllList.size());

    }


//    @Test
//    @DisplayName("R: findAllNotDeleted를 통해 전체 질문글 조회")
//    public void findAllNotDeletedTest(){
//        //given
//        int andId = 123;
//        int andQnaId = 3;
//
//        // when
//        dynamicAndQnaRepository.deleteByAndQnaId(andId, andQnaId);
//        List<DynamicAndQnaDTO.FindById> findAllList= dynamicAndQnaRepository.findAllNotDeleted(andId);
//
//        // then
//        assertEquals(2, findAllList.size());
//
//    }




    @Test
    @DisplayName("R: findByAndQnaId를 통해 2번 질문글 조회")
    public void findByAndQnaIdTest(){
        //given
        int andQnaId = 2;
        int andId = 123;

        // when
        DynamicAndQnaDTO.FindById andQna = dynamicAndQnaRepository.findByAndQnaId(andId, andQnaId);

        // then
        assertEquals(2, andQna.getAndQnaId());
        assertEquals("QnA Title 2", andQna.getAndQnaTitle());
        assertEquals("QnA Content 2", andQna.getAndQnaContent());
        assertEquals(123, andQna.getAndId());
    }

    @Test
    @DisplayName("C: save를 통해 4번째 행 데이터 저장")
    public void saveTest(){
        // given
        int andQnaId = 4;
        int andId = 123;
        int userId = 4;
        String andQnaTitle = "추가된 4번 질문 제목";
        String andQnaContent = "추가된 4번 질문 내용";

        DynamicAndQnaDTO.Update andQnaSave = DynamicAndQnaDTO.Update.builder()
                .andQnaId(andQnaId)
                .andId(andId)
                .userId(userId)
                .andQnaTitle(andQnaTitle)
                .andQnaContent(andQnaContent)
                .build();

        // when
        dynamicAndQnaRepository.save(andQnaSave);
        DynamicAndQnaDTO.FindById savedAndQna = dynamicAndQnaRepository.findByAndQnaId(andId, andQnaId);

        // then
        assertEquals(userId, savedAndQna.getUserId());
        assertEquals(andQnaTitle, savedAndQna.getAndQnaTitle());
        assertEquals(andQnaContent, savedAndQna.getAndQnaContent());

    }

    @Test
    @DisplayName("U: update를 통해 1번 질문글 수정")
    public void updateTest(){
        // given
        int andQnaId = 1;
        int andId = 123;
        String andQnaTitle = "수정된 1번 질문 제목";
        String andQnaContent = "수정된 1번 질문 내용";

        DynamicAndQnaDTO.Update andQnaUpdate = DynamicAndQnaDTO.Update.builder()
                .andQnaId(andQnaId)
                .andId(andId)
                .andQnaTitle(andQnaTitle)
                .andQnaContent(andQnaContent)
                .build();

        // when
        dynamicAndQnaRepository.update(andQnaUpdate);
        DynamicAndQnaDTO.FindById updatedAndQna = dynamicAndQnaRepository.findByAndQnaId(andId, andQnaId);

        // then
        assertEquals(andQnaTitle, updatedAndQna.getAndQnaTitle());
        assertEquals(andQnaContent, updatedAndQna.getAndQnaContent());

    }

    @Test
    @DisplayName("D: delete를 통해 3번 질문글 삭제")
    public void deleteTest(){
        // given
        int andQnaId = 3;
        int andId = 123;

        // when
        dynamicAndQnaRepository.deleteByAndQnaId(andId, andQnaId);

        // then
        assertTrue(dynamicAndQnaRepository.findByAndQnaId(andId, andQnaId).isDeleted());
    }
}
