package com.fiveis.andcrowd.service;

import com.fiveis.andcrowd.dto.AndCategoryDTO;
import com.fiveis.andcrowd.entity.AndCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AndCategoryService {

    Optional<AndCategoryDTO.FindByCategoryId> findById(int andId);

    List<AndCategoryDTO.FindByCategoryId> findAll();

    void deleteById(int andCategoryId);

    void save(AndCategory andCategory);

    public AndCategoryDTO.FindByCategoryId convertToAndCategoryFindDTO(AndCategory andCategory);

    void update(AndCategory andCategory);
}
