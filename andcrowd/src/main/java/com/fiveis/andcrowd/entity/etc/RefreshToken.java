package com.fiveis.andcrowd.entity.etc;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private int id;

    @Column(name="user_id", nullable = false, unique = true)
    private int userId;

    @Column(name="refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(int userId, String refreshToken){
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
