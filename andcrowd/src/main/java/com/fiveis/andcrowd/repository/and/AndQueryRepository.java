package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.entity.and.And;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface AndQueryRepository {
    Slice<AndDTO.Find> findAllByCategoryAndStatusAndSort(
            Integer categoryId, Integer andStatus, String sortField, String sortOrder, String searchKeyword, Pageable pageable);
}
