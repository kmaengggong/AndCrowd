package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdBoardDTO;
import com.fiveis.andcrowd.service.crowd.CrowdService;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/crowd/{crowdId}/board")
public class DynamicCrowdBoardController {

    private final CrowdService crowdService;
    private final DynamicCrowdBoardService dynamicCrowdBoardService;

    @Autowired
    public DynamicCrowdBoardController(CrowdService crowdService,
                                       DynamicCrowdBoardService dynamicCrowdBoardService){
        this.crowdService = crowdService;
        this.dynamicCrowdBoardService = dynamicCrowdBoardService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<DynamicCrowdBoardDTO.Find> findAllBoards(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size,
                                                             @PathVariable("crowdId") int crowdId){
        int offset = page * size;
        int limit = size;

        return dynamicCrowdBoardService.findAllByIsDeletedFalse(offset, limit, crowdId);
    }

    @GetMapping("/all/count")
    public int countAll(@PathVariable("crowdId") int crowdId){
        return dynamicCrowdBoardService.countAll(crowdId);
    }

    @RequestMapping(value = "/{crowdBoardId}", method = RequestMethod.GET)
    public DynamicCrowdBoardDTO.Find findById(@PathVariable("crowdId") int crowdId,
                                                              @PathVariable("crowdBoardId") int crowdBoardId){
        return dynamicCrowdBoardService.findById(crowdId, crowdBoardId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void insertCrowdBoard(@RequestBody DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTO){
        dynamicCrowdBoardService.save(dynamicCrowdBoardDTO);
    }

    @RequestMapping(value = "/{crowdBoardId}/update", method = RequestMethod.PATCH)
    public void updateCrowdBoard(@PathVariable("crowdId")int crowdId,
                                   @PathVariable("crowdBoardId") int crowdBoardId,
                                   @RequestBody DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOupdate){
        dynamicCrowdBoardService.update(dynamicCrowdBoardDTOupdate);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteByCrowdId(@PathVariable("crowdId")int crowdId) {
        dynamicCrowdBoardService.deleteByCrowdId(crowdId);
    }

    @RequestMapping(value = "/{crowdBoardId}/delete", method = RequestMethod.DELETE)
    public void deleteCrowdBoard(@PathVariable("crowdId") int crowdId,
                                 @PathVariable("crowdBoardId") int crowdBoardId){
        dynamicCrowdBoardService.deleteByCrowdBoardId(crowdId, crowdBoardId);
    }

    @GetMapping(value = "/user-check/{userId}")
    public boolean checkCrowdUser(@PathVariable("crowdId") int crowdId, @PathVariable("userId") int userId){
        int crowdUserId = crowdService.findByCrowdId(crowdId).get().getUserId();
        if(crowdUserId == userId){
            return true;
        }else {
            return false;
        }
    }
}
