package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserOrderDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import com.fiveis.andcrowd.entity.user.DynamicUserOrder;
import com.fiveis.andcrowd.repository.crowd.CrowdJPARepository;
import com.fiveis.andcrowd.repository.crowd.CrowdOrderDetailsJPARepository;
import com.fiveis.andcrowd.repository.user.DynamicUserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicUserOrderServiceImpl implements DynamicUserOrderService{
    private final DynamicUserOrderRepository dynamicUserOrderRepository;
    private final CrowdOrderDetailsJPARepository crowdOrderDetailsJPARepository;
    private final CrowdJPARepository crowdJPARepository;

    public List<CrowdOrderDetailsDTO.FindById> findAll(String userEmail){
        List<DynamicUserOrderDTO.Find> findList = dynamicUserOrderRepository.findAll(userEmail);
        List<CrowdOrderDetailsDTO.FindById> orderList = new ArrayList<>();
        for(DynamicUserOrderDTO.Find find : findList){
            if(crowdOrderDetailsJPARepository.findById(find.getOrderId()).isEmpty()) continue;
            CrowdOrderDetails crowdOrderDetails = crowdOrderDetailsJPARepository.findById(find.getOrderId()).get();
            orderList.add(CrowdOrderDetailsDTO.FindById.convertToFindByIdDTO(crowdOrderDetails));
        }
        return orderList;
    }

    public DynamicUserOrderDTO.Find findById(String userEmail, int uOrderId){
        return dynamicUserOrderRepository.findById(userEmail, uOrderId);
    }

    public boolean save(String userEmail, DynamicUserOrder dynamicUserOrder){
        // 존재하지 않는 projectId
        if(crowdOrderDetailsJPARepository.findById(dynamicUserOrder.getOrderId()).isEmpty()) return false;
        // user_like에 이미 존재
        if(dynamicUserOrderRepository.findByOrderId(userEmail, dynamicUserOrder.getOrderId()) != null) return false;
        // 그 외에는 저장 성공
        dynamicUserOrderRepository.save(userEmail, dynamicUserOrder);
        return true;
    }
    public void deleteById(String userEmail, int uOrderId){
        dynamicUserOrderRepository.deleteById(userEmail, uOrderId);
    }

    public void deleteByOrderId(String userEmail, int orderId){
        dynamicUserOrderRepository.deleteByOrderId(userEmail, orderId);
    }

    public void deleteTableByUserEmail(String userEmail){
        dynamicUserOrderRepository.deleteTableByUserEmail(userEmail);
    }

    public List<CrowdDTO.FindById> findAllCrowd(String userEmail){
        // 유저 테이블의 결제 정보를 가져옴(orderId)
        List<DynamicUserOrderDTO.Find> findList = dynamicUserOrderRepository.findAll(userEmail);

        // orderId를 기준으로 결제 상세 정보를 가져옴(crowdId)
        List<CrowdOrderDetailsDTO.FindById> orderList = findAll(userEmail);

        // crowdId 중복 제거
        List<Integer> crowdIdList = new ArrayList<>();
        for(CrowdOrderDetailsDTO.FindById order : orderList){
            if(!crowdIdList.contains(order.getCrowdId())){
                crowdIdList.add(order.getCrowdId());
            }
        }

        List<CrowdDTO.FindById> crowdList = new ArrayList<>();

        // crowdId를 기준으로 후원한 펀딩 내용을 가져옴
        for(Integer crowdId : crowdIdList){
            if(crowdJPARepository.findById(crowdId).isEmpty()) continue;
            CrowdDTO.FindById crowd = CrowdDTO.convertToCrowdFindDTO(crowdJPARepository.findById(crowdId).get());
            if(crowdList.contains(crowd)) continue;
            crowdList.add(crowd);
        }

        return crowdList;
    }
}
