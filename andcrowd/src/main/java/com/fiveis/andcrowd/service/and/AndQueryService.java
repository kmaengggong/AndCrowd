package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public interface AndQueryService {
    Slice<AndDTO.Find> findAllByCategoryAndStatusAndSort(
            Integer categoryId, Integer andStatus, String sortField, String sortOrder, String searchKeyword, Pageable pageable);

}
