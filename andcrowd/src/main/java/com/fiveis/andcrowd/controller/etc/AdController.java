package com.fiveis.andcrowd.controller.etc;

import com.fiveis.andcrowd.dto.etc.AdDTO;
import com.fiveis.andcrowd.entity.etc.Ad;
import com.fiveis.andcrowd.service.etc.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ad")
public class AdController {

    private final AdService adService;

    @Autowired
    public AdController(AdService adService){
        this.adService = adService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<AdDTO.Find>> findAll() {
        List<AdDTO.Find> adList = adService.findAll();
        return ResponseEntity.ok(adList);
    }

    @RequestMapping(value = "/{adId}", method = RequestMethod.GET)
    public ResponseEntity<AdDTO.Find> findById(@PathVariable int adId){
        AdDTO.Find ad = adService.findById(adId);
        return ResponseEntity.ok(ad);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> insert(@RequestBody Ad ad){
        adService.save(ad);
        return ResponseEntity.ok("정상적으로 광고가 등록되었습니다.");
    }

    @RequestMapping(value = "/{adId}", method = RequestMethod.PATCH)
    public ResponseEntity<String> update(@RequestBody AdDTO.Update updateAd){
        adService.update(updateAd);
        return ResponseEntity.ok("광고가 정상적으로 수정되었습니다.");
    }

    @RequestMapping(value = "/{adId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int adId){
        adService.deleteById(adId);
        return ResponseEntity.ok(adId + "번 광고가 정상 삭제되었습니다.");
    }
}
