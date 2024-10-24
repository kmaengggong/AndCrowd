package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.repository.and.AndQueryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class AndQueryServiceImpl implements AndQueryService{
    private final AndQueryRepository andQueryRepository;

    public AndQueryServiceImpl(@Qualifier("andQueryRepositoryImpl") AndQueryRepository andQueryRepository) {
        this.andQueryRepository = andQueryRepository;
    }

    @Override
    public Slice<AndDTO.Find> findAllByCategoryAndStatusAndSort(Integer categoryId, Integer andStatus, String sortField, String sortOrder, String searchKeyword, Pageable pageable) {
        return andQueryRepository.findAllByCategoryAndStatusAndSort(categoryId, andStatus, sortField, sortOrder, searchKeyword, pageable);
    }
}
