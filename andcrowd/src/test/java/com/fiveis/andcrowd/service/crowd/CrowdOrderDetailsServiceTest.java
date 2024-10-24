package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.service.user.DynamicUserOrderService;
import com.fiveis.andcrowd.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CrowdOrderDetailsServiceTest {
    @Autowired
    CrowdOrderDetailsService crowdOrderDetailsService;
    @Autowired
    CrowdService crowdService;
    @Autowired
    DynamicUserOrderService dynamicUserOrderService;
    @Autowired
    UserService userService;

    @Test
    @Transactional
    @DisplayName("purchaseId 3번 조회")
    public void findByIdTest() {
        // given
        int purchaseId = 3;
        // when
        Optional<CrowdOrderDetailsDTO.FindById> result = crowdOrderDetailsService.findById(purchaseId);
        // then
        assertEquals(purchaseId, result.get().getPurchaseId());
    }

    @Test
    @Transactional
    @DisplayName("결제 내역 전체 조회")
    public void findAllTest() {
        // given
        // when
        List<CrowdOrderDetailsDTO.FindById> findAllList = crowdOrderDetailsService.findAll();
        // then
        assertEquals(3, findAllList.size());
    }

    @Test
    @Transactional
    @DisplayName("1번 유저가 crowd 3번글의 후원결제시 3번글의 결제내역은 총 2개" +
            "이며, 해당 유저의 결제내역에 결제한 내역이 저장된다.")
    public void saveTest() {
        // given
        int crowdId = 3;
        String purchaseAddress = "서울시";
        String purchaseName = "조승연";
        String purchasePhone = "010-0101-0101";
        String purchaseStatus = "결제완료";
        int rewardId = 1;
        int userId = 1;

        CrowdOrderDetails saveList = CrowdOrderDetails.builder()
                .crowdId(crowdId)
                .purchaseAddress(purchaseAddress)
                .purchaseName(purchaseName)
                .purchasePhone(purchasePhone)
                .purchaseStatus(purchaseStatus)
                .rewardId(rewardId)
                .userId(userId)
                .build();

        // when
        crowdOrderDetailsService.save(saveList);

        List<CrowdOrderDetailsDTO.FindById> crowdOrderList = crowdOrderDetailsService.findAllByCrowdId(crowdId);
        UserDTO.FindAsAdmin user = userService.findById(userId);
        String userEmail = user.getUserEmail().replace('@', '_').replace('.', '_');
        List<CrowdOrderDetailsDTO.FindById> userOrder = dynamicUserOrderService.findAll(userEmail);

        // then
        assertThat(crowdOrderList.size()).isEqualTo(2);
        assertThat(userOrder.get(0).getUserId()).isEqualTo(userId);
    }

//    private boolean checkTableExists(String tableName) {
//        // mysql에서 테이블 생성 여부 확인
//        return true;
//    }
}
