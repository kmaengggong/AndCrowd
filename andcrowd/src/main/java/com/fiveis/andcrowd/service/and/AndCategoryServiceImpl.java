package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.AndCategoryDTO;
import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.and.AndCategory;
import com.fiveis.andcrowd.repository.and.AndCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AndCategoryServiceImpl implements AndCategoryService {

    private final AndCategoryRepository andCategoryRepository;

    @Autowired
    public AndCategoryServiceImpl(AndCategoryRepository andCategoryRepository) {
        this.andCategoryRepository = andCategoryRepository;
    }

    @Override
    public Optional<AndCategoryDTO.FindByCategoryId> findById(int andCategoryId) {
        Optional<AndCategory> andCategoryOptional = andCategoryRepository.findById(andCategoryId);
        return andCategoryOptional.map(this::convertToAndCategoryFindDTO);
    }

    @Override
    public List<AndCategoryDTO.FindByCategoryId> findAllByIsDeletedFalse() {
        List<AndCategory> andList = andCategoryRepository.findAllByIsDeletedFalse();
        List<AndCategoryDTO.FindByCategoryId> findAllNotDeletedList = new ArrayList<>();

        for (AndCategory andCategory : andList) {
            AndCategoryDTO.FindByCategoryId dto = AndCategoryDTO.convertToAndCategoryFindDTO(andCategory);
            findAllNotDeletedList.add(dto);
        }


        return findAllNotDeletedList;
    }

    @Override
    public void update(AndCategory andCategory) {
        andCategoryRepository.save(andCategory);
    }

    @Override
    public void save(AndCategory andCategory) {
        AndCategory savedAndCategory = andCategoryRepository.save(andCategory);
    }
    @Override
    public void deleteById(int andCategoryId) {
        andCategoryRepository.deleteById(andCategoryId);
    }

    @Override
    public AndCategoryDTO.FindByCategoryId convertToAndCategoryFindDTO(AndCategory andCategory) {
        return AndCategoryDTO.FindByCategoryId.builder()
                .andCategoryId(andCategory.getAndCategoryId())
                .andCategoryName(andCategory.getAndCategoryName())
                .isDeleted(andCategory.isDeleted())
                .build();
    }
}
