package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndQnaReplyRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicAndQnaReplyRepositoryTest {

    @Autowired
    DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository;

    @BeforeEach
    public void setAndQnaReplyTable(){
        dynamicAndQnaReplyRepository.createAndQnaReplyTable();
        dynamicAndQnaReplyRepository.insertTestData();
    }

    @AfterEach
    public void dropAndqnaReplyTable(){
        dynamicAndQnaReplyRepository.dropAndQnaReplyTable();
    }

    @Test
    @DisplayName("R: findAll를 통해 전체 답변 조회")
    public void findAllTest(){
        //given
        int andId = 123;

        // when
        List<DynamicAndQnaReplyDTO.FindById> findAllList= dynamicAndQnaReplyRepository.findAll(andId);

        // then
        assertEquals(4, findAllList.size());

    }

    @Test
    @DisplayName("R: findAllNotDeleted를 통해 삭제되지 않은 전체 답변 조회")
    public void findAllNotDeletedTest(){
        //given
        int andId = 123;
        int andReplyId = 1;

        // when
        dynamicAndQnaReplyRepository.deleteByAndReplyId(andId, andReplyId);
        List<DynamicAndQnaReplyDTO.FindById> findAllList= dynamicAndQnaReplyRepository.findAllNotDeleted(andId);

        // then
        assertEquals(3, findAllList.size());

    }

    @Test
    @DisplayName("R: findByAndQnaId를 통해 1번 질문글의 전체 답변 리스트 조회")
    public void findByAndQnaIdTest(){
        //given
        int andQnaId = 1;
        int andId = 123;

        // when
        List<DynamicAndQnaReplyDTO.FindById> andQnaReplyList = dynamicAndQnaReplyRepository.findAllByAndQnaId(andId, andQnaId);

        // then
        assertEquals(2, andQnaReplyList.size());
    }

    @Test
    @DisplayName("R: findAllByAndReplyId를 통해 2번 답변 조회")
    public void findAllByAndReplyIdTest(){
        //given
        int andQnaId = 2;
        int andId = 123;

        // when
        DynamicAndQnaReplyDTO.FindById andQnaReply = dynamicAndQnaReplyRepository.findByAndReplyId(andId, andQnaId);

        // then
        assertEquals(2, andQnaReply.getAndQnaId());
        assertEquals("QnA Reply Content 2", andQnaReply.getAndReplyContent());
        assertEquals(123, andQnaReply.getAndId());
        assertEquals(2, andQnaReply.getUserId());

    }

    @Test
    @DisplayName("C: save를 통해 3번 질문글에 새로운 5번 답변댓글 저장")
    public void saveTest(){
        // given
        int andReplyId = 5;
        int andQnaId = 3;
        int andId = 123;
        int userId = 1;
        String andReplyContent = "3번 질문글에 대한 새로운 5번 답변을 1번 유저가 작성";

        DynamicAndQnaReplyDTO.Update andReplySaveDTO = DynamicAndQnaReplyDTO.Update.builder()
                .andReplyId(andReplyId)
                .andQnaId(andQnaId)
                .andId(andId)
                .userId(userId)
                .andReplyContent(andReplyContent)
                .build();

        // when
        dynamicAndQnaReplyRepository.save(andReplySaveDTO);
        DynamicAndQnaReplyDTO.FindById savedAndReply = dynamicAndQnaReplyRepository.findByAndReplyId(andId, andReplyId);

        // then
        assertEquals(userId, savedAndReply.getUserId());
        assertEquals(andReplyContent, savedAndReply.getAndReplyContent());

    }

    @Test
    @DisplayName("U: update를 통해 4번 답변 내용 수정")
    public void updateTest(){
        // given
        int andId = 123;
        int andReplyId = 4;
        String andReplyContent = "수정된 4번 답변 내용";

        DynamicAndQnaReplyDTO.Update andReplyUpdateDTO = DynamicAndQnaReplyDTO.Update.builder()
                .andReplyId(andReplyId)
                .andId(andId)
                .andReplyContent(andReplyContent)
                .build();

        // when
        dynamicAndQnaReplyRepository.update(andReplyUpdateDTO);
        DynamicAndQnaReplyDTO.FindById updatedAndReply = dynamicAndQnaReplyRepository.findByAndReplyId(andId, andReplyId);

        // then
        assertEquals(andReplyContent, updatedAndReply.getAndReplyContent());

    }

    @Test
    @DisplayName("D: deleteByAndReplyId를 통해 1번 댓글 삭제")
    public void deleteByAndReplyIdTest(){
        // given
        int andId = 123;
        int andReplyId = 1;

        // when
        dynamicAndQnaReplyRepository.deleteByAndReplyId(andId, andReplyId);

        // then
        assertTrue(dynamicAndQnaReplyRepository.findByAndReplyId(andId, andReplyId).isDeleted());
    }

    @Test
    @DisplayName("D: deleteByAndQnaId를 통해 1번 질문글의 답변댓글 모두 삭제")
    public void deleteByAndQnaIdTest(){
        // given
        int andId = 123;
        int andAnaId = 1;

        // when
        dynamicAndQnaReplyRepository.deleteByAndQnaId(andId, andAnaId);

        // then : 1번 질문들에 달린 답변 댓글의 isDeleted 컬럼은 모두 true일 것이다 / 전체 답변 댓글 중 isDeleted 컬럼이 true인 개수는 2개일 것이다
        List<DynamicAndQnaReplyDTO.FindById> deletedReplyList = dynamicAndQnaReplyRepository.findAllByAndQnaId(andId, andAnaId);
        for (DynamicAndQnaReplyDTO.FindById deletedReply : deletedReplyList) {
            assertTrue(deletedReply.isDeleted());
        }

        List<DynamicAndQnaReplyDTO.FindById> replyList = dynamicAndQnaReplyRepository.findAllByAndQnaId(andId, andAnaId);
        long deletedCount = replyList.stream()
                .filter(DynamicAndQnaReplyDTO.FindById::isDeleted)
                .count();
        assertEquals(2, deletedCount);

    }

}
