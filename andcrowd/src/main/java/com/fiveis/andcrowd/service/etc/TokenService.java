package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.config.jwt.TokenProvider;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.etc.RefreshTokenRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import com.fiveis.andcrowd.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserJPARepository userJPARepository;

    public String createAndSaveRTAndGetAT(HttpServletRequest request,
                                       HttpServletResponse response,
                                       User user){
        // 리프레쉬 토큰 생성 후 저장
        String refreshToken = createNewRefreshTokenAndSave(user);

        // 엑세스 토큰 생성
        String accessToken = createNewAccessToken(user);

        // 리프레쉬 토큰 쿠키에 저장
        int cookieMaxAge = (int) TokenProvider.REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, CookieUtil.REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, CookieUtil.REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);

        return accessToken;
    }

    public String createNewRefreshTokenAndSave(User user){
        String refreshToken = tokenProvider.generateToken(user, TokenProvider.REFRESH_TOKEN_DURATION);
        tokenProvider.saveRefreshToken(user.getUserId(), refreshToken);
        return refreshToken;
    }

    public String createNewAccessToken(User user){
        return tokenProvider.generateToken(user, TokenProvider.ACCESS_TOKEN_DURATION);
    }

    public void deleteRefreshToken(HttpServletRequest request,
                                     HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, CookieUtil.REFRESH_TOKEN_COOKIE_NAME);
    }
}
