package com.fiveis.andcrowd.config.jwt;

import com.fiveis.andcrowd.entity.etc.RefreshToken;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.enums.Role;
import com.fiveis.andcrowd.repository.etc.RefreshTokenRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(2); // 이틀(리프레시 토큰의 유효기간)
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2); // 2시간(억세스 토큰의 유효기간)

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserJPARepository userJPARepository;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secret_key}")
    private String secretKey;


    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getUserEmail())
                .claim("userId", user.getUserId()) // 클레임에는 PK 제공
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void saveRefreshToken(int userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);

        if(refreshToken != null){
            refreshToken.update(newRefreshToken);
        }
        else{
            refreshToken = new RefreshToken(userId, newRefreshToken);
        }

        refreshTokenRepository.save(refreshToken);
    }

    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isRefreshTokenValid(String refreshToken){
        if(!validToken(refreshToken)) return false;
        return refreshTokenRepository.findByRefreshToken(refreshToken) != null;
    }

    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        String role =  userJPARepository.findByUserEmail(claims.getSubject()).get().getRole().toString();
        Set<SimpleGrantedAuthority> authorities = null;

        if(role.equals("ADMIN")){
            authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
            System.out.println("ROLE_ADMIN");
        }
        else{
            authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("ROLE_USER");
        }

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),
                        "", authorities), token, authorities);
    }

    public Integer getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", Integer.class);
    }

    public String getUserEmail(String token){
        Claims claims = getClaims(token);
        return claims.get("sub", String.class);
    }

    public Date getExpirationDate(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){
        return !getExpirationDate(token).before(new Date());
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
