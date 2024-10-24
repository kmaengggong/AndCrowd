package com.fiveis.andcrowd.controller.etc;

import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import com.fiveis.andcrowd.service.user.UserService;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    private final UserService userService;
    private final UserJPARepository userJPARepository;

    public UserInfoController(UserService userService, UserJPARepository userJPARepository) {
        this.userService = userService;
        this.userJPARepository = userJPARepository;
    }

    @GetMapping("/nickname/{userId}")
    public String getUserNickname(@PathVariable int userId){
        String userNickname = userJPARepository.findByUserId(userId).get().getUserNickname();

        return userNickname;
    }

    @GetMapping("/profileImg/{userId}")
    public String getUserProfileImg(@PathVariable int userId){
        String userProfileImg = userJPARepository.findByUserId(userId).get().getUserProfileImg();

        return userProfileImg;
    }


    @GetMapping
    public String getUserInfo() {
        // 현재 인증된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 이메일 정보 가져오기
        String userEmail = authentication.getName();

        return "User Email: " + userEmail;
    }

    @GetMapping("/nickname")
    public ResponseEntity<String> getNickname() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        User user = userService.findByUserEmail(userEmail);
        String nickname = user.getUserNickname();

        // JSON 응답을 생성하여 반환
        JSONObject response = new JSONObject();
        response.put("nickname", nickname);

        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/userid")
    public ResponseEntity<String> getUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        User user = userService.findByUserEmail(userEmail);
        int userId = user.getUserId();

        JSONObject response = new JSONObject();
        response.put("userId", userId);

        System.out.println("response.toString(): "+response.toString());

        return ResponseEntity.ok(response.toString());
    }


}
