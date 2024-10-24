package com.fiveis.andcrowd.controller.user;

import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.dto.user.UserS3DTO;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.service.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/user")
public class UserController {
    private final UserService userService;
    private final UserS3Service userS3Service;
    private final DynamicUserAndService dynamicUserAndService;
    private final DynamicUserFollowService dynamicUserFollowService;
    private final DynamicUserLikeService dynamicUserLikeService;
    private final DynamicUserMakerService dynamicUserMakerService;
    private final DynamicUserOrderService dynamicUserOrderService;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public ResponseEntity<?> findAllUser(){
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
                return ResponseEntity.ok(userService.findAllAsAdmin());
            }
            return ResponseEntity.ok(userService.findAllAsPublic());
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Find All User Failed");
        }
    }

    @RequestMapping(value="/{userId}", method=RequestMethod.GET)
    public ResponseEntity<?> findUser(@PathVariable int userId){
        try{
            // 권한 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDTO.FindAsAdmin findAsAdmin = userService.findById(userId);
            String userEmail = findAsAdmin.getUserEmail();

            List<String> authorityList = authentication
                    .getAuthorities()
                    .stream()
                    .map(authority -> authority.toString())
                    .toList();

            // 관리자 유저의 경우
            if(authorityList.contains("ROLE_ADMIN")){
                System.out.println("!!! 어드민 유저");
                return ResponseEntity.ok(findAsAdmin);
            }

            // 본인인 경우
            if(authentication.getName().equals(userEmail)){
                return ResponseEntity.ok(UserDTO.convertToFindAsUserDTO(userService.findByUserEmail(userEmail)));
            }

            // 본인이 아닌 경우
            return ResponseEntity.ok(UserDTO.convertToFindAsPublicDTO(userService.findByUserEmail(userEmail)));

        } catch(Exception e){
            return ResponseEntity.badRequest().body("User Not Found");
        }
    }

    @RequestMapping(value="/{userId}", method=RequestMethod.PATCH)
    public ResponseEntity<?> updateUser(@PathVariable int userId,
                                        @RequestBody UserDTO.Update update){
        // 권한 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorityList = authentication
                .getAuthorities()
                .stream()
                .map(authority -> authority.toString())
                .toList();

        // 본인이 아니라면 컷
        if(!authorityList.contains("ROLE_USER") ||
                !authentication.getName().equals(userService.findById(userId).getUserEmail())){
            return ResponseEntity.badRequest().body("Permission Denied");
        }

        try{
            if(update.getUserEmail() == null){
                update.setUserEmail(userService.findById(userId).getUserEmail());
            }
            System.out.println(update);
            userService.update(update);
            return ResponseEntity.ok("User Updated!");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("User Update Failed");
        }
    }

    @RequestMapping(value="/{userId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable int userId){
        System.out.println("user delete");
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
        }
        // 로그인 한 경우
        else if(authorityList.contains("ROLE_USER")){
            System.out.println("!!! 로그인한 유저");
            if(!authentication.getName().equals(userService.findById(userId).getUserEmail())){
                return ResponseEntity.badRequest().body("Permission Denied");
            }
        }
        // 로그인 안한 경우
        else{
            System.out.println("!!! 비로그인 유저 혹은 AccessToken 만료");
            return ResponseEntity.badRequest().body("Permission Denied");
        }

        // 관리자 유저 및 본인 유저만 접근 가능
        try{
            System.out.println("!!! 본인 혹은 어드민 유저");
            String userEmail = userService.findById(userId).getUserEmail();
            String tblUserEmail = User.toTableName(userEmail);
            dynamicUserAndService.deleteTableByUserEmail(tblUserEmail);
            dynamicUserFollowService.deleteTableByUserEmail(tblUserEmail);
            dynamicUserLikeService.deleteTableByUserEmail(tblUserEmail);
            dynamicUserMakerService.deleteTableByUserEmail(tblUserEmail);
            dynamicUserOrderService.deleteTableByUserEmail(tblUserEmail);
            userService.deleteByUserEmail(userEmail);
            return ResponseEntity.ok("User Deleted!");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("User Delete Failed");
        }
    }

    @RequestMapping(value="/uploadProfileImg", method=RequestMethod.POST)
    public ResponseEntity<Object> uploadProfileImg(
            @RequestParam(value="userId") int userId,
            @RequestParam(value="fileType") String fileType,
            @RequestParam(value="file")MultipartFile multipartFile){
        UserS3DTO profileImg = userS3Service.uploadFile(userId, fileType, multipartFile);
        return ResponseEntity.ok(profileImg);
    }

    @RequestMapping(value="{userId}/isNotAdmin", method=RequestMethod.GET)
    public ResponseEntity<?> isNotAdmin(@PathVariable int userId){
        try{
            UserDTO.FindAsAdmin findAsAdmin = userService.findById(userId);
            String role = findAsAdmin.getRole().toString();
            if(role.equals("ADMIN")){
                return ResponseEntity.badRequest().body("It's ADMIN user");
            }
            else{
                return ResponseEntity.ok("It's not ADMIN user");
            }
        } catch(Exception e){
            return ResponseEntity.internalServerError().body("isNotAdmin Error");
        }
    }
}