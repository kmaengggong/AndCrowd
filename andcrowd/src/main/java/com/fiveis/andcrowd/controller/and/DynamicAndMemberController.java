package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.service.and.DynamicAndMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/and/{andId}/member")
public class DynamicAndMemberController {

    private final DynamicAndMemberService dynamicAndMemberService;

    @Autowired
    public DynamicAndMemberController(DynamicAndMemberService dynamicAndMemberService) {
        this.dynamicAndMemberService = dynamicAndMemberService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void saveDynamicAndMember(@RequestBody DynamicAndMemberDTO.Update dynamicAndMemberDTOUpdate) {
        dynamicAndMemberService.save(dynamicAndMemberDTOUpdate);
    }

    @RequestMapping(value = "/{memberId}", method = RequestMethod.GET)
    public DynamicAndMemberDTO.FindByAndMemberId findByAndMemberId(
            @PathVariable("andId") int andId,
            @PathVariable("memberId") int memberId
    ) {
        return dynamicAndMemberService.findByAndMemberId(andId, memberId);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DynamicAndMemberDTO.FindByAndMemberId> findAll (@PathVariable("andId") int andId) {
        return dynamicAndMemberService.findAllNotDeleted(andId);
    }

    @RequestMapping(value = "/list/popup", method = RequestMethod.GET)
    public List<UserDTO.UserChatInfo> findAllNotdeletedWithProfile(@PathVariable("andId") int andId){
        return dynamicAndMemberService.findAllNotdeletedWithProfile(andId);
    }

    @RequestMapping(value = "/{memberId}/delete", method = RequestMethod.DELETE)
    public void deleteDynamicAndMember(
            @PathVariable("andId") int andId,
            @PathVariable("memberId") int memberId
    ) {
        dynamicAndMemberService.deleteById(andId, memberId);
    }

    @RequestMapping(value = "/{memberId}/update", method = RequestMethod.PATCH)
    public void updateDynamicAndMember(@RequestBody DynamicAndMemberDTO.Update dynamicAndMemberDTOUpdate) {
        dynamicAndMemberService.update(dynamicAndMemberDTOUpdate);
    }


}
