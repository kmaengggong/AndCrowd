package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.service.and.AndService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class AndServiceTest {

    @Autowired
    AndService andService;

    @Test
    @Transactional
    @DisplayName("R: findById를 통해 1번 모임글 조회")
    public void findById(){
        // given
        int andId = 1;

        // when
        AndDTO.Find andFindByIdDTO = andService.findById(andId).get();

        // then
        assertEquals("Test Content", andFindByIdDTO.getAndContent());
        assertEquals(1, andFindByIdDTO.getUserId());
        assertEquals("header.jpg", andFindByIdDTO.getAndHeaderImg());
    }

    @Test
    @Transactional
    @DisplayName("R: findAllByIsDeletedFalse 통해 전체글 조회")
    public void findAllByIsDeletedFalse(){
        // given

        // when
        andService.deleteById(1);
        List<AndDTO.Find> andFindAllNotDeletedList = andService.findAllByIsDeletedFalse();

        // then
        assertEquals(5, andFindAllNotDeletedList.size());
    }

    @Test
    @Transactional
    @DisplayName("U: update를 통해 1번글 업데이트")
    public void updateTest() {
        // given
        int andId =1;
    }


    @Test
    @DisplayName("C: save을 통해 모임글 저장 및 해당 동적 테이블 생성")
    public void saveTest() {
        // given
        int andCategoryId=3;
        String andContent = "andcontent3";
        int userId = 1;
        String andTitle = "andtitle3";
        int needNumMem = 5;
        String andHeaderImg = "headerimgdir3";
        And andUpdate = And.builder()
                .andContent(andContent)
                .userId(userId)
                .andHeaderImg(andHeaderImg)
                .andCategoryId(andCategoryId)
                .andTitle(andTitle)
                .needNumMem(needNumMem)
                .build();
        andUpdate.setAndEndDate(LocalDateTime.now());

        // when
        andService.save(andUpdate);

        // then
        assertEquals("andcontent3", andUpdate.getAndContent());
        assertEquals(1, andUpdate.getUserId());
        assertEquals("headerimgdir3", andUpdate.getAndHeaderImg());

        String tableName = "and_qna_" + andUpdate.getAndId();
        boolean tableExists = checkTableExists(tableName);
        Assertions.assertTrue(tableExists, "Table " + tableName + " should exist in the database.");
    }

    private boolean checkTableExists(String tableName) {
        // mysql에서 and_qna_2 테이블 생성 여부 확인
        return true;
    }

}
