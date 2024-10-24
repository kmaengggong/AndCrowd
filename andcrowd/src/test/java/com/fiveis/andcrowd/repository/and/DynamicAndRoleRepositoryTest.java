package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndRoleDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndRoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicAndRoleRepositoryTest {

    @Autowired
    DynamicAndRoleRepository dynamicAndRoleRepository;

    @BeforeEach
    public void setAndRoleTable(){
        dynamicAndRoleRepository.createAndRoleTable();
        dynamicAndRoleRepository.insertTestData();
    }

    @AfterEach
    public void dropAndRoleTable() {
        dynamicAndRoleRepository.dropAndRoleTable();
    }

    @Test
    @DisplayName("R: findAll를 통해 전체 역할 리스트 조회")
    public void findAllTest(){
        //given
        int andId = 123;

        // when
        List<DynamicAndRoleDTO.FindById> findAllList= dynamicAndRoleRepository.findAll(andId);

        // then
        assertEquals(3, findAllList.size());

    }

    @Test
    @DisplayName("R: findAllNotDeleted를 통해 전체 역할 리스트 조회")
    public void findAllNotDeletedTest(){
        //given
        int andId = 123;
        int andRoleId = 3;

        // when
        dynamicAndRoleRepository.deleteByAndRoleId(andId, andRoleId);
        List<DynamicAndRoleDTO.FindById> findAllList= dynamicAndRoleRepository.findAllNotDeleted(andId);

        // then
        assertEquals(2, findAllList.size());

    }

    @Test
    @DisplayName("R: findByAndRoleId를 통해 1번 역할 조회")
    public void findByAndRoleIdTest(){
        //given
        int andRoleId = 1;
        int andId = 123;

        // when
        DynamicAndRoleDTO.FindById andRole = dynamicAndRoleRepository.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(1, andRole.getAndRoleId());
        assertEquals("역할 1", andRole.getAndRole());
        assertEquals(10, andRole.getAndRoleLimit());
    }

    @Test
    @DisplayName("C: save를 통해 4번째 행 데이터 저장")
    public void saveTest(){
        // given
        int andRoleId = 4;
        int andId = 123;
        String andRole = "추가된 4번 역할";
        int andRoleLimit = 40;

        DynamicAndRoleDTO.Update andRoleSave = DynamicAndRoleDTO.Update.builder()
                .andRoleId(andRoleId)
                .andId(andId)
                .andRole(andRole)
                .andRoleLimit(andRoleLimit)
                .build();

        // when
        dynamicAndRoleRepository.save(andRoleSave);
        DynamicAndRoleDTO.FindById savedRole = dynamicAndRoleRepository.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(andRoleId, savedRole.getAndRoleId());
        assertEquals(andRole, savedRole.getAndRole());
        assertEquals(andRoleLimit, savedRole.getAndRoleLimit());

    }

    @Test
    @DisplayName("U: update를 통해 2번 역할 내용 수정")
    public void updateTest(){
        // given
        int andRoleId = 2;
        int andId = 123;
        String andRole = "수정된 2번 역할";
        int andRoleLimit = 1;

        DynamicAndRoleDTO.Update andRoleUpdate = DynamicAndRoleDTO.Update.builder()
                .andRoleId(andRoleId)
                .andId(andId)
                .andRole(andRole)
                .andRoleLimit(andRoleLimit)
                .build();

        // when
        dynamicAndRoleRepository.update(andRoleUpdate);
        DynamicAndRoleDTO.FindById updatedAndRole = dynamicAndRoleRepository.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(andRole, updatedAndRole.getAndRole());
        assertEquals(andRoleLimit, updatedAndRole.getAndRoleLimit());

    }

    @Test
    @DisplayName("D: delete를 통해 3번 질문글 삭제")
    public void deleteTest(){
        // given
        int andRoleId = 3;
        int andId = 123;

        // when
        dynamicAndRoleRepository.deleteByAndRoleId(andId, andRoleId);

        // then
        assertTrue(dynamicAndRoleRepository.findByAndRoleId(andId, andRoleId).isDeleted());
    }


}
