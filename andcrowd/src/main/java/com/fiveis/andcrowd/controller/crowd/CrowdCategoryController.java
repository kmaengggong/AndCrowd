package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdCategoryDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdCategory;
import com.fiveis.andcrowd.service.crowd.CrowdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crowdCategory")
public class CrowdCategoryController {

    private final CrowdCategoryService crowdCategoryService;

    @Autowired
    public CrowdCategoryController(CrowdCategoryService crowdCategoryService) {
        this.crowdCategoryService = crowdCategoryService;
    }

    @RequestMapping(value = "/{crowdCategoryId}", method = RequestMethod.GET)
    public CrowdCategoryDTO.Find getCrowdCategoryById(@PathVariable("crowdCategoryId") int crowdCategoryId) {
        return crowdCategoryService.findById(crowdCategoryId).orElse(null);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CrowdCategoryDTO.Find> getAllCrowdCategories() {
        return crowdCategoryService.findAllByIsDeletedFalse();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createCrowdCategory(@RequestBody CrowdCategory crowdCategory) {
        crowdCategoryService.save(crowdCategory);
    }

    @RequestMapping(value = "/{crowdCategoryId}/update", method = RequestMethod.PATCH)
    public void updateCrowdCategory(@RequestBody CrowdCategory crowdCategory) {
        crowdCategoryService.update(crowdCategory);
    }

    @RequestMapping(value = "/{crowdCategoryId}/delete", method = RequestMethod.DELETE)
    public void deleteCrowdCategory(@PathVariable("crowdCategoryId") int crowdCategoryId) {
        crowdCategoryService.deleteById(crowdCategoryId);
    }
}
