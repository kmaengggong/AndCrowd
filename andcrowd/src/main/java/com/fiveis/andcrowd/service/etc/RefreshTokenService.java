package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.entity.etc.RefreshToken;
import com.fiveis.andcrowd.repository.etc.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
}
