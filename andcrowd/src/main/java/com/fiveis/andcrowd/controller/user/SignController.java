package com.fiveis.andcrowd.controller.user;

//import com.fiveis.andcrowd.config.jwt.TokenProvider;
import com.fiveis.andcrowd.config.jwt.TokenProvider;
import com.fiveis.andcrowd.dto.etc.AccessTokenResponseDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.service.etc.TokenService;
import com.fiveis.andcrowd.service.user.MailService;
import com.fiveis.andcrowd.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final MailService mailService;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public ResponseEntity<?> authenticate(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @RequestBody UserDTO.Login userLogin) {
        System.out.println("/login: " + userLogin);
        User userInfo = userService.getByCredentials(userLogin.getUserEmail());
        if(userInfo == null) return ResponseEntity.badRequest().body("Log In Failed");

        // 로그인 성공 시 리프레쉬 토큰, 엑세스 토큰 발급
        if(bCryptPasswordEncoder.matches(userLogin.getUserPassword(), userInfo.getUserPassword())){

            // 리프레쉬 토큰 생성
            String accessToken = tokenService.createAndSaveRTAndGetAT(request, response, userInfo);
            AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(accessToken);

            // 엑세스 토큰은 로컬에 저장하기 때문에 반환
            return ResponseEntity.ok(accessTokenResponseDTO);
        }
        else{
            return ResponseEntity.badRequest().body("Log In Failed");
        }
    }

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody User user){
        System.out.println("/signup: " + user);
        try{
            userService.save(user);
            return ResponseEntity.ok("Sign Up Success!");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Sign Up Failed");
        }
    }

    @RequestMapping(value="/passwordCheck", method=RequestMethod.POST)
    public ResponseEntity<?> passwordCheck(@RequestBody UserDTO.Login userLogin){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorityList = authentication
                .getAuthorities()
                .stream()
                .map(authority -> authority.toString())
                .toList();

        // 로그인 안 한 유저면 컷
        if(authorityList.contains("ROLE_ANONYMOUS")){
            return ResponseEntity.badRequest().body("Permission Denied");
        }

        User userInfo = userService.getByCredentials(userLogin.getUserEmail());
        System.out.println(userInfo);
        System.out.println(userLogin.getUserPassword());
        if(bCryptPasswordEncoder.matches(userLogin.getUserPassword(), userInfo.getUserPassword())){
            return ResponseEntity.ok("Correct Password");
        }
        else{
            return ResponseEntity.badRequest().body("Wrong Password");
        }
    }

    @RequestMapping(value="/mailAuth", method=RequestMethod.POST)
    public ResponseEntity<?> getEmailAuthNumber(@RequestBody Map<String, String> userEmail){
        System.out.println("/mailAuth: " + userEmail.get("userEmail"));
        String res = mailService.setEmail(userEmail.get("userEmail"));
        System.out.println(res);
        if(res == null) return ResponseEntity.internalServerError().body("서버 오류입니다.");
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value="/isEmailExists", method=RequestMethod.POST)
    public ResponseEntity<?> isEmailExists(@RequestBody Map<String, String> userEmail){
        System.out.println("/isEmailExists: " + userEmail.get("userEmail"));
        if(userService.findByUserEmail(userEmail.get("userEmail")) == null){
            return ResponseEntity.badRequest().body("존재하지 않는 이메일입니다.");
        }
        return ResponseEntity.ok("Email Exists");
    }

    @RequestMapping(value="/changePassword", method=RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> userInfo){
        System.out.println("userInfo: " + userInfo);

        UserDTO.Update update = UserDTO.Update.builder()
                .userEmail(userInfo.get("userEmail"))
                .userPassword(userInfo.get("userPassword"))
                .build();

        userService.update(update);
        return ResponseEntity.ok("UserPassoword Changed");
    }

    @RequestMapping(value="/nicknameCheck", method=RequestMethod.POST)
    public ResponseEntity<?> checkNickname(@RequestBody Map<String, String> userNickname){
        System.out.println("/nicknameCheck: " + userNickname.get("userNickname"));
        if(userService.findByUserNickname(userNickname.get("userNickname")) != null)
            return ResponseEntity.badRequest().body("Nickname already exist");
        else return ResponseEntity.ok("Good to use");
    }

    @RequestMapping(value="/updateForSocial", method=RequestMethod.POST)
    public ResponseEntity<?> updateForSocial(@RequestBody User user) throws Exception {
        User userInfo = userService.findByUserEmail(user.getUserEmail());
        userInfo.updateForSocial(user.getUserNickname(),
                user.getUserKorName(),
                user.getUserPrivacy(),
                user.getUserMarketing(),
                user.getUserTos());
        userService.udpateForSocial(userInfo);

        return ResponseEntity.ok("updateForSocial good");
    }
}
