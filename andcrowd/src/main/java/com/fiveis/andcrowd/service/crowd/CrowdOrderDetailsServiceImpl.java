package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import com.fiveis.andcrowd.entity.user.DynamicUserOrder;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.crowd.CrowdOrderDetailsJPARepository;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdRewardRepository;
import com.fiveis.andcrowd.repository.user.DynamicUserOrderRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrowdOrderDetailsServiceImpl implements CrowdOrderDetailsService{

    private final CrowdOrderDetailsJPARepository crowdOrderDetailsJPARepository;
    private final DynamicUserOrderRepository dynamicUserOrderRepository;
    private final UserJPARepository userJPARepository;
    private final DynamicCrowdRewardRepository dynamicCrowdRewardRepository;

    @Override
    public List<CrowdOrderDetailsDTO.FindById> findAll() {//int crowdId) {
        List<CrowdOrderDetails> orderList = crowdOrderDetailsJPARepository.findAll();
        return orderList.stream()
                .map(this::convertToFindByIdDTO)
                .collect(Collectors.toList());
    } // 모든 결제내역을 조회하는 메서드

    @Override
    public Optional<CrowdOrderDetailsDTO.FindById> findById(int purchaseId) {
        Optional<CrowdOrderDetails> crowdOrderDetailsOptional = crowdOrderDetailsJPARepository.findById(purchaseId);
        return crowdOrderDetailsOptional.map(this::convertToFindByIdDTO);
    } // 특정 주문을 ID로 조회하는 메서드

    @Override
    public Optional<CrowdOrderDetailsDTO.FindById> findByMerchantUid(String merchantUid){
        Optional<CrowdOrderDetails> crowdOrderDetailsOptional = crowdOrderDetailsJPARepository.findByMerchantUid(merchantUid);
        return crowdOrderDetailsOptional.map(this::convertToFindByIdDTO);
    }

    // crowdId를 기준으로 조회하는 메서드
    @Override
    public List<CrowdOrderDetailsDTO.FindById> findAllByCrowdId(int crowdId){
        List<CrowdOrderDetails> orderListByCrowdId = crowdOrderDetailsJPARepository.findAllByCrowdId(crowdId);
        return orderListByCrowdId.stream()
                .map(this::convertToFindByIdDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CrowdOrderDetailsDTO.Manage> crowdIdManage(int crowdId){
        List<CrowdOrderDetails> orderListByCrowdId = crowdOrderDetailsJPARepository.findAllByCrowdId(crowdId);
        return orderListByCrowdId.stream()
                .map(this::convertToManageDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updatePurchaseStatus(int purchaseId, String purchaseStatus) {
        crowdOrderDetailsJPARepository.updatePurchaseStatus(purchaseId, purchaseStatus);
    }


    //
    @Override
    @Transactional
    public boolean save(CrowdOrderDetails crowdOrderDetails) {
        // 결제 정보 검증
        DynamicCrowdRewardDTO.FindAllById find = dynamicCrowdRewardRepository.findByRewardId(crowdOrderDetails.getCrowdId(),
                crowdOrderDetails.getRewardId());
        if(find.getRewardAmount() != crowdOrderDetails.getPurchaseAmount()){
            return false;
        }
        DynamicCrowdRewardDTO.FindAllById reward = dynamicCrowdRewardRepository
                .findByRewardId(crowdOrderDetails.getCrowdId(), crowdOrderDetails.getRewardId());
        // 한 번 더 재고 파악
        if(reward.getRewardLeft() < 1){
            return false;
        }
        // 재고 감소
        DynamicCrowdRewardDTO.UpdateRewardLeft update = DynamicCrowdRewardDTO.UpdateRewardLeft.builder()
                .rewardId(crowdOrderDetails.getRewardId())
                .crowdId(crowdOrderDetails.getCrowdId())
                .rewardLeft(reward.getRewardLeft()-1)
                .build();

        dynamicCrowdRewardRepository.updateRewardLeft(update);

        // 결제 정보 저장
        long milliseconds = Long.parseLong(crowdOrderDetails.getMerchantUid().split("_")[1]);
        LocalDateTime ldt = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
        crowdOrderDetails.setPurchaseDate(ldt);
        crowdOrderDetailsJPARepository.save(crowdOrderDetails);

        // 유저 테이블에도 저장
        List<CrowdOrderDetails> orderDetailsList = crowdOrderDetailsJPARepository.findAllByCrowdId(crowdOrderDetails.getCrowdId());
        for(CrowdOrderDetails orderDetails : orderDetailsList){
            if(orderDetails.getMerchantUid().equals(crowdOrderDetails.getMerchantUid())){
                String userEmail = User.toTableName(userJPARepository.findById(crowdOrderDetails.getUserId()).get().getUserEmail());
                DynamicUserOrder dynamicUserOrder = DynamicUserOrder.builder()
                        .orderId(orderDetails.getPurchaseId())
                        .build();
                dynamicUserOrderRepository.save(userEmail, dynamicUserOrder);
                break;
            }
        }

        // 현재시간 입력
//        crowdOrderDetails.setPurchaseDate(LocalDateTime.now());
//        CrowdOrderDetails insertOrder = crowdOrderDetailsJPARepository.save(crowdOrderDetails);
//        // 추가된 결제내역 유저별 주문 테이블에 적재
//        // 전체 주문내역을 가져온후
//        List<CrowdOrderDetails> orderList = crowdOrderDetailsJPARepository.findAll();
//        // 가장 첫번째 인덱스 번호 저장
//        CrowdOrderDetails newOrder = orderList.get(0);
//        // 주문내역에 저장된 userId를 가져옴
//        int orderUserId = newOrder.getUserId();
//        // 클라이언트에서 보낸 유저Id와 첫번째 인덱스에서 가져온 유저 아이디 비교후
//        // 해당 유저 주문내역 테이블에 데이터 적재
//        if(orderUserId != insertOrder.getUserId()) return false;
//
//        // 주문내역의 유저 Id로 유저 정보 불러옴
//        Optional<User> orderUser = userJPARepository.findByUserId(orderUserId);
//        // 유저 정보에서 email 추출후 테이블명으로 들어가도록 변환
//        String userEmail = User.toTableName(orderUser.get().getUserEmail());
//        // newUserOrder 객체에 주문Id 적재
//        DynamicUserOrder newUserOrder = DynamicUserOrder.builder()
//                .orderId(newOrder.getPurchaseId())
//                .build();
//        dynamicUserOrderRepository.save(userEmail, newUserOrder);
        return true;
    } // 주문내역 저장 메서드

    @Override
    public void update(CrowdOrderDetailsDTO.Update updateDTO) { //CrowdOrderDetails crowdOrderDetails) {
        Optional<CrowdOrderDetails> orderOptional = crowdOrderDetailsJPARepository.findById(updateDTO.getPurchaseId());
        orderOptional.ifPresent(updateOrder -> {
            // 필요한 필드 업데이트
            updateOrder.setPurchaseName(updateDTO.getPurchaseName());
            updateOrder.setPurchaseAddress(updateDTO.getPurchaseAddress());
            updateOrder.setPurchasePhone(updateDTO.getPurchasePhone());
            crowdOrderDetailsJPARepository.save(updateOrder);
        });
//        CrowdOrderDetails updateOrder = crowdOrderDetailsJPARepository.findById(crowdOrderDetails.getPurchaseId()).get();
//        crowdOrderDetailsJPARepository.save(crowdOrderDetails);
    } // 주문내역 수정 메서드

    @Override
    @Transactional
    public void deleteById(int purchaseId) {
        Optional<CrowdOrderDetails> orderOptional = crowdOrderDetailsJPARepository.findById(purchaseId);

        if(orderOptional.isPresent()) {
            CrowdOrderDetails crowdOrderDetails = orderOptional.get();
            crowdOrderDetails.setDeleted(true);
            crowdOrderDetailsJPARepository.save(crowdOrderDetails);
        }
    } // 주문내역 삭제 메서드

    @Override
    public CrowdOrderDetailsDTO.FindById convertToFindByIdDTO(CrowdOrderDetails crowdOrderDetails) {
        return CrowdOrderDetailsDTO.FindById.builder()
                .purchaseId(crowdOrderDetails.getPurchaseId())
                .userId(crowdOrderDetails.getUserId())
                .crowdId(crowdOrderDetails.getCrowdId())
                .rewardId(crowdOrderDetails.getRewardId())
                .merchantUid(crowdOrderDetails.getMerchantUid())
                .purchaseName(crowdOrderDetails.getPurchaseName())
                .purchasePhone(crowdOrderDetails.getPurchasePhone())
                .purchaseAddress(crowdOrderDetails.getPurchaseAddress())
                .purchaseDate(crowdOrderDetails.getPurchaseDate())
                .purchaseStatus(crowdOrderDetails.getPurchaseStatus())
                .isDeleted(crowdOrderDetails.isDeleted())
                .build();
    }

    public CrowdOrderDetailsDTO.Manage convertToManageDTO(CrowdOrderDetails crowdOrderDetails) {
        return CrowdOrderDetailsDTO.Manage.builder()
                .purchaseId(crowdOrderDetails.getPurchaseId())
                .userId(crowdOrderDetails.getUserId())
                .rewardName(crowdOrderDetails.getRewardName())
                .purchaseAmount(crowdOrderDetails.getPurchaseAmount())
                .purchaseStatus(crowdOrderDetails.getPurchaseStatus())
                .purchaseDate(crowdOrderDetails.getPurchaseDate())
                .purchaseName(crowdOrderDetails.getPurchaseName())
                .purchasePhone(crowdOrderDetails.getPurchasePhone())
                .purchaseAddress(crowdOrderDetails.getPurchaseAddress())
                .build();
    }



    @Override
    public List<CrowdOrderDetailsDTO.RewardSales> rewardSales(int crowdId) {
        List<CrowdOrderDetailsDTO.RewardCounts> rewardCountsList = crowdOrderDetailsJPARepository.countRewardsByCrowdId(crowdId);
        List<CrowdOrderDetailsDTO.RewardSales> rewardSalesList = new ArrayList<>();

        for (CrowdOrderDetailsDTO.RewardCounts counts : rewardCountsList) {
            int rewardId = counts.getRewardId();
            Long rewardCount = counts.getRewardCounts();

            // rewardId를 기반으로 rewardAmount 가져오기
            int rewardAmount = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId).getRewardAmount();

            // rewardCounts를 rewardSales로 변환하고 필드 추가
            CrowdOrderDetailsDTO.RewardSales sales = new CrowdOrderDetailsDTO.RewardSales(
                    counts.getRewardId(),
                    counts.getRewardName(),
                    counts.getRewardCounts(),
                    rewardAmount,
                    (int) (rewardCount * rewardAmount)
            );

            rewardSalesList.add(sales);
        }

        return rewardSalesList;
    }

}
