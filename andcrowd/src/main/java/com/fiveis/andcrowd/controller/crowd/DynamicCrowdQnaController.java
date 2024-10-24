package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdQnaReplyService;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdQnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/crowd/{crowdId}/qna")
public class DynamicCrowdQnaController {

    private final DynamicCrowdQnaService dynamicCrowdQnaService;

    @Autowired
    public DynamicCrowdQnaController(DynamicCrowdQnaService dynamicCrowdQnaService){
        this.dynamicCrowdQnaService = dynamicCrowdQnaService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<DynamicCrowdQnaDTO.Find> findAllQnas(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size,
                                                     @PathVariable("crowdId") int crowdId){
        int offset = page * size;
        int limit = size;

        return dynamicCrowdQnaService.findAllByIsDeletedFalse(offset, limit, crowdId);
    }

    @GetMapping("/all/count")
    public int countAll(@PathVariable("crowdId") int crowdId) {
        return dynamicCrowdQnaService.countAll(crowdId);
    }

    @RequestMapping(value = "/{crowdQnaId}", method = RequestMethod.GET)
    public DynamicCrowdQnaDTO.Find findById(@PathVariable("crowdId") int crowdId, @PathVariable("crowdQnaId") int crowdQnaId){
        return dynamicCrowdQnaService.findById(crowdId, crowdQnaId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void insertCrowdQna(@RequestBody DynamicCrowdQnaDTO.Update dynamicCrowdQnaDTO){
        dynamicCrowdQnaService.save(dynamicCrowdQnaDTO);
    }

    @RequestMapping(value = "/{crowdQnaId}/update", method = RequestMethod.PATCH)
    public String updateCrowdQna(@PathVariable("crowdId") int crowdId,
                                @PathVariable("crowdQnaId")int crowdQnaId,
                                @RequestBody DynamicCrowdQnaDTO.Update dynamicCrowdQnaDTOUpdate){
        dynamicCrowdQnaService.update(dynamicCrowdQnaDTOUpdate);
        return "redirect:/crowd/" + crowdId + "/qna/" + crowdQnaId;
    }

    @RequestMapping(value = "/{crowdQnaId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCrowdQna(@PathVariable("crowdId") int crowdId, @PathVariable("crowdQnaId") int crowdQnaId){
        dynamicCrowdQnaService.deleteByCrowdQnaId(crowdId, crowdQnaId);
        return ResponseEntity.ok("문의글 삭제 완료");
    }
}
