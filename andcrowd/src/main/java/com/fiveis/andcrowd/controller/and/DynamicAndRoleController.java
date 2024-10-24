package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.DynamicAndRoleDTO;
import com.fiveis.andcrowd.service.and.DynamicAndRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/and/{andId}/role")
public class DynamicAndRoleController {

    private final DynamicAndRoleService dynamicAndRoleService;

    @Autowired
    public DynamicAndRoleController(DynamicAndRoleService dynamicAndRoleService) {
        this.dynamicAndRoleService = dynamicAndRoleService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DynamicAndRoleDTO.FindById> findAll(@PathVariable("andId") int andId) {
        return dynamicAndRoleService.findAllNotDeleted(andId);
    }

    @RequestMapping(value = "/applicant/count", method = RequestMethod.GET)
    public List<DynamicAndRoleDTO.AndRoleWithApplicantsDTO> getRolesWithApplicantCounts (@PathVariable("andId") int andId){
        return dynamicAndRoleService.getRolesWithApplicantCounts(andId);
    }

    @RequestMapping(value = "/{andRoleId}", method = RequestMethod.GET)
    public DynamicAndRoleDTO.FindById findByAndRoleId(@PathVariable("andId") int andId,
                                                      @PathVariable ("andRoleId") int andRoleId) {
        return dynamicAndRoleService.findByAndRoleId(andId, andRoleId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void insertDynamicAndRole(@RequestBody DynamicAndRoleDTO.Update andRoleInsertDTO) {
        dynamicAndRoleService.save(andRoleInsertDTO);
    }

    @RequestMapping(value = "/{andRoleId}/update", method = RequestMethod.PATCH)
    public void updateDynamicAndRole(@RequestBody DynamicAndRoleDTO.Update andRoleUpdateDTO) {
        dynamicAndRoleService.update(andRoleUpdateDTO);
    }

    @RequestMapping(value = "/{andRoleId}/delete", method = RequestMethod.DELETE)
    public void deleteDynamicAndRole(@PathVariable("andId") int andId,
                                     @PathVariable ("andRoleId") int andRoleId) {
        dynamicAndRoleService.deleteByAndRoleId(andId, andRoleId);
    }

}
