package com.fiveis.andcrowd.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenValidator {
    private final ObjectMapper objectMapper;

    @Value("${jwt.secret_key}")
    private String secretKey;

    public boolean isAccessTokenValid(String accessToken){
        Date now = new Date();
        Map<String, Object> token = objectMapper.convertValue(
                Jwts.parser()
                    .setSigningKey(secretKey)
                    .parse(accessToken)
                    .getBody(),
                Map.class
        );
        for(String key : token.keySet()){
            System.out.println(key + ": " + token.get(key));
        }
        System.out.println("require: " + Jwts.parser()
                .setSigningKey(secretKey)
                .requireExpiration(now));
        System.out.println(token);
        return true;
    }
}
