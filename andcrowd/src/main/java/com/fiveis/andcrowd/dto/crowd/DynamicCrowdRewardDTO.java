package com.fiveis.andcrowd.dto.crowd;

import lombok.*;

public class DynamicCrowdRewardDTO { // FindAllByUserId, update, save, delete
    @Getter @Setter
    @Builder @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindAllById { // 크라우드 리워드 조회
        private int rewardId;
        private int crowdId;
        private String rewardTitle;
        private String rewardContent;
        private int rewardAmount;
        private int rewardLimit;
        private int rewardLeft;
        private boolean isDeleted;
    }

    @Getter @Setter
    @Builder @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{ // 크라우드 리워드 업데이트
        private int rewardId;
        private int crowdId;
        private String rewardTitle;
        private String rewardContent;
        private int rewardAmount;
        private int rewardLimit;
        private int rewardLeft;
        private boolean isDeleted;
    }

    @Getter @Setter
    @Builder @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRewardLeft{
        private int rewardId;
        private int crowdId;
        private int rewardLeft;
    }

}
