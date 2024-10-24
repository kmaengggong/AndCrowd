package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CrowdTest {

    @Autowired
    CrowdJPARepository crowdJPARepository;




    @Test
    @Transactional
    public void findAllTest(){
        int userId = 1;

        List<CrowdDTO.FindAllByUserId> crowdList = crowdJPARepository.findAllByUserId(userId);

        assertThat(crowdList.size()).isEqualTo(1);
    }
}
