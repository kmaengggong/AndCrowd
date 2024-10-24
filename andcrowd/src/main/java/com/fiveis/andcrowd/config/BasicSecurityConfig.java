package com.fiveis.andcrowd.config;

import com.fiveis.andcrowd.config.jwt.TokenProvider;
import com.fiveis.andcrowd.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.fiveis.andcrowd.config.oauth.OAuth2UserCustomService;
import com.fiveis.andcrowd.config.oauth.OAuth2SuccessHandler;
import com.fiveis.andcrowd.repository.etc.RefreshTokenRepository;
import com.fiveis.andcrowd.service.etc.TokenService;
import com.fiveis.andcrowd.service.user.UserAuthorityService;
import com.fiveis.andcrowd.service.user.UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;


@Configuration
@RequiredArgsConstructor
// @CrossOrigin(origins="http://localhost:3000")
public class BasicSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final OAuth2UserCustomService oauth2UserCustomService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final TokenService tokenService;

    // 정적 파일이나 .jsp 파일 등 스프링 시큐리티가 기본적으로 적용되지 않을 영역 설정하기.
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/static/**")
                .dispatcherTypeMatchers(DispatcherType.FORWARD);

    }

    // http 요청에 대한 웹 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizationConfig -> {
                    authorizationConfig
                            .requestMatchers("/**", "/login", "/signup", "/user")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(formLoginConfig -> {
                    formLoginConfig
                            .disable(); // 토큰기반 로그인시에는 폼 로그인을 막아야 합니다.
                })
                .httpBasic(httpBasicConfig -> {
                    httpBasicConfig
                            .disable();
                })
                .logout(logoutConfig -> {
                    logoutConfig
                            .logoutUrl("/logout") // 디폴트로 "/logout"으로 잡아주기 때문에 굳이 설정할필요없음
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true);
                })
                .sessionManagement(sessionConfig -> {
                    sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .csrf(csrfConfig -> {
                    csrfConfig
                            .disable();
                })
                .oauth2Login(oauth2Config -> {
                    oauth2Config.loginPage("/login")
                            .authorizationEndpoint(endpointConfig ->
                                    endpointConfig.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                            .successHandler(oAuth2SuccessHandler())
                            .userInfoEndpoint(userInfoConfig ->
                                    userInfoConfig
                                            .userService(oauth2UserCustomService));
                })
                // Before시점(Request를 서버가 처리하기 직전 시점)에 해당 필터를 사용해 로그인을 검증하도록 설정
                .addFilterBefore(tokenAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserAuthorityService userAuthorityService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userAuthorityService) // userService에 기술된 내용을 토대로 로그인처리
                .passwordEncoder(bCryptPasswordEncoder) // 비밀번호 암호화 저장 모듈
                .and()
                .build();

    }

    // 암호화 모듈 임포트
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService, tokenService);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
}
