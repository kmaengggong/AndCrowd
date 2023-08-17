package com.fiveis.andcrowd.controller.user;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.*;
import com.fiveis.andcrowd.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value="/user")
public class DynamicUserController {
    private final UserService userService;
    private final DynamicUserAndService dynamicUserAndService;
    private final DynamicUserFollowService dynamicUserFollowService;
    private final DynamicUserLikeService dynamicUserLikeService;
    private final DynamicUserMakerService dynamicUserMakerService;
    private final DynamicUserOrderService dynamicUserOrderService;

    @Autowired
    public DynamicUserController(UserService userService,
                                 DynamicUserAndService dynamicUserAndService,
                                 DynamicUserFollowService dynamicUserFollowService,
                                 DynamicUserLikeService dynamicUserLikeService,
                                 DynamicUserMakerService dynamicUserMakerService,
                                 DynamicUserOrderService dynamicUserOrderService){
        this.userService = userService;
        this.dynamicUserAndService = dynamicUserAndService;
        this.dynamicUserFollowService = dynamicUserFollowService;
        this.dynamicUserLikeService = dynamicUserLikeService;
        this.dynamicUserMakerService = dynamicUserMakerService;
        this.dynamicUserOrderService = dynamicUserOrderService;
    }

    @RequestMapping(value="{userId}/and", method=RequestMethod.GET)
    public ResponseEntity<?> getUserAnd(@PathVariable int userId){//,
                                        //Principal principal){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<AndDTO.Find> andDTOFindList = dynamicUserAndService.findAll(userEmail);
        return ResponseEntity.ok(andDTOFindList);
    }

    @RequestMapping(value="{userId}/and", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserAnd(@PathVariable int userId, @RequestBody DynamicUserAnd dynamicUserAnd){//,
        //Principal principal){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserAnd.getAndId() == 0) return ResponseEntity.badRequest().body("Value Missing");
        dynamicUserAndService.save(userEmail, dynamicUserAnd);
        return ResponseEntity.ok("UserAnd Saved!");
    }

    @RequestMapping(value="{userId}/follow", method=RequestMethod.GET)
    public ResponseEntity<?> getUserFollow(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<UserDTO.FindAsPublic> userDTOFindList = dynamicUserFollowService.findAll(userEmail);
        return ResponseEntity.ok(userDTOFindList);
    }

    @RequestMapping(value="{userId}/follow", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserFollow(@PathVariable int userId, @RequestBody DynamicUserFollow dynamicUserFollow){//,
        //Principal principal){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserFollow.getUserId() == 0) return ResponseEntity.badRequest().body("Value Missing");
        dynamicUserFollowService.save(userEmail, dynamicUserFollow);
        return ResponseEntity.ok("UserFollow Saved!");
    }

    @RequestMapping(value="{userId}/like", method=RequestMethod.GET)
    public ResponseEntity<?> getUserLike(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<ProjectDTO.Find> projectDTOFindList = dynamicUserLikeService.findAll(userEmail);
        return ResponseEntity.ok(projectDTOFindList);
    }

    @RequestMapping(value="{userId}/like", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserLike(@PathVariable int userId, @RequestBody DynamicUserLike dynamicUserLike){//,
        //Principal principal){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserLike.getProjectId() == 0) return ResponseEntity.badRequest().body("Value Missing");
        dynamicUserLikeService.save(userEmail, dynamicUserLike);
        return ResponseEntity.ok("UserLike Saved!");
    }

    @RequestMapping(value="{userId}/maker", method=RequestMethod.GET)
    public ResponseEntity<?> getUserMaker(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<ProjectDTO.Find> projectDTOFindList = dynamicUserMakerService.findAll(userEmail);
        return ResponseEntity.ok(projectDTOFindList);
    }

    @RequestMapping(value="{userId}/maker", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserMaker(@PathVariable int userId, @RequestBody DynamicUserMaker dynamicUserMaker){//,
        //Principal principal){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserMaker.getProjectId() == 0) return ResponseEntity.badRequest().body("Value Missing");
        dynamicUserMakerService.save(userEmail, dynamicUserMaker);
        return ResponseEntity.ok("UserLike Saved!");
    }

    @RequestMapping(value="{userId}/order", method=RequestMethod.GET)
    public ResponseEntity<?> getUserOrder(@PathVariable int userId){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        List<CrowdOrderDetailsDTO.FindById> crowdOrderDetailsDTOFindList = dynamicUserOrderService.findAll(userEmail);
        return ResponseEntity.ok(crowdOrderDetailsDTOFindList);
    }

    @RequestMapping(value="{userId}/order", method=RequestMethod.POST)
    public ResponseEntity<?> saveUserOrder(@PathVariable int userId, @RequestBody DynamicUserOrder dynamicUserOrder){//,
        //Principal principal){
        String userEmail = User.toTableName(userService.findById(userId).getUserEmail());
        if(dynamicUserOrder.getOrderId() == 0) return ResponseEntity.badRequest().body("Value Missing");
        dynamicUserOrderService.save(userEmail, dynamicUserOrder);
        return ResponseEntity.ok("UserLike Saved!");
    }

}
