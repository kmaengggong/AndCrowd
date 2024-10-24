package com.fiveis.andcrowd.entity.crowd;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DynamicCrowdReward { // 리워드 카테고리

    private int rewardId;

    private int crowdId;

    private String rewardTitle; // 리워드 제목

    private String rewardContent; // 리워드 본문

    private int rewardAmount; // 리워드 금액

    private int rewardLimit; // 리워드 제한

    private int rewardLeft;  // 남은 리워드

    private boolean isDeleted;
}