package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.entity.etc.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdJPARepository extends JpaRepository<Ad, Integer> {
}
