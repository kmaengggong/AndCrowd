package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.AdPaymentDTO;
import com.fiveis.andcrowd.entity.etc.AdPayment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdPaymentService {
    List<AdPaymentDTO.Find> findAll();
    AdPaymentDTO.Find findById(int adPaymentId);
    List<AdPaymentDTO.Find> findAllByUserId(int userId);
    void save(AdPayment adPayment);
    void update(AdPaymentDTO.Update update);
    void deleteById(int adPaymentId);
}
