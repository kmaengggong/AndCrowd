package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.AndCategoryDTO;
import com.fiveis.andcrowd.entity.and.AndCategory;
import com.fiveis.andcrowd.service.and.AndCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/andCategory") // 컨트롤러에 대한 기본 URL 경로 설정
public class AndCategoryController {

    private final AndCategoryService andCategoryService;

    @Autowired
    public AndCategoryController(AndCategoryService andCategoryService) {
        this.andCategoryService = andCategoryService;
    }

    @RequestMapping(value = "/{andCategoryId}", method = RequestMethod.GET)
    public AndCategoryDTO.FindByCategoryId getAndCategoryById(@PathVariable("andCategoryId") int andCategoryId) {
        return andCategoryService.findById(andCategoryId).orElse(null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AndCategoryDTO.FindByCategoryId> getAllAndCategories() {
        return andCategoryService.findAllByIsDeletedFalse();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createAndCategory(@RequestBody AndCategory andCategory) {
        andCategoryService.save(andCategory);
    }

    @RequestMapping(value = "/{andCategoryId}/update", method = RequestMethod.PATCH)
    public void updateAndCategory(@RequestBody AndCategory andCategory) {
        andCategoryService.update(andCategory);
    }

    @RequestMapping(value = "/{andCategoryId}/delete", method = RequestMethod.DELETE)
    public void deleteAndCategory(@PathVariable("andCategoryId") int andCategoryId) {
        andCategoryService.deleteById(andCategoryId);
    }
}
