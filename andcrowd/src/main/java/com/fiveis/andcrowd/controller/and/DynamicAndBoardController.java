package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.DynamicAndBoardDTO;
import com.fiveis.andcrowd.service.and.DynamicAndBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/and/{andId}/board")
public class DynamicAndBoardController {

    private final DynamicAndBoardService dynamicAndBoardService;

    @Autowired
    public DynamicAndBoardController(DynamicAndBoardService dynamicAndBoardService) {
        this.dynamicAndBoardService = dynamicAndBoardService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DynamicAndBoardDTO.FindById> getAllAndBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @PathVariable("andId") int andId) {

        int offset = page * size;
        int limit = size;

        return dynamicAndBoardService.findAllNotDeleted(offset, limit, andId);
    }

    @GetMapping("/list/count")
    public int countAll(@PathVariable("andId") int andId) {
        return dynamicAndBoardService.countAll(andId);
    }

    @RequestMapping(value = "{andBoardId}", method = RequestMethod.GET)
    public DynamicAndBoardDTO.FindById getAndBoardById(@PathVariable("andId") int andId,
                                                       @PathVariable("andBoardId") int andBoardId) {
        return dynamicAndBoardService.findByAndBoardId(andId, andBoardId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createAndBoard(@RequestBody DynamicAndBoardDTO.Update dynamicAndBoardUpdateDTO) {
        dynamicAndBoardService.save(dynamicAndBoardUpdateDTO);
    }

    @RequestMapping(value = "/{andBoardId}/update", method = RequestMethod.PATCH)
    public void updateAndBoard(@PathVariable("andId") int andId,
                               @PathVariable("andBoardId") int andBoardId,
                               @RequestBody DynamicAndBoardDTO.Update dynamicAndBoardUpdateDTO) {
        dynamicAndBoardService.update(dynamicAndBoardUpdateDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteByAndId(@PathVariable("andId") int andId) {
        dynamicAndBoardService.deleteByAndId(andId);
    }

    @RequestMapping(value = "/{andBoardId}/delete", method = RequestMethod.DELETE)
    public void deleteByAndBoardId(@PathVariable("andId") int andId,
                                   @PathVariable("andBoardId") int andBoardId) {
        dynamicAndBoardService.deleteByAndBoardId(andId, andBoardId);
    }
}
