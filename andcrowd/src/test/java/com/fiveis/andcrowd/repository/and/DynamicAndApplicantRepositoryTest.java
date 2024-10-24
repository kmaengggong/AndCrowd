package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndApplicantDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndApplicantRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicAndApplicantRepositoryTest {

    @Autowired
    DynamicAndApplicantRepository dynamicAndApplicantRepository;

    @BeforeEach
    public void setAndRoleTable(){
        dynamicAndApplicantRepository.createAndApplicantTable();
        dynamicAndApplicantRepository.insertTestData();
    }

    @AfterEach
    public void dropAndApplicantTable() {
        dynamicAndApplicantRepository.dropAndApplicantTable();
    }

    @Test
    @DisplayName("R: findAll를 통해 전체 신청자 조회")
    public void findAllTest(){
        //given
        int andId = 123;

        // when
        List<DynamicAndApplicantDTO.FindById> findAllList= dynamicAndApplicantRepository.findAll(andId);

        // then
        assertEquals(3, findAllList.size());

    }

    @Test
    @DisplayName("R: findAllNotDeleted를 통해 삭제되지 않은 전체 신청자 조회")
    public void findAllNotDeletedTest(){
        // given
        int andApplyId = 3;
        int andId = 123;

        // when
        dynamicAndApplicantRepository.deleteByAndApplicantId(andId, andApplyId);
        List<DynamicAndApplicantDTO.FindById> findAllNotDeletedList= dynamicAndApplicantRepository.findAllNotDeleted(andId);

        // then
        assertEquals(2, findAllNotDeletedList.size());

    }

    @Test
    @DisplayName("R: findByAndApplicantId를 통해 1번 신청자 조회")
    public void findByAndApplicantIdTest(){
        //given
        int andApplyId = 1;
        int andId = 123;

        // when
        DynamicAndApplicantDTO.FindById andApplicant = dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId);

        // then
        assertEquals(1, andApplicant.getAndApplyId());
        assertEquals("신청서 내용 1", andApplicant.getAndApplyContent());
        assertEquals(3, andApplicant.getAndRoleId());
        assertEquals(11, andApplicant.getUserId());
    }

    @Test
    @DisplayName("R: findByUserId를 통해 12번 유저의 신청내역 조회")
    public void findByUserIdTest(){
        //given
        int userId = 12;
        int andId = 123;

        // when
        List<DynamicAndApplicantDTO.FindById> userApplyList = dynamicAndApplicantRepository.findByUserId(andId, userId);

        // then
        assertEquals(2, userApplyList.size());

    }

    @Test
    @DisplayName("R: findByAndRoleId를 통해 2번 역할 신청 조회")
    public void findByAndRoleIdTest(){
        //given
        int andRoleId = 2;
        int andId = 123;

        // when
        List<DynamicAndApplicantDTO.FindById> roleApplyList = dynamicAndApplicantRepository.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(1, roleApplyList.size());
    }


    @Test
    @DisplayName("C: save를 통해 4번째 행 데이터 저장")
    public void saveTest(){
        // given
        int andApplyId = 4;
        int andId = 123;
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
        dynamicAndApplicantRepository.save(andApplicantSave);
        DynamicAndApplicantDTO.FindById savedApplicant = dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId);

        // then
        assertEquals(andRoleId, savedApplicant.getAndRoleId());
        assertEquals(andApplyContent, savedApplicant.getAndApplyContent());
        assertEquals(userId, savedApplicant.getUserId());

    }

    @Test
    @DisplayName("U: update를 통해 3번 역할 내용 수정")
    public void updateTest(){
        // given
        int andApplyId = 3;
        int andId = 123;
        String andApplyContent = "수정된 3번 신청서 내용";
        int andRoleId = 33;

        DynamicAndApplicantDTO.Update andApplicantUpdate = DynamicAndApplicantDTO.Update.builder()
                .andApplyId(andApplyId)
                .andId(andId)
                .andApplyContent(andApplyContent)
                .andRoleId(andRoleId)
                .build();

        // when
        dynamicAndApplicantRepository.update(andApplicantUpdate);
        DynamicAndApplicantDTO.FindById updatedAndApplicant = dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId);

        // then
        assertEquals(andApplyContent, updatedAndApplicant.getAndApplyContent());
        assertEquals(andRoleId, updatedAndApplicant.getAndRoleId());

    }

    @Test
    @DisplayName("D: delete를 통해 1번 신청서 삭제")
    public void deleteTest(){
        // given
        int andApplyId = 3;
        int andId = 123;

        // when
        dynamicAndApplicantRepository.deleteByAndApplicantId(andId, andApplyId);

        // then
        assertTrue(dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId).isDeleted());
    }


}
