package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import com.fiveis.andcrowd.repository.crowd.CrowdOrderDetailsJPARepository;
import com.fiveis.andcrowd.service.crowd.CrowdOrderDetailsService;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdRewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/crowd_order")
@RequiredArgsConstructor
public class CrowdOrderDetailsController {

    private final CrowdOrderDetailsService crowdOrderDetailsService;
    private final DynamicCrowdRewardService dynamicCrowdRewardService;

    @RequestMapping(value = "/list", method = RequestMethod.GET) // 관리자 권한 전체 조회 // 일반 유저는 조회 불가
    public ResponseEntity<List<CrowdOrderDetailsDTO.FindById>> getlist(@PathVariable int crowdId) {
        List<CrowdOrderDetailsDTO.FindById> orderList = crowdOrderDetailsService.findAll();
        return ResponseEntity.ok().body(orderList);//crowdId);
    }

    @RequestMapping(value = "/successorder", method = RequestMethod.POST)
    public ResponseEntity<String> insertOrder(@RequestBody CrowdOrderDetails crowdOrderDetails) {
        try{
            DynamicCrowdRewardDTO.FindAllById reward = dynamicCrowdRewardService
                    .findByRewardId(crowdOrderDetails.getCrowdId(), crowdOrderDetails.getRewardId());
            if(reward.getRewardLeft() < 1){
                return ResponseEntity.badRequest().body("해당 리워드가 품절되었습니다.");
            }
            if(!crowdOrderDetailsService.save(crowdOrderDetails)){
                return ResponseEntity.badRequest().body("결제 정보에 오류가 있습니다.");
            }
            return ResponseEntity.ok("리워드 결제 정보 저장 성공.");
        } catch(Error e){
            return ResponseEntity.badRequest().body("결제 정보에 오류가 있습니다.");
        }
    }

    @RequestMapping(value = "/{purchaseId}", method = RequestMethod.GET)
    public ResponseEntity<CrowdOrderDetailsDTO.FindById> getOrder(@PathVariable("purchaseId") int purchaseId) {
        // 개인결제내역 조회
        Optional<CrowdOrderDetailsDTO.FindById> findById = crowdOrderDetailsService.findById(purchaseId);
        if(findById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//crowdOrderDetailsService.findById(purchaseId);
        }else {
            return ResponseEntity.ok(findById.get());
        }
    }

    @RequestMapping(value = "/{purchaseId}/update", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> updateOrder(@RequestBody CrowdOrderDetailsDTO.Update updateDTO) {
        // 수정 작업 수행
        crowdOrderDetailsService.update(updateDTO);
        return ResponseEntity.ok("주문 정보가 정상적으로 수정되었습니다.");
    }

    @RequestMapping(value = "/{purchaseId}")//, method = RequestMethod.DELETE)
    public String deleteOrder(@PathVariable("purchaseId") int purchaseId) {
        // 소프트 딜리트 구현
        crowdOrderDetailsService.deleteById(purchaseId);
        ResponseEntity.ok("결제내역이 삭제되었습니다.");
        return "redirect:/crowd_order/list";
    }

    @GetMapping(value = "/{crowdId}/reward")
    public ResponseEntity<List<CrowdOrderDetailsDTO.RewardSales>> getRewardSales(@PathVariable("crowdId") int crowdId){
        List<CrowdOrderDetailsDTO.RewardSales> rewardCountList = crowdOrderDetailsService.rewardSales(crowdId);
        return ResponseEntity.ok(rewardCountList);
    }

    @GetMapping(value = "/{crowdId}/total")
    public ResponseEntity<Integer> getTotalSales(@PathVariable("crowdId") int crowdId){
        List<CrowdOrderDetailsDTO.RewardSales> rewardCountList = crowdOrderDetailsService.rewardSales(crowdId);
        int totalSales = rewardCountList.stream()
                .mapToInt(CrowdOrderDetailsDTO.RewardSales::getRewardSale)
                .sum();

        return ResponseEntity.ok(totalSales);
    }

    @GetMapping(value = "/{crowdId}/list")
    public ResponseEntity<List<CrowdOrderDetailsDTO.Manage>> findAllByCrowdId(@PathVariable("crowdId") int crowdId){

        List<CrowdOrderDetailsDTO.Manage> purchaseList = crowdOrderDetailsService.crowdIdManage(crowdId);

        return ResponseEntity.ok(purchaseList);
    }

    @RequestMapping(value="/crowd/purchase/{purchaseId}/update/status" , method=RequestMethod.PATCH)
    public ResponseEntity<String> updatePurchaseStatus( @PathVariable("purchaseId") int purchaseId, @RequestBody Map<String, String> purchaseStatus) {
        crowdOrderDetailsService.updatePurchaseStatus(purchaseId, purchaseStatus.get("purchaseStatus"));
        return ResponseEntity.ok("주문 상태가 정상적으로 업데이트 되었습니다.");
    }

    @RequestMapping(value="/order/{merchantUid}", method=RequestMethod.GET)
    public ResponseEntity<?> findOrderByMerchantUid(@PathVariable String merchantUid){
        Optional<CrowdOrderDetailsDTO.FindById> findById = crowdOrderDetailsService.findByMerchantUid(merchantUid);
        if(findById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(findById);
        }
    }
}