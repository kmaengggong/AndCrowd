package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndRoleDTO;
import com.fiveis.andcrowd.service.and.DynamicAndRoleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DynamicAndRoleServiceTest {
    @Autowired
    DynamicAndRoleService dynamicAndRoleService;

    @Test
    public void testGetRolesWithApplicantCounts() {
        int andId = 1;
        List<DynamicAndRoleDTO.AndRoleWithApplicantsDTO> rolesWithCounts = dynamicAndRoleService.getRolesWithApplicantCounts(andId);

        System.out.println(rolesWithCounts);
    }

    @Test
    @Transactional
    public void findAllTest(){
        //given
        int andId = 321;

        // when
        List<DynamicAndRoleDTO.FindById> findAllList= dynamicAndRoleService.findAll(andId);

        // then
        assertEquals(3, findAllList.size());

    }

    @Test
    @Transactional
    public void findAllNotDeletedTest(){
        //given
        int andId = 321;
        int andRoleId = 3;

        // when
        dynamicAndRoleService.deleteByAndRoleId(andId, andRoleId);
        List<DynamicAndRoleDTO.FindById> findAllList= dynamicAndRoleService.findAllNotDeleted(andId);

        // then
        assertEquals(2, findAllList.size());

    }


    @Test
    @Transactional
    public void findByIdTest() {
        //given
        int andRoleId = 2;
        int andId = 321;

        // when
        DynamicAndRoleDTO.FindById andRole = dynamicAndRoleService.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(2, andRole.getAndRoleId());
        assertEquals("역할 2", andRole.getAndRole());
        assertEquals(20, andRole.getAndRoleLimit());

    }

    @Test
    @Transactional
    public void saveTest() {
        // given
        int andRoleId = 4;
        int andId = 321;
        String andRole = "추가된 4번 역할";
        int andRoleLimit = 100;

        DynamicAndRoleDTO.Update andRoleSave = DynamicAndRoleDTO.Update.builder()
                .andRoleId(andRoleId)
                .andId(andId)
                .andRole(andRole)
                .andRoleLimit(andRoleLimit)
                .build();

        // when
        dynamicAndRoleService.save(andRoleSave);
        DynamicAndRoleDTO.FindById savedAndRole = dynamicAndRoleService.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(andRoleId, savedAndRole.getAndRoleId());
        assertEquals(andRole, savedAndRole.getAndRole());
        assertEquals(andRoleLimit, savedAndRole.getAndRoleLimit());

    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        int andRoleId = 1;
        int andId = 321;
        String andRole = "수정된 1번 역할";
        int andRoleLimit = 200;

        DynamicAndRoleDTO.Update andRoleUpdate = DynamicAndRoleDTO.Update.builder()
                .andRoleId(andRoleId)
                .andId(andId)
                .andRole(andRole)
                .andRoleLimit(andRoleLimit)
                .build();

        // when
        dynamicAndRoleService.update(andRoleUpdate);
        DynamicAndRoleDTO.FindById updatedAndRole = dynamicAndRoleService.findByAndRoleId(andId, andRoleId);

        // then
        assertEquals(andRole, updatedAndRole.getAndRole());
        assertEquals(andRoleLimit, updatedAndRole.getAndRoleLimit());

    }

    @Test
    @Transactional
    public void deleteTest() {
        // given
        int andRoleId = 3;
        int andId = 321;

        // when
        dynamicAndRoleService.deleteByAndRoleId(andId, andRoleId);

        // then
        assertTrue(dynamicAndRoleService.findByAndRoleId(andId, andRoleId).isDeleted());


    }

}
