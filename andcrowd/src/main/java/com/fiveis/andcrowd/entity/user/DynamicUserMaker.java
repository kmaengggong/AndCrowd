package com.fiveis.andcrowd.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicUserMaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uMakerId;  // 유저 생성 프로젝트 ID

    @Column(nullable = false)
    private int projectId;  // 프로젝트 ID

    @Column(nullable = false)
    private int projectType ;  // 프로젝트 유형(0: 모임, 1: 펀딩)
}
