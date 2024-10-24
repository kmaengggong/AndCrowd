package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import com.fiveis.andcrowd.service.and.DynamicAndQnaReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/and/{andId}/qna/reply")
public class DynamicAndQnaReplyController {

    DynamicAndQnaReplyService dynamicAndQnaReplyService;

    @Autowired
    public DynamicAndQnaReplyController(DynamicAndQnaReplyService dynamicAndQnaReplyService){
        this.dynamicAndQnaReplyService = dynamicAndQnaReplyService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DynamicAndQnaReplyDTO.FindById> getList(@PathVariable("andId") int andId){
        List<DynamicAndQnaReplyDTO.FindById> replyList = dynamicAndQnaReplyService.findAllNotDeleted(andId);
        return replyList;
    }

    @RequestMapping(value = "/{andQnaId}/all", method = RequestMethod.GET)
    public List<DynamicAndQnaReplyDTO.FindById> getReplies (@PathVariable("andId") int andId, @PathVariable("andQnaId") int andQnaId){
        List<DynamicAndQnaReplyDTO.FindById> replies = dynamicAndQnaReplyService.findAllByAndQnaId(andId, andQnaId);
        return replies;
    }

    @RequestMapping(value = "/{andQnaReplyId}", method = RequestMethod.GET)
    public DynamicAndQnaReplyDTO.FindById getReply (@PathVariable("andId") int andId, @PathVariable("andQnaReplyId") int andQnaReplyId){
        DynamicAndQnaReplyDTO.FindById reply = dynamicAndQnaReplyService.findByAndReplyId(andId, andQnaReplyId);
        return reply;
    }

    @RequestMapping(value = "/{andQnaId}/create", method = RequestMethod.POST)
    public void createReply(
            @PathVariable("andId") int andId,
            @PathVariable("andQnaId") int andQnaId,
            @RequestBody DynamicAndQnaReplyDTO.Update andReplySaveDTO){
        dynamicAndQnaReplyService.save(andReplySaveDTO);
    }

    @RequestMapping(value = "/{andQnaId}/{andQnaReplyId}/update", method = RequestMethod.PATCH)
    public String updateReply(
            @PathVariable("andId") int andId,
            @PathVariable("andQnaId") int andQnaId,
            @PathVariable("andQnaReplyId") int andQnaReplyId,
            @RequestBody DynamicAndQnaReplyDTO.Update andReplyUpdateDTO){
        dynamicAndQnaReplyService.update(andReplyUpdateDTO);
        return "redirect:/and/" + andId + "/qna/" + andQnaId;
    }

    @RequestMapping(value = "/{andQnaId}/{andQnaReplyId}/delete", method = RequestMethod.DELETE)
    public String deleteReply(@PathVariable("andId") int andId, @PathVariable("andQnaId") int andQnaId, @PathVariable("andQnaReplyId") int andQnaReplyId){
        dynamicAndQnaReplyService.deleteByAndReplyId(andId, andQnaReplyId);
        return "redirect:/and/" + andId + "/qna/" + andQnaId;
    }

    @RequestMapping(value = "/{andQnaId}/all/delete", method = RequestMethod.DELETE)
    public String deleteReplies(@PathVariable("andId") int andId, @PathVariable("andQnaId") int andQnaId){
        dynamicAndQnaReplyService.deleteByAndQnaId(andId, andQnaId);
        return "redirect:/and/" + andId + "/qna/reply/" + andQnaId + "/all";
    }


}
