package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.service.and.AndQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/and")
public class AndScrollController {
    private final AndQueryService andQueryService;

    @Autowired
    public AndScrollController(AndQueryService andQueryService) {
        this.andQueryService = andQueryService;
    }

    @RequestMapping("/scroll")
    public Slice<AndDTO.Find> findAllByCategoryAndStatusAndSort(
            @RequestParam(value = "andCategoryId", required = false) Integer andCategoryId,
            @RequestParam(value = "andStatus", required = false) Integer andStatus,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortOrder", required = false) String sortOrder,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword, // 검색어 추가
            Pageable pageable) {
        return andQueryService.findAllByCategoryAndStatusAndSort(andCategoryId, andStatus, sortField, sortOrder, searchKeyword, pageable);
    }
}
