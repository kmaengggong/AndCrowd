package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DynamicAndMemberServiceTest {

    @Autowired
    private DynamicAndMemberService dynamicAndMemberService;

    @Test
    @Transactional
    public void findAllTest() {
        // given
        int andId = 1111;

        // when
        List<DynamicAndMemberDTO.FindByAndMemberId> findAllList = dynamicAndMemberService.findAll(andId);

        // then
        assertEquals(3, findAllList.size());
    }

    @Test
    @Transactional
    public void findByIdTest() {
        // given
        int andId = 1111;
        int memberId = 1;

        // when
        DynamicAndMemberDTO.FindByAndMemberId member = dynamicAndMemberService.findByAndMemberId(andId, memberId);

        // then
        assertEquals(memberId, member.getMemberId());
        assertEquals(1111, member.getAndId());
        assertEquals(1, member.getUserId());
    }

    @Test
    @Transactional
    public void saveTest() {
        // given
        int andId = 1111;
        int memberId = 4;
        int userId = 4;

        DynamicAndMemberDTO.Update memberInsertDTO = DynamicAndMemberDTO.Update.builder()
                .andId(andId)
                .userId(userId)
                .isDeleted(false)
                .build();

        // when
        dynamicAndMemberService.save(memberInsertDTO);
        DynamicAndMemberDTO.FindByAndMemberId savedMember = dynamicAndMemberService.findByAndMemberId(andId,memberId);

        // then
        assertEquals(andId, savedMember.getAndId());
        assertEquals(userId, savedMember.getUserId());
        assertEquals(false, savedMember.isDeleted());
    }

    @Test
    @Transactional
    public void deleteByIdTest() {
        // given
        int andId = 1111;
        int memberId = 3;

        // when
        dynamicAndMemberService.deleteById(andId, memberId);
        DynamicAndMemberDTO.FindByAndMemberId andMember = dynamicAndMemberService.findByAndMemberId(andId,memberId);
        // then
        assertThat(andMember.isDeleted()).isTrue();
    }
}