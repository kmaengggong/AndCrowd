package com.fiveis.andcrowd.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicUserAnd {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uAndId;  // 유저 참가 모임 ID

//    @Column(nullable = false)
    private int andId;  // 참여한 모임 ID
}