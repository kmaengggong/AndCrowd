package com.fiveis.andcrowd.controller.etc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveis.andcrowd.config.jwt.TokenProvider;
import com.fiveis.andcrowd.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.fiveis.andcrowd.dto.etc.AccessTokenResponseDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.enums.SocialType;
import com.fiveis.andcrowd.service.etc.TokenService;
import com.fiveis.andcrowd.service.user.NaverService;
import com.fiveis.andcrowd.service.user.UserService;
import com.fiveis.andcrowd.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TokenController {
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final NaverService naverService;

    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private String grantType;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @RequestMapping(value="/accessTokenValid", method=RequestMethod.POST)
    public ResponseEntity<?> isAccessTokenValid(HttpServletRequest request,
                                                HttpServletResponse response){
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
            }
            // 로그인 한 경우
            else if(authorityList.contains("ROLE_USER")){
                System.out.println("!!! 로그인한 유저");
            }
            // 로그인 안한 경우
            else{
                System.out.println("!!! 비로그인 유저 혹은 AccessToken 만료");
            }

            // 쿠키를 통해 리프레쉬 토큰 추출
            Cookie[] cookies = request.getCookies();
            String refreshToken = "";
            if(cookies == null) return ResponseEntity.badRequest().body("NoRefreshToken");
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("refresh_token")){
                    refreshToken = cookie.getValue();
                    break;
                }
            }

            if(!tokenProvider.validToken(refreshToken)) return ResponseEntity.badRequest().body("InvalidRefreshToken");

            int userId = tokenProvider.getUserId(refreshToken);
            UserDTO.FindAsAdmin findAsAdmin = userService.findById(userId);
            User user = userService.getByCredentials(findAsAdmin.getUserEmail());

            // 새 리프레쉬 토큰 생성
            String accessToken = tokenService.createAndSaveRTAndGetAT(request, response, user);
            AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(accessToken);

            // 엑세스 토큰은 로컬에 저장하기 때문에 반환
            return ResponseEntity.ok(accessTokenResponseDTO);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("/accessTokenValid: " + e);
        }
    }

    @RequestMapping(value="/oauth/naver", method=RequestMethod.POST)
    public ResponseEntity<?> naverLogin(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody Map<String, String> callback) throws Exception {
        System.out.println("naverLogin");

        // 프론트 엔드를 통해 code, state를 받아옴
        String code = callback.get("code");
        String state = callback.get("state");
        String uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/token?")
                .queryParam("grant_type", grantType)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("state", state)
                .toUriString();

        // 네이버 토큰 요청
        String responseToken = naverService.getNaverToken(uri);
        if(responseToken == null) return ResponseEntity.badRequest().body("Naver login failed: Could not get token");

        // 네이버 토큰 -> Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> tokenMap = objectMapper.readValue(responseToken, Map.class);
        String accessToken = tokenMap.get("access_token");
        String tokenType = tokenMap.get("token_type");
        System.out.println("tokenMap: " + tokenMap);

        // 네이버 토큰을 통해 유저 정보 요청
        String userInfo = naverService.getUserInfo(tokenType, accessToken);
        if(userInfo == null) return ResponseEntity.badRequest().body("Naver login failed: Could not get userinfo");

        // 유저 정보 -> 읽을 수 있는 값으로
        Map<String, Object> userInfoMap = objectMapper.readValue(userInfo, Map.class);
        System.out.println("userInfoMap: " + userInfoMap);
        String responseObject = userInfoMap.get("response").toString();
        System.out.println("responseObject: " + responseObject);
        responseObject = responseObject
                .replace("{", "")
                .replace("}", "");
        String[] responseList = responseObject.split(",");
        String id = "";
        String nickname = "";
        String email = "";
        String name = "";
        for(String res : responseList){
            if(res.contains("id=")){
                id = res.replace("id=", "").trim();
            }
            if(res.contains("nickname=")){
                nickname = res.replace("nickname=", "").trim();
            }
            if(res.contains("email=")){
                email = res.replace("email=", "").trim();
            }
            if(res.contains("name=")){
                name = res.replace("name=", "").trim();
            }
        }

        // 필터를 통해 잘못 생성된 토큰 제거
        CookieUtil.deleteCookie(request, response,
                OAuth2AuthorizationRequestBasedOnCookieRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);

        User getUser = userService.findByUserEmail(email);

        // 유저가 있으면 바로 로그인 시도
        if(getUser != null){
            if(getUser.getSocialId().equals(id)){
                // 로그인
                getUser.setSocialType(SocialType.NAVER);
                userService.udpateForSocial(getUser);

                // 리프레쉬 토큰 생성
                String at = tokenService.createAndSaveRTAndGetAT(request, response, getUser);
                AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(at);

                // 엑세스 토큰은 로컬에 저장하기 때문에 반환
                return ResponseEntity.ok(accessTokenResponseDTO);

            }
            else{
                return ResponseEntity.badRequest().body("Naver login failed: userinfo corrupted");
            }
        }

        // 유저 저장 후 로그인 처리
        User user = User.builder()
            .userEmail(email)
            .userNickname(nickname)
            .userKorName(name)
            .socialType(SocialType.NAVER)
            .socialId(id)
            .userPrivacy(1)
            .userTos(1)
            .build();

        userService.save(user);

        // 리프레쉬 토큰 생성
        String at = tokenService.createAndSaveRTAndGetAT(request, response, user);
        AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(at);

        // 엑세스 토큰은 로컬에 저장하기 때문에 반환
        return ResponseEntity.ok(accessTokenResponseDTO);
    }

    @RequestMapping(value="/isAdmin", method=RequestMethod.GET)
    public ResponseEntity<Boolean> isAdmin(){
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
                return ResponseEntity.ok(true);
            }
            return ResponseEntity.ok(false);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(false);
        }
    }
}
