package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.entity.etc.AdPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPaymentJPARepository extends JpaRepository<AdPayment, Integer> {
    List<AdPayment> findAllByUserId(int userId);
}
