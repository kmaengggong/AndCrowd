package com.fiveis.andcrowd.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uLikeId;  // 유저 좋아요 ID

    @Column(nullable = false)
    private int projectId;  // 좋아요한 프로젝트 ID

    @Column(nullable = false)
    private int uLikeKind;  // 프로젝트 유형(0: 모임, 1: 펀딩)
}
