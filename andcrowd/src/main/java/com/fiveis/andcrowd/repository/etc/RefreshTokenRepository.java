package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.entity.etc.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken findByUserId(int userId);
    RefreshToken findByRefreshToken(String refreshToken);
}
