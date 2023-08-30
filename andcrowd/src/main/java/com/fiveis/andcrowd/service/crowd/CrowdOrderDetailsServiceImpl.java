package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import com.fiveis.andcrowd.entity.user.DynamicUserOrder;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.crowd.CrowdOrderDetailsJPARepository;
import com.fiveis.andcrowd.repository.user.DynamicUserOrderRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrowdOrderDetailsServiceImpl implements CrowdOrderDetailsService{

    CrowdOrderDetailsJPARepository crowdOrderDetailsJPARepository;
    DynamicUserOrderRepository dynamicUserOrderRepository;
    UserJPARepository userJPARepository;

    @Autowired
    public CrowdOrderDetailsServiceImpl(CrowdOrderDetailsJPARepository crowdOrderDetailsJPARepository,
                                        DynamicUserOrderRepository dynamicUserOrderRepository,
                                        UserJPARepository userJPARepository){
        this.crowdOrderDetailsJPARepository = crowdOrderDetailsJPARepository;
        this.dynamicUserOrderRepository = dynamicUserOrderRepository;
        this.userJPARepository = userJPARepository;
    }

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

    // crowdId를 기준으로 조회하는 메서드
    @Override
    public List<CrowdOrderDetailsDTO.FindById> findAllByCrowdId(int crowdId){
        List<CrowdOrderDetails> orderListByCrowdId = crowdOrderDetailsJPARepository.findAllByCrowdId(crowdId);
        return orderListByCrowdId.stream()
                .map(this::convertToFindByIdDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(CrowdOrderDetails crowdOrderDetails) {
        // 현재시간 입력
        crowdOrderDetails.setPurchaseDate(LocalDateTime.now());
        CrowdOrderDetails insertOrder = crowdOrderDetailsJPARepository.save(crowdOrderDetails);
        // 추가된 결제내역 유저별 주문 테이블에 적재
        // 전체 주문내역을 가져온후
        List<CrowdOrderDetails> orderList = crowdOrderDetailsJPARepository.findAll();
        // 가장 첫번째 인덱스 번호 저장
        CrowdOrderDetails newOrder = orderList.get(0);
        // 주문내역에 저장된 userId를 가져옴
        int orderUserId = newOrder.getUserId();
        // 클라이언트에서 보낸 유저Id와 첫번째 인덱스에서 가져온 유저 아이디 비교후
        // 해당 유저 주문내역 테이블에 데이터 적재
        if(orderUserId == insertOrder.getUserId()){
            // 주문내역의 유저 Id로 유저 정보 불러옴
            Optional<User> orderUser = userJPARepository.findByUserId(orderUserId);
            // 유저 정보에서 email 추출후 테이블명으로 들어가도록 변환
            String userEmail = User.toTableName(orderUser.get().getUserEmail());
            // newUserOrder 객체에 주문Id 적재
            DynamicUserOrder newUserOrder = DynamicUserOrder.builder()
                    .orderId(newOrder.getPurchaseId())
                    .build();
            dynamicUserOrderRepository.save(userEmail, newUserOrder);
        }
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
                .sponsorId(crowdOrderDetails.getSponsorId())
                .purchaseName(crowdOrderDetails.getPurchaseName())
                .purchasePhone(crowdOrderDetails.getPurchasePhone())
                .purchaseAddress(crowdOrderDetails.getPurchaseAddress())
                .purchaseDate(crowdOrderDetails.getPurchaseDate())
                .purchaseStatus(crowdOrderDetails.getPurchaseStatus())
                .isDeleted(crowdOrderDetails.isDeleted())
                .build();
    }
}
