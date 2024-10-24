package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.etc.Report;
import com.fiveis.andcrowd.service.and.AndService;
import com.fiveis.andcrowd.service.etc.ReportService;
import com.fiveis.andcrowd.service.user.DynamicUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/and")
public class AndController {

    private final AndService andService;
    private final ReportService reportService;

    @Autowired
    public AndController(AndService andService, ReportService reportService) {
        this.andService = andService;
        this.reportService = reportService;
    }

    @RequestMapping("/list")
    public List<AndDTO.Find> getList() {
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
                return andService.findAll();
            }
            return andService.findAllByIsDeletedFalse();
        } catch(Exception e){
            return null;
        }
    }

    @GetMapping("/total/{searchKeyword}")
    public int getSearchSize(@PathVariable String searchKeyword){
        return andService.totalCount(searchKeyword);
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ResponseEntity<Integer> createItem(@RequestBody And and) {
        andService.save(and);
        // 생성된 andId 값을 응답 Body에 포함하여 클라이언트에게 전달
        return ResponseEntity.ok(and.getAndId());
    }

    @RequestMapping(value="/{andId}/create" , method=RequestMethod.PATCH)
    public void createAnd( @RequestBody And and) {
        andService.update(and);
    }

    @RequestMapping(value="/report", method = RequestMethod.POST)
    public ResponseEntity<String> createReport(@RequestBody Report report) {
        reportService.save(report);
        return ResponseEntity.ok("신고 완료");
    }


    @GetMapping("/{andId}")
    public AndDTO.Find getItem(@PathVariable("andId") int andId) {
        return andService.findById(andId).orElse(null);
    }

    @PutMapping("/{andId}/updateView")
    public ResponseEntity<String> updateView(@PathVariable("andId") int andId) {
        andService.updateView(andId);
        return ResponseEntity.ok("View count updated successfully.");
    }

    @PostMapping("/{andId}/like/{userId}")
    public ResponseEntity<String> updateLike(@PathVariable("andId") int andId, @PathVariable("userId") int userId) {
        System.out.println("here");
        andService.updateLike(andId, userId);
        return ResponseEntity.ok("like count updated successfully.");
    }

    @GetMapping("/{andId}/like/{userId}")
    public boolean isLike(@PathVariable("andId") int andId, @PathVariable("userId") int userId) {
        return andService.isLiked(andId, userId);
    }

    @GetMapping("/{myId}/follow/{userId}")
    public boolean isFollowed(@PathVariable("myId") int myId, @PathVariable("userId") int userId) {
        return andService.isFollowed(myId, userId);
    }

    @PostMapping("/{myId}/follow/{userId}")
    public ResponseEntity<String> updateFollow(@PathVariable("myId") int myId, @PathVariable("userId") int userId) {
        andService.updateFollow(myId, userId);
        return ResponseEntity.ok("follow updated successfully.");
    }



    @RequestMapping(value="/{andId}/update" , method=RequestMethod.PATCH)
    public void updateAnd( @RequestBody And and) {
        andService.update(and);
    }


    @GetMapping("/updateStatusForExpiredItems")
    public String updateStatusForExpiredItems() {
        andService.updateStatusForExpiredItems();
        return "Status updated for expired items.";
    }

    @RequestMapping(value="/{andId}/update/status" , method=RequestMethod.PATCH)
    public void updateAndStatus( @PathVariable("andId") int andId, @RequestBody Map<String, Integer> status) {
        andService.updateStatus(andId, status.get("status"));
    }

    @RequestMapping(value="/{andId}/update/status/{status}" , method=RequestMethod.PATCH)
    public void updateAndStatusPath( @PathVariable("andId") int andId, @PathVariable("status") int status) {
        andService.updateStatus(andId, status);
    }

    @RequestMapping(value = "/{andId}/update/needNumMem/{needNumMem}", method = RequestMethod.PATCH)
    public void updateNeedNumMem(@PathVariable("andId") int andId, @PathVariable("needNumMem") int needNumMem){
        andService.updateNeedNumMem(andId, needNumMem);
    }

    @RequestMapping("/{andId}/delete")
    public void deleteItem(@PathVariable("andId") int andId) {
        andService.deleteById(andId);
    }
}
