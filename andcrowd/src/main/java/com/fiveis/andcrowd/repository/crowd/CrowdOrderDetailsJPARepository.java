package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrowdOrderDetailsJPARepository extends JpaRepository<CrowdOrderDetails, Integer> {
    List<CrowdOrderDetails> findAllByCrowdId(int crowdId);

    @Query("SELECT NEW com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO$RewardCounts(c.rewardId, c.rewardName, CAST(COUNT(c.rewardId) AS Long)) FROM CrowdOrderDetails c WHERE c.crowdId = :crowdId AND c.purchaseStatus NOT IN ('결제대기', '결제취소') GROUP BY c.rewardId")
    List<CrowdOrderDetailsDTO.RewardCounts> countRewardsByCrowdId(@Param("crowdId") int crowdId);

    @Modifying
    @Query("UPDATE CrowdOrderDetails c SET c.purchaseStatus = :purchaseStatus WHERE c.purchaseId = :purchaseId")
    void updatePurchaseStatus(@Param("purchaseId") int purchaseId, @Param("purchaseStatus") String purchaseStatus);

    Optional<CrowdOrderDetails> findByMerchantUid(String merchantUid);

//    CrowdOrderDetailsDT findByMerchantUid(String merchantUid);
}
