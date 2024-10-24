package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdSponsorDTO;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crowd/{crowdId}/sponsor")
// @CrossOrigin(origins = "http://localhost:3000")
public class DynamicCrowdSponsorController {

    private final DynamicCrowdSponsorService dynamicCrowdSponsorService;

    @Autowired
    public DynamicCrowdSponsorController(DynamicCrowdSponsorService dynamicCrowdSponsorService) {
        this.dynamicCrowdSponsorService = dynamicCrowdSponsorService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<DynamicCrowdSponsorDTO.FindById>> findAll(@PathVariable int crowdId) {
        // crowd글 클릭 하면 후원자 리스트 조회
        List<DynamicCrowdSponsorDTO.FindById> sponsorList = dynamicCrowdSponsorService.findAll(crowdId);
        return ResponseEntity.ok()
                .body(sponsorList);
    }

    @RequestMapping(value = "/{sponsorId}", method = RequestMethod.GET)
    public ResponseEntity<?> findBySponsorId(@PathVariable int crowdId,
                                             @PathVariable int sponsorId) {
        // 특정 후원자 번호 클릭시 정보 조회
        DynamicCrowdSponsorDTO.FindById findSponsorId = dynamicCrowdSponsorService.findBySponsorId(crowdId, sponsorId);
        if(findSponsorId == null) {
            return new ResponseEntity<>("조회되지않는 ID의 후원자입니다.", HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(findSponsorId);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> insertCrowdSponsor(@RequestBody DynamicCrowdSponsorDTO.Update crowdSponsorAddDTO) {
        dynamicCrowdSponsorService.save(crowdSponsorAddDTO);
        return ResponseEntity.ok("프로젝트 펀딩 후원이 정상적으로 진행되었습니다.");
    }

    @RequestMapping(value = "/{sponsorId}/update", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> updateCrowdSponsor(@PathVariable int sponsorId,
                                                     @RequestBody DynamicCrowdSponsorDTO.Update crowdSponsorUpdateDTO) {
        crowdSponsorUpdateDTO.setSponsorId(sponsorId);
        dynamicCrowdSponsorService.update(crowdSponsorUpdateDTO);

        return ResponseEntity.ok("후원 정보가 수정되었습니다.");
    }

    @RequestMapping(value = "/{sponsorId}", method = RequestMethod.PATCH)
    public void deleteCrowdSponsorId(@PathVariable("crowdId") int crowdId,
                                     @PathVariable("sponsorId") int sponsorId) {
        dynamicCrowdSponsorService.deleteByCrowdSponsorId(crowdId, sponsorId);
    }
}