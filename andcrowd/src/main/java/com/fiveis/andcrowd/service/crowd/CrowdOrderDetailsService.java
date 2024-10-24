package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CrowdOrderDetailsService {

   List<CrowdOrderDetailsDTO.FindById> findAll();//int crowdId);

   // 해당 로직은 단일 객체만 불러오는 기능으로 결제내역 단일건에 대한 조회로 PK를 파라미터로 사용
   Optional<CrowdOrderDetailsDTO.FindById> findById(int purchaseId);
   Optional<CrowdOrderDetailsDTO.FindById> findByMerchantUid(String merchantUid);

   // 특정 crowd글의 결재내역을 조회할수 있는 기능 추가
   List<CrowdOrderDetailsDTO.FindById> findAllByCrowdId(int crowdId);

   boolean save(CrowdOrderDetails crowdOrderDetails);
   void update(CrowdOrderDetailsDTO.Update updateDTO);
   void deleteById(int purchaseId);
   public CrowdOrderDetailsDTO.FindById convertToFindByIdDTO(CrowdOrderDetails crowdOrderDetails);
   List <CrowdOrderDetailsDTO.RewardSales> rewardSales(int crowdId);
   List<CrowdOrderDetailsDTO.Manage> crowdIdManage(int crowdId);
   void updatePurchaseStatus(int purchaseId, String purchaseStatus);
}
