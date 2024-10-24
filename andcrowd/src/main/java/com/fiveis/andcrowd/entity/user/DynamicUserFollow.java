package com.fiveis.andcrowd.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicUserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uFollowId;  // 유저 팔로우 ID

    @Column(nullable = false)
    private int userId;  // 팔로우한 유저 ID
}
