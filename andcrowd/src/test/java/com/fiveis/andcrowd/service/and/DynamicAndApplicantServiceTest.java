package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndApplicantDTO;
import com.fiveis.andcrowd.service.and.DynamicAndApplicantService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DynamicAndApplicantServiceTest {

    @Autowired
    DynamicAndApplicantService dynamicAndApplicantService;

    @Test
    @Transactional
    public void findAllTest(){
        //given
        int andId = 321;

        // when
        List<DynamicAndApplicantDTO.FindById> findAllList= dynamicAndApplicantService.findAll(andId);

        // then
        assertEquals(4, findAllList.size());

    }

    @Test
    @Transactional
    public void findAllNotDeletedTest(){
        //given
        int andId = 321;
        int andApplyId = 1;

        // when
        dynamicAndApplicantService.deleteByAndApplicantId(andId, andApplyId);
        List<DynamicAndApplicantDTO.FindById> findAllNotDeletedList= dynamicAndApplicantService.findAllNotDeleted(andId);

        // then
        assertEquals(3, findAllNotDeletedList.size());

    }


    @Test
    @Transactional
    public void findByAndApplicantIdTest(){
        //given
        int andApplyId = 1;
        int andId = 321;

        // when
        DynamicAndApplicantDTO.FindById andApplicant = dynamicAndApplicantService.findByAndApplicantId(andId, andApplyId);

        // then
        assertEquals(1, andApplicant.getAndApplyId());
        assertEquals("신청서 내용 1", andApplicant.getAndApplyContent());
        assertEquals(3, andApplicant.getAndRoleId());
        assertEquals(11, andApplicant.getUserId());
    }

    @Test
    @Transactional
    public void findByUserIdTest(){
        //given
        int userId = 12;
        int andId = 321;

        // when
        List<DynamicAndApplicantDTO.FindById> userApplyList = dynamicAndApplicantService.findByUserId(andId, userId);

        // then
        assertEquals(1, userApplyList.size());

    }

    @Test
    @Transactional
    public void findByAndRoleIdTest(){
        //given
        int andRoleId = 1;
        int andId = 321;

        // when
        List<DynamicAndApplicantDTO.FindById> roleApplyList = dynamicAndApplicantService.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(2, roleApplyList.size());
    }


    @Test
    @Transactional
    public void saveTest(){
        // given
        int andApplyId = 4;
        int andId = 321;
        int userId = 1;
        String andApplyContent = "4번 신청서 내용";
        int andRoleId = 4;

        DynamicAndApplicantDTO.Update andApplicantSave = DynamicAndApplicantDTO.Update.builder()
                .andApplyId(andApplyId)
                .andId(andId)
                .userId(userId)
                .andApplyContent(andApplyContent)
                .andRoleId(andRoleId)
                .build();

        // when
        dynamicAndApplicantService.save(andApplicantSave);
        DynamicAndApplicantDTO.FindById savedApplicant = dynamicAndApplicantService.findByAndApplicantId(andId, andApplyId);

        // then
        assertEquals(andRoleId, savedApplicant.getAndRoleId());
        assertEquals(andApplyContent, savedApplicant.getAndApplyContent());
        assertEquals(userId, savedApplicant.getUserId());

    }

    @Test
    @Transactional
    public void updateTest(){
        // given
        int andApplyId = 2;
        int andId = 321;
        String andApplyContent = "수정된 2번 신청서 내용";
        int andRoleId =22;

        DynamicAndApplicantDTO.Update andApplicantUpdate = DynamicAndApplicantDTO.Update.builder()
                .andApplyId(andApplyId)
                .andId(andId)
                .andApplyContent(andApplyContent)
                .andRoleId(andRoleId)
                .build();

        // when
        dynamicAndApplicantService.update(andApplicantUpdate);
        DynamicAndApplicantDTO.FindById updatedAndApplicant = dynamicAndApplicantService.findByAndApplicantId(andId, andApplyId);

        // then
        assertEquals(andApplyContent, updatedAndApplicant.getAndApplyContent());
        assertEquals(andRoleId, updatedAndApplicant.getAndRoleId());

    }

    @Test
    @Transactional
    public void deleteTest(){
        // given
        int andApplyId = 3;
        int andId = 321;

        // when
        dynamicAndApplicantService.deleteByAndApplicantId(andId, andApplyId);

        // then
        assertTrue(dynamicAndApplicantService.findByAndApplicantId(andId, andApplyId).isDeleted());
    }

}
