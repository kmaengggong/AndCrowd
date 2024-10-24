package com.fiveis.andcrowd.controller.user;

import com.fiveis.andcrowd.config.jwt.TokenProvider;
import com.fiveis.andcrowd.config.jwt.TokenValidator;
import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.*;
import com.fiveis.andcrowd.service.etc.TokenService;
import com.fiveis.andcrowd.service.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value="/user")
@RequiredArgsConstructor
public class DynamicUserController {
    private final UserService userService;
    private final DynamicUserAndService dynamicUserAndService;
    private final DynamicUserFollowService dynamicUserFollowService;
    private final DynamicUserLikeService dynamicUserLikeService;
    private final DynamicUserMakerService dynamicUserMakerService;
    private final DynamicUserOrderService dynamicUserOrderService;

    // 유저가 참여한 모임
    @RequestMapping(value="{userId}/and", method=RequestMethod.GET)
    public ResponseEntity<?> findUserAnd(@PathVariable int userId){
        System.out.println("/user/" + userId + "/and: " + userId);
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<AndDTO.Find> andDTOFindList = dynamicUserAndService.findAll(userEmail);
        andDTOFindList.sort((o1, o2) -> {
            if (o1.getAndId() > o2.getAndId()) return -1;
            return 1;
        });
        return ResponseEntity.ok(andDTOFindList);
    }

    @RequestMapping(value="{userId}/and", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserAnd(@PathVariable int userId, @RequestBody DynamicUserAnd dynamicUserAnd){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(!dynamicUserAndService.save(userEmail, dynamicUserAnd)) return ResponseEntity.badRequest().body("Save Failed!");
        return ResponseEntity.ok("UserAnd Saved!");
    }

    @RequestMapping(value="{userId}/and/{andId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserAnd(@PathVariable int userId, @PathVariable int andId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        dynamicUserAndService.deleteByAndId(userEmail, andId);
        return ResponseEntity.ok("UserAnd Deleted!");
    }

    // 유저가 팔로우한 유저
    @RequestMapping(value="{userId}/follow", method=RequestMethod.GET)
    public ResponseEntity<?> findUserFollowAll(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<UserDTO.FindAsPublic> userDTOFindList = dynamicUserFollowService.findAll(userEmail);
        return ResponseEntity.ok(userDTOFindList);
    }

    @RequestMapping(value="{userId}/follow/{fUserId}", method=RequestMethod.GET)
    public ResponseEntity<?> findUserFollow(@PathVariable int userId, @PathVariable int fUserId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserFollowService.findByUserId(userEmail, fUserId) != null){
            return ResponseEntity.ok("팔로우 된 유저입니다.");
        };
        return ResponseEntity.badRequest().body("팔로우 되지 않은 유저입니다.");
    }

    @RequestMapping(value="{userId}/follow/{fUserId}", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserFollow(@PathVariable int userId, @PathVariable int fUserId){
        if(userId == fUserId) return ResponseEntity.badRequest().body("똑같은 아이디");
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserFollowService.findByUserId(userEmail, fUserId) == null){
            DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder().userId(fUserId).build();
            if(!dynamicUserFollowService.save(userEmail, dynamicUserFollow)) return ResponseEntity.badRequest().body("Save Failed!");
        }
        else{
            dynamicUserFollowService.deleteByUserId(userEmail, fUserId);
        }

        return ResponseEntity.ok("UserFollow Saved!");
    }

    @RequestMapping(value="{userId}/follow/{fUserId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserFollow(@PathVariable int userId, @PathVariable int fUserId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        dynamicUserFollowService.deleteByUserId(userEmail, fUserId);
        return ResponseEntity.ok("UserFollow Deleted!");
    }

    // 유저가 좋아요 누른 프로젝트
    @RequestMapping(value="{userId}/like", method=RequestMethod.GET)
    public ResponseEntity<?> findUserLike(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<ProjectDTO.Find> projectDTOFindList = dynamicUserLikeService.findAll(userEmail);
        projectDTOFindList.sort((o1, o2) -> {
            if (o1.getProjectId() > o2.getProjectId()) return -1;
            return 1;
        });

        return ResponseEntity.ok(projectDTOFindList);
    }

    @RequestMapping(value="{userId}/like", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserLike(@PathVariable int userId, @RequestBody DynamicUserLike dynamicUserLike){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserLike.getProjectType() != 0 && dynamicUserLike.getProjectType() != 1)
            return ResponseEntity.badRequest().body("Wrong Value!");
        if(!dynamicUserLikeService.save(userEmail, dynamicUserLike)) return ResponseEntity.badRequest().body("Save Failed!");
        return ResponseEntity.ok("UserLike Saved!");
    }

    @RequestMapping(value="{userId}/like/{projectId}/{projectType}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserLike(@PathVariable int userId,
                                            @PathVariable int projectId,
                                            @PathVariable int projectType){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        dynamicUserLikeService.deleteByProjectId(userEmail, projectId, projectType);
        return ResponseEntity.ok("UserLike Deleted!");
    }

    // 유저가 만든 프로젝트
    @RequestMapping(value="{userId}/maker", method=RequestMethod.GET)
    public ResponseEntity<?> findUserMaker(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<ProjectDTO.Find> projectDTOFindList = dynamicUserMakerService.findAll(userEmail);
        projectDTOFindList.sort((o1, o2) -> {
            if (o1.getProjectId() > o2.getProjectId()) return -1;
            return 1;
        });

        return ResponseEntity.ok(projectDTOFindList);
    }

    @RequestMapping(value="{userId}/maker/0", method=RequestMethod.GET)
    public ResponseEntity<?> findUserMakerAnd(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<AndDTO.Find> andList = dynamicUserMakerService.findAllAnd(userEmail);
        andList.sort((o1, o2) -> {
            if (o1.getAndId() > o2.getAndId()) return -1;
            return 1;
        });

        return ResponseEntity.ok(andList);
    }

    @RequestMapping(value="{userId}/maker/1", method=RequestMethod.GET)
    public ResponseEntity<?> findUserMakerCrowd(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<CrowdDTO.FindById> crowdList = dynamicUserMakerService.findAllCrowd(userEmail);
        crowdList.sort((o1, o2) -> {
            if (o1.getCrowdId() > o2.getCrowdId()) return -1;
            return 1;
        });

        return ResponseEntity.ok(crowdList);
    }

    @RequestMapping(value="{userId}/maker", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserMaker(@PathVariable int userId, @RequestBody DynamicUserMaker dynamicUserMaker){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserMaker.getProjectType() != 0 && dynamicUserMaker.getProjectType() != 1)
            return ResponseEntity.badRequest().body("Wrong Value!");
        if(!dynamicUserMakerService.save(userEmail, dynamicUserMaker)) return ResponseEntity.badRequest().body("Save Failed!");
        return ResponseEntity.ok("UserMaker Saved!");
    }

    @RequestMapping(value="{userId}/maker/{projectId}/{projectType}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserMaker(@PathVariable int userId,
                                             @PathVariable int projectId,
                                             @PathVariable int projectType){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        dynamicUserMakerService.deleteByProjectId(userEmail, projectId, projectType);
        return ResponseEntity.ok("UserMaker Deleted!");
    }

    // 유저의 펀딩 주문 내역
    @RequestMapping(value="{userId}/order", method=RequestMethod.GET)
    public ResponseEntity<?> findUserOrder(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<CrowdOrderDetailsDTO.FindById> crowdOrderDetailsDTOFindList = dynamicUserOrderService.findAll(userEmail);
        crowdOrderDetailsDTOFindList.sort((o1, o2) -> {
            if (o1.getPurchaseId() > o2.getPurchaseId()) return -1;
            return 1;
        });
        return ResponseEntity.ok(crowdOrderDetailsDTOFindList);
    }

    @RequestMapping(value="{userId}/order", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserOrder(@PathVariable int userId, @RequestBody DynamicUserOrder dynamicUserOrder){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(!dynamicUserOrderService.save(userEmail, dynamicUserOrder)) return ResponseEntity.badRequest().body("Save Failed!");
        return ResponseEntity.ok("UserOrder Saved!");
    }

    @RequestMapping(value="{userId}/order/{orderId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserOrder(@PathVariable int userId,
                                             @PathVariable int orderId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        dynamicUserOrderService.deleteByOrderId(userEmail, orderId);
        return ResponseEntity.ok("UserOrder Deleted!");
    }

    // 유저의 주문한 펀딩 내역
    @RequestMapping(value="{userId}/crowd", method=RequestMethod.GET)
    public ResponseEntity<?> findUserCrowd(@PathVariable int userId){
        try{
            String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
            List<CrowdDTO.FindById> findList = dynamicUserOrderService.findAllCrowd(userEmail);
            return ResponseEntity.ok(findList);
        } catch(Error e){
            return ResponseEntity.badRequest().body("findUserCrowd error");
        }
    }
}
