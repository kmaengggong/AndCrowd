package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndMemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicAndMemberRepositoryTest {

    @Autowired
    private DynamicAndMemberRepository dynamicAndMemberRepository;

    @BeforeEach
    public void setUpAndMemberTable() {
        dynamicAndMemberRepository.createAndMemberTable();
        dynamicAndMemberRepository.insertTestData();
    }

    @AfterEach
    public void dropAndMemberTable() {
        dynamicAndMemberRepository.dropAndMemberTable();
    }

    @Test
    @DisplayName("R: findAll를 통해 전체 멤버 조회")
    public void findAllTest() {
        // given
        int andId = 1111;

        // when
        List<DynamicAndMemberDTO.FindByAndMemberId> findAllList = dynamicAndMemberRepository.findAll(andId);

        // then
        assertEquals(3, findAllList.size());
    }

    @Test
    @DisplayName("R: findById를 통해 멤버 조회")
    public void findByIdTest() {
        // given
        int andId = 1111;
        int memberId = 1;

        // when
        DynamicAndMemberDTO.FindByAndMemberId member = dynamicAndMemberRepository.findByAndMemberId(andId, memberId);

        // then
        assertEquals(memberId, member.getMemberId());
        assertEquals(1111, member.getAndId());
        assertEquals(1, member.getUserId());
    }

    @Test
    @Transactional
    @DisplayName("C: save를 통해 새로운 멤버 저장")
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
        dynamicAndMemberRepository.save(memberInsertDTO);
        DynamicAndMemberDTO.FindByAndMemberId savedMember = dynamicAndMemberRepository.findByAndMemberId(andId,memberId);

        // then
        assertEquals(andId, savedMember.getAndId());
        assertEquals(userId, savedMember.getUserId());
        assertEquals(false, savedMember.isDeleted());
    }

    @Test
    @DisplayName("D: deleteById를 통해 멤버 삭제")
    public void deleteByIdTest() {
        // given
        int andId = 1111;
        int memberId = 3;

        // when
        dynamicAndMemberRepository.deleteById(andId, memberId);
        DynamicAndMemberDTO.FindByAndMemberId andMember = dynamicAndMemberRepository.findByAndMemberId(andId,memberId);
        // then
        assertThat(andMember.isDeleted()).isTrue();
    }
}
