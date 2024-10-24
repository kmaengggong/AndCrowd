package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdCategoryDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CrowdCategoryService {

    Optional<CrowdCategoryDTO.Find> findById(int crowdId);

    List<CrowdCategoryDTO.Find> findAllByIsDeletedFalse();

    void save(CrowdCategory crowdCategory);

    void update(CrowdCategory crowdCategory);

    public CrowdCategoryDTO.Find convertToCrowdCategoryFindDTO(CrowdCategory crowdCategory);

    void deleteById(int crowdCategoryId);
}
