package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.entity.crowd.CrowdCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrowdCategoryJPARepository extends JpaRepository<CrowdCategory, Integer> {

    List<CrowdCategory> findAllByIsDeletedFalse();
}
