package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdSponsorDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DynamicCrowdSponsorRepositoryTest {

    @Autowired
    DynamicCrowdSponsorRepository dynamicCrowdSponsorRepository;

    @BeforeEach
    public void setCrowdSponsorTable(){
        dynamicCrowdSponsorRepository.testCreateDynamicCrowdSponsorTable();
        dynamicCrowdSponsorRepository.insertTestData();
    }

    @AfterEach
    public void dropCrowdSponsorTable(){
        dynamicCrowdSponsorRepository.dropCrowdSponsorTable();
    }

    @Test
    @DisplayName("1번 Crowd글의 후원자 전체 조회")
    public void findAllTest(){
        // given
        int crowdId = 456;
        // when
        List<DynamicCrowdSponsorDTO.FindById> findAllList = dynamicCrowdSponsorRepository.findAll(crowdId);
        // then
        assertEquals(3, findAllList.size());
    }

    @Test
    @DisplayName("findById로 후원자 조회")
    public void findBySponsorId() {
        // given
        int crowdId = 456;
        int sponsorId = 1;
        // when
        DynamicCrowdSponsorDTO.FindById sponsor = dynamicCrowdSponsorRepository.findBySponsorId(crowdId, sponsorId);
        // then
        assertEquals(sponsorId, sponsor.getSponsorId());
        assertEquals(456, sponsor.getCrowdId());
        assertEquals(100, sponsor.getPurchaseId());
    }

    @Test
    @Transactional
    @DisplayName("새로운 후원자 생성")
    public void saveTest() {
        // given
        int crowdId = 456;
        int sponsorId = 4;
        int purchaseId = 4;

        DynamicCrowdSponsorDTO.Update sponsorInsert = DynamicCrowdSponsorDTO.Update.builder()
                .crowdId(crowdId)
                .sponsorId(sponsorId)
                .purchaseId(purchaseId)
                .isDeleted(false)
                .build();

        // when
        dynamicCrowdSponsorRepository.save(sponsorInsert);
        DynamicCrowdSponsorDTO.FindById saveSponsor = dynamicCrowdSponsorRepository.findBySponsorId(crowdId, sponsorId);

        // then
        assertEquals(crowdId, saveSponsor.getCrowdId());
        assertEquals(sponsorId, saveSponsor.getSponsorId());
        assertEquals(purchaseId, saveSponsor.getPurchaseId());
        assertEquals(false, saveSponsor.isDeleted());
    }

    @Test
    @Transactional
    @DisplayName("특정 SponsorId를 소프트딜리트 후 조회되지 않는지 확인")
    public void deleteSponsorTest() {
        // given
        int crowdId = 456;
        int sponsorId = 3;

        // when
        dynamicCrowdSponsorRepository.deleteByCrowdSponsorId(crowdId, sponsorId);

        // then
        DynamicCrowdSponsorDTO.FindById deletedSponsor = dynamicCrowdSponsorRepository.findBySponsorId(crowdId, sponsorId);
        System.out.println(deletedSponsor);
        assertTrue(deletedSponsor.isDeleted());
    }
}