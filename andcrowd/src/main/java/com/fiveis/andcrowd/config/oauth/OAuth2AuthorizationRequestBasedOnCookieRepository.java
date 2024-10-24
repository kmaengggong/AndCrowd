package com.fiveis.andcrowd.config.oauth;

import com.fiveis.andcrowd.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

public class OAuth2AuthorizationRequestBasedOnCookieRepository
    implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private final static int COOKIE_EXPIRE_SECOND = 18000;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        System.out.println("loadAuthorizationRequest");
        // 토큰을 발급해서 쿠키에 실어서 사용자측에 보냈을때 쿠키에서 토큰만 추려내기
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        // 쿠키에 대한 정보를 역직렬화해 자바 내부에서 쓸 수 있게 만들어서 리턴
        return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("saveAuthorizatonRequest");
        // 저장하려고 했지만 요청정보가 null인경우(비정상적인 인증요청인 경우)
        if(authorizationRequest == null){
            removeAuthorizationRequest(request, response);// 인증정보 제거 후
            System.out.println("비정상 인증 요청");
            return; // 종료
        }
        // 쿠키에 직렬화된 토큰 정보 저장하기
        CookieUtil.addCookie(response,
                OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtil.serialize(authorizationRequest),
                COOKIE_EXPIRE_SECOND);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    // 실질적인 삭제는 쿠키 삭제로 이뤄짐
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
