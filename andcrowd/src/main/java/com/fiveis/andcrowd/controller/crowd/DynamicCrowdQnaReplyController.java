package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaReplyDTO;

import com.fiveis.andcrowd.service.crowd.CrowdService;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdQnaReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/crowd/{crowdId}/qna/reply")
public class DynamicCrowdQnaReplyController {

//    private final CrowdService crowdService;
    private final DynamicCrowdQnaReplyService dynamicCrowdQnaReplyService;

    @Autowired
    public DynamicCrowdQnaReplyController(//CrowdService crowdService,
                                          DynamicCrowdQnaReplyService dynamicCrowdQnaReplyService){
//        this.crowdService = crowdService;
        this.dynamicCrowdQnaReplyService = dynamicCrowdQnaReplyService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DynamicCrowdQnaReplyDTO.Find> getList(@PathVariable("crowdId") int crowdId){
        List<DynamicCrowdQnaReplyDTO.Find> replies = dynamicCrowdQnaReplyService.findAllNotDeleted(crowdId);
        return replies;
    }

    @RequestMapping(value = "/{crowdQnaId}/all", method = RequestMethod.GET)
    public List<DynamicCrowdQnaReplyDTO.Find> getReplies(@PathVariable("crowdId") int crowdId,
                                                                      @PathVariable("crowdQnaId") int crowdQnaId){
        List<DynamicCrowdQnaReplyDTO.Find> replies = dynamicCrowdQnaReplyService.findAllByCrowdQnaId(crowdId, crowdQnaId);
        return replies;
    }

    @RequestMapping(value = "/{qnaReplyId}", method = RequestMethod.GET)
    public DynamicCrowdQnaReplyDTO.Find getReply(@PathVariable("crowdId")int crowdId, @PathVariable("qnaReplyId")int qnaReplyId) {
        DynamicCrowdQnaReplyDTO.Find reply = dynamicCrowdQnaReplyService.findById(crowdId,qnaReplyId);
        return reply;
    }

    @RequestMapping(value = "/{crowdQnaId}/create", method = RequestMethod.POST)
    public void insertQnaReply(@PathVariable("crowdId")int crowdId,
                               @PathVariable("crowdQnaId")int crowdQnaId,@RequestBody DynamicCrowdQnaReplyDTO.Update saveReplyDTO){
        dynamicCrowdQnaReplyService.save(saveReplyDTO);
    }

    @RequestMapping(value = "/{crowdQnaId}/{qnaReplyId}/update", method = RequestMethod.PATCH)
    public String updateQnaReply(@PathVariable("crowdId")int crowdId,
                                 @PathVariable("crowdQnaId")int crowdQnaId,
                                 @PathVariable("qnaReplyId")int qnaReplyId,
                                 @RequestBody DynamicCrowdQnaReplyDTO.Update dynamicCrowdQnaReplyDTOUpdate){
        dynamicCrowdQnaReplyService.update(dynamicCrowdQnaReplyDTOUpdate);
        return "redirect:/crowd/" + crowdId + "/qna/" + crowdQnaId;
    }

    @RequestMapping(value = "/{crowdQnaId}/{qnaReplyId}/delete", method = RequestMethod.DELETE)
    public String deleteQnaReply(@PathVariable("crowdId") int crowdId,
                                 @PathVariable("crowdQnaId")int crowdQnaId,
                                 @PathVariable("qnaReplyId") int qnaReplyId){
        dynamicCrowdQnaReplyService.deleteByQnaReplyId(crowdId, qnaReplyId);
        return "redirect:/crowd/" + crowdId + "/qna/" + crowdQnaId + "/all";
    }

    @RequestMapping(value = "/{andQnaId}/all/delete", method = RequestMethod.DELETE)
    public String deleteReplies(@PathVariable("crowdId") int crowdId, @PathVariable("crowdQnaId") int crowdQnaId){
        dynamicCrowdQnaReplyService.deleteAllByQnaId(crowdId, crowdQnaId);
        return "redirect:/and/" + crowdId + "/qna/reply/" + crowdQnaId + "/all";
    }

//    @GetMapping(value = "/user-check/{userId}")
//    public boolean checkCrowdUser(@PathVariable("crowdId") int crowdId, @PathVariable("userId") int userId){
//        int crowdUserId = crowdService.findByCrowdId(crowdId).get().getUserId();
//        if(crowdUserId == userId){
//            return true;
//        }else {
//            return false;
//        }
//    }
}
