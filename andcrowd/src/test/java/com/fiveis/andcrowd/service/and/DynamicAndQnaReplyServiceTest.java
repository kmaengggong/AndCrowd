package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import com.fiveis.andcrowd.service.and.DynamicAndQnaReplyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DynamicAndQnaReplyServiceTest {

    @Autowired
    DynamicAndQnaReplyService dynamicAndQnaReplyService;

    @Test
    @Transactional
    public void findAllTest(){
        //given
        int andId = 321;

        // when
        List<DynamicAndQnaReplyDTO.FindById> findAllList= dynamicAndQnaReplyService.findAll(andId);

        // then
        assertEquals(4, findAllList.size());

    }

    @Test
    @Transactional
    public void findAllNotDeletedTest(){
        //given
        int andId = 321;
        int andReplyId = 1;

        // when
        dynamicAndQnaReplyService.deleteByAndReplyId(andId, andReplyId);
        List<DynamicAndQnaReplyDTO.FindById> findAllList= dynamicAndQnaReplyService.findAllNotDeleted(andId);

        // then
        assertEquals(3, findAllList.size());

    }

    @Test
    @Transactional
    public void findAllByAndQnaIdTest(){
        // given
        int andId = 321;
        int andQnaId = 2;

        // when
        List<DynamicAndQnaReplyDTO.FindById> findAllByAndQnaIdList = dynamicAndQnaReplyService.findAllByAndQnaId(andId, andQnaId);

        // then
        assertEquals(1, findAllByAndQnaIdList.size());
    }

    @Test
    @Transactional
    public void findByAndReplyIdTest(){
        // given
        int andId = 321;
        int andReplyId = 3;

        // when
        DynamicAndQnaReplyDTO.FindById findByAndReplyId = dynamicAndQnaReplyService.findByAndReplyId(andId, andReplyId);

        // then
        assertEquals(3, findByAndReplyId.getAndReplyId());
        assertEquals("QnA Reply Content 3", findByAndReplyId.getAndReplyContent());
        assertEquals(3, findByAndReplyId.getUserId());

    }

    @Test
    @Transactional
    public void saveTest(){
        // given
        int andReplyId = 5;
        int andQnaId = 3;
        int andId = 321;
        int userId = 2;
        String andReplyContent = "새로 저장한 5번 댓글";

        DynamicAndQnaReplyDTO.Update andReplySaveDTO = DynamicAndQnaReplyDTO.Update.builder()
                .andReplyId(andReplyId)
                .andQnaId(andQnaId)
                .andId(andId)
                .userId(userId)
                .andReplyContent(andReplyContent)
                .build();

        // when
        dynamicAndQnaReplyService.save(andReplySaveDTO);
        DynamicAndQnaReplyDTO.FindById savedReply = dynamicAndQnaReplyService.findByAndReplyId(andId, andReplyId);

        // then
        assertEquals(5, dynamicAndQnaReplyService.findAll(andId).size());
        assertEquals(andReplyContent, savedReply.getAndReplyContent());

    }

    @Test
    @Transactional
    public void updateTest(){
        // given
        int andId = 321;
        int andReplyId = 4;
        String andReplyContent = "업데이트된 4번 답변 댓글";

        DynamicAndQnaReplyDTO.Update updateDTO = DynamicAndQnaReplyDTO.Update.builder()
                .andId(andId)
                .andReplyId(andReplyId)
                .andReplyContent(andReplyContent)
                .build();

        // when
        dynamicAndQnaReplyService.update(updateDTO);
        DynamicAndQnaReplyDTO.FindById updatedReply = dynamicAndQnaReplyService.findByAndReplyId(andId, andReplyId);

        // then
        assertEquals(andReplyContent, updatedReply.getAndReplyContent());
        assertEquals(andReplyId, updatedReply.getAndReplyId());
    }

    @Test
    @Transactional
    public void deleteByAndReplyIdTest(){
        // given
        int andId = 321;
        int andReplyId = 1;

        // when
        dynamicAndQnaReplyService.deleteByAndReplyId(andId, andReplyId);
        DynamicAndQnaReplyDTO.FindById deletedReply = dynamicAndQnaReplyService.findByAndReplyId(andId, andReplyId);

        // then
        assertEquals(true, deletedReply.isDeleted());
    }

    @Test
    @Transactional
    public void deleteByAndQnaIdTest() {
        // given
        int andId = 321;
        int andQnaId = 2;

        // when
        dynamicAndQnaReplyService.deleteByAndQnaId(andId, andQnaId);
        List<DynamicAndQnaReplyDTO.FindById> deletedReplyList = dynamicAndQnaReplyService.findAllByAndQnaId(andId, andQnaId);

        // then
        assertEquals(1, deletedReplyList.size());

        for (DynamicAndQnaReplyDTO.FindById deletedReply : deletedReplyList) {
            assertTrue(deletedReply.isDeleted());

        }
    }
}