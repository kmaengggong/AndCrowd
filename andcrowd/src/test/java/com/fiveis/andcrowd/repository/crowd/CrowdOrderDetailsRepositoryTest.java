package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CrowdOrderDetailsRepositoryTest {

    @Autowired
    private CrowdOrderDetailsJPARepository crowdOrderDetailsJPARepository;

    @Test
    @Transactional
    public void FindAllTest(){
        List<CrowdOrderDetails> crowdOrderDetailsList = crowdOrderDetailsJPARepository.findAll();

        assertEquals(3, crowdOrderDetailsList.size());
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 결제내역을 조회하면 2개의 결제내역이 조회될것이다.")
    public void findByCrowdIdTest(){
        // given
        int crowdId = 1;

        // when
        List<CrowdOrderDetails> orders = crowdOrderDetailsJPARepository.findAllByCrowdId(crowdId);

        //then
        assertThat(orders.size()).isEqualTo(2);
    }

}
