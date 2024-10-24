
package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;
import com.fiveis.andcrowd.service.and.DynamicAndQnaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DynamicAndQnaServiceTest {

    @Autowired
    DynamicAndQnaService dynamicAndQnaService;

    @Test
    @Transactional
    public void findAllTest(){
        //given
        int andId = 321;

        // when
        List<DynamicAndQnaDTO.FindById> findAllList= dynamicAndQnaService.findAll(andId);

        // then
        assertEquals(3, findAllList.size());

    }


//    @Test
//    @Transactional
//    public void findAllNotDeletedTest(){
//        //given
//        int andId = 321;
//        int andQnaId = 1;
//
//        // when
//        dynamicAndQnaService.deleteByAndQnaId(andId, andQnaId);
//        List<DynamicAndQnaDTO.FindById> findAllList= dynamicAndQnaService.findAllNotDeleted(andId);
//
//        // then
//        assertEquals(2, findAllList.size());
//
//    }


    @Test
    @Transactional
    public void findByIdTest() {
        //given
        int andQnaId = 2;
        int andId = 321;

        // when
        DynamicAndQnaDTO.FindById andQna = dynamicAndQnaService.findByAndQnaId(andId, andQnaId);

        // then
        assertEquals(2, andQna.getAndQnaId());
        assertEquals("QnA Title 2", andQna.getAndQnaTitle());
        assertEquals("QnA Content 2", andQna.getAndQnaContent());
        assertEquals(321, andQna.getAndId());

    }

    @Test
    @Transactional
    public void saveTest() {
        // given
        int andQnaId = 4;
        int andId = 321;
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
        dynamicAndQnaService.save(andQnaSave);
        DynamicAndQnaDTO.FindById savedAndQna = dynamicAndQnaService.findByAndQnaId(andId, andQnaId);

        // then
        assertEquals(userId, savedAndQna.getUserId());
        assertEquals(andQnaTitle, savedAndQna.getAndQnaTitle());
        assertEquals(andQnaContent, savedAndQna.getAndQnaContent());

    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        int andQnaId = 1;
        int andId = 321;
        String andQnaTitle = "수정된 1번 질문 제목";
        String andQnaContent = "수정된 1번 질문 내용";

        DynamicAndQnaDTO.Update andQnaUpdate = DynamicAndQnaDTO.Update.builder()
                .andQnaId(andQnaId)
                .andId(andId)
                .andQnaTitle(andQnaTitle)
                .andQnaContent(andQnaContent)
                .build();

        // when
        dynamicAndQnaService.update(andQnaUpdate);
        DynamicAndQnaDTO.FindById updatedAndQna = dynamicAndQnaService.findByAndQnaId(andId, andQnaId);

        // then
        assertEquals(andQnaTitle, updatedAndQna.getAndQnaTitle());
        assertEquals(andQnaContent, updatedAndQna.getAndQnaContent());

    }

    @Test
    @Transactional
    public void deleteTest() {
        // given
        int andQnaId = 3;
        int andId = 321;

        // when
        dynamicAndQnaService.deleteByAndQnaId(andId, andQnaId);

        // then
        assertTrue(dynamicAndQnaService.findByAndQnaId(andId, andQnaId).isDeleted());


    }

}
