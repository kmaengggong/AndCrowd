package com.fiveis.andcrowd.controller.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.service.crowd.CrowdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/crowd")
@CrossOrigin(origins = "http://localhost:3000")
public class CrowdController {

    private final CrowdService crowdService;

    @Autowired
    public CrowdController(CrowdService crowdService) {
        this.crowdService = crowdService;
    }

    @GetMapping(value = "/list")
    public List<CrowdDTO.FindById> getlist() {
        try{
            // 권한 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> authorityList = authentication
                    .getAuthorities()
                    .stream()
                    .map(authority -> authority.toString())
                    .toList();

            // 관리자 유저의 경우
            if(authorityList.contains("ROLE_ADMIN")){
                System.out.println("!!! 어드민 유저");
                return crowdService.findAll();
            }
            return crowdService.findAllByIsDeletedFalse();
        } catch(Exception e){
            return null;
        }
    }


    @GetMapping(value = "/page")
    public ResponseEntity<Page<CrowdDTO.FindById>> searchPageList(
            @RequestParam(value = "crowdCategoryId", required = false) Integer crowdCategoryId,
            @RequestParam(value = "crowdStatus", required = false) Integer crowdStatus,
            @RequestParam(value = "sortField", defaultValue = "publishedAt",required = false) String sortField,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Pageable pageable){

        Page<CrowdDTO.FindById> crowdPage = crowdService.searchPageList(crowdStatus, sortField, pageNumber, crowdCategoryId, searchKeyword, pageable);

        return ResponseEntity.ok(crowdPage);
    }



    @PostMapping(value = "/create")
    public ResponseEntity<Integer> createCrowd(@RequestBody Crowd crowd) {
        crowdService.save(crowd);
        return ResponseEntity.ok(crowd.getCrowdId());
    }

    @GetMapping(value = "/{crowdId}")
    public ResponseEntity<?> getCrowd(@PathVariable("crowdId") int crowdId) {
        // 특정번호의 펀딩글 조회
        Optional<CrowdDTO.FindById> findCrowd = crowdService.findByCrowdId(crowdId);

        if (!findCrowd.isPresent()) {
            return new ResponseEntity<>("이 펀딩글은 마감되거나 등록되지 않은 펀딩 글번호입니다.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(findCrowd.get());
        }
    }

    @RequestMapping(value = "/{crowdId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> updateCrowd(@RequestBody Crowd crowd) {
        System.out.println(crowd);
        crowdService.update(crowd);
        return ResponseEntity.ok("펀딩글이 수정되었습니다.");
    }

    @RequestMapping(value = "/{crowdId}/delete")
    public String deleteCrowd(@PathVariable("crowdId") int crowdId) {
        crowdService.deleteByCrowdId(crowdId);
        ResponseEntity.ok("펀딩글이 삭제되었습니다.");
        return "redirect:/crowd/list";
    }

    @RequestMapping(value="/{crowdId}/update/status" , method=RequestMethod.PATCH)
    public void updateCrowdStatus( @PathVariable("crowdId") int crowdId, @RequestBody Map<String, Integer> status) {
        crowdService.updateStatus(crowdId, status.get("status"));
    }

    @GetMapping(value = "/popular/top5")
    public ResponseEntity<List<CrowdDTO.FindById>> findByViewCountAndLikeSum() {
        List<CrowdDTO.FindById> popularList = crowdService.findByViewCountAndLikeSum();
        return ResponseEntity.ok(popularList);
    }

}