package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.AdPaymentDTO;
import com.fiveis.andcrowd.entity.etc.AdPayment;
import com.fiveis.andcrowd.repository.etc.AdPaymentJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdPaymentServiceImpl implements AdPaymentService{
    private static AdPaymentJPARepository adPaymentJPARepository;

    @Autowired
    public AdPaymentServiceImpl(AdPaymentJPARepository adPaymentJPARepository){
        this.adPaymentJPARepository = adPaymentJPARepository;
    }

    @Override
    public List<AdPaymentDTO.Find> findAll() {
        List<AdPayment> adPaymentList = adPaymentJPARepository.findAll();
        List<AdPaymentDTO.Find> findList = new ArrayList<>();
        for(AdPayment adPayment : adPaymentList){
            findList.add(AdPaymentDTO.converToAdPaymentDTOFind(adPayment));
        }
        return findList;
    }

    @Override
    public AdPaymentDTO.Find findById(int adPaymentId) {
        if(adPaymentJPARepository.findById(adPaymentId).isEmpty()) return null;
        return AdPaymentDTO.converToAdPaymentDTOFind(adPaymentJPARepository.findById(adPaymentId).get());
    }

    @Override
    public List<AdPaymentDTO.Find> findAllByUserId(int userId){
        List<AdPayment> adPaymentListByUserId = adPaymentJPARepository.findAllByUserId(userId);
        List<AdPaymentDTO.Find> findListByUserId = new ArrayList<>();
        for(AdPayment adPayment : adPaymentListByUserId){
            findListByUserId.add(AdPaymentDTO.converToAdPaymentDTOFind(adPayment));
        }
        return findListByUserId;
    }

    @Override
    public void save(AdPayment adPayment) {
        adPayment.setPurchasedAt(LocalDateTime.now());
        // 광고 종료시간의 경우 now가 아니나 현재 광고 정책이 확정되지 않아 임시 작성
        adPayment.setExpiredAt(LocalDateTime.now());
        adPaymentJPARepository.save(adPayment);
    }

    @Override
    public void update(AdPaymentDTO.Update update) {
        AdPayment adPayment = adPaymentJPARepository.findById(update.getAdPaymentId()).get();
        if(update.getExpiredAt() != null){
            adPayment.setExpiredAt(update.getExpiredAt());
        }
        // 변경 사항이 없는 경우엔 원래 가지고 있던 값을 넣음
        adPayment.setAdPaymentStatus(update.getAdPaymentStatus());
        adPaymentJPARepository.save(adPayment);
    }

    @Override
    public void deleteById(int adPaymentId) {
        adPaymentJPARepository.deleteById(adPaymentId);
    }
}
