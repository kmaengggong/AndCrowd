package com.fiveis.andcrowd.dto.crowd;

import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
import lombok.*;

import java.time.LocalDateTime;

public class CrowdOrderDetailsDTO { // findAll, findById, update, save, delete
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class FindById {
        private int purchaseId;
        private int userId;
        private int crowdId;
        private int rewardId;
        private String rewardName;
        private String merchantUid;
        private String purchaseName;
        private String purchasePhone;
        private String purchaseAddress;
        private LocalDateTime purchaseDate;
        private String purchaseStatus;
        private boolean isDeleted;

        public static CrowdOrderDetailsDTO.FindById convertToFindByIdDTO(CrowdOrderDetails crowdOrderDetails) {
            return FindById.builder()
                    .purchaseId(crowdOrderDetails.getPurchaseId())
                    .userId(crowdOrderDetails.getUserId())
                    .crowdId(crowdOrderDetails.getCrowdId())
                    .rewardId(crowdOrderDetails.getRewardId())
                    .rewardName(crowdOrderDetails.getRewardName())
                    .merchantUid(crowdOrderDetails.getMerchantUid())
                    .purchaseName(crowdOrderDetails.getPurchaseName())
                    .purchasePhone(crowdOrderDetails.getPurchasePhone())
                    .purchaseAddress(crowdOrderDetails.getPurchaseAddress())
                    .purchaseDate(crowdOrderDetails.getPurchaseDate())
                    .purchaseStatus(crowdOrderDetails.getPurchaseStatus())
                    .isDeleted(crowdOrderDetails.isDeleted())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class Update {
        private int purchaseId;
        private int userId;
        private int crowdId;
        private int rewardId;
        private String rewardName;
        private String merchantUid;
        private String purchaseName;
        private String purchasePhone;
        private String purchaseAddress;
        private LocalDateTime purchaseDate;
        private String purchaseStatus;
        private boolean isDeleted;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class RewardSales {
        private int rewardId; // 리워드ID
        private String rewardName; // 리워드 이름
        private Long rewardCounts; // 해당 리워드의 판매량
        private int rewardAmount; // 리워드 가격
        private int rewardSale; // 해당 리워드의 총 판매액
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class RewardCounts {
        private int rewardId; // 리워드ID
        private String rewardName; // 리워드 이름
        private Long rewardCounts; // 해당 리워드의 판매량

        public RewardCounts(int rewardId, String rewardName, Long rewardCounts) {
            this.rewardId = rewardId;
            this.rewardName = rewardName;
            this.rewardCounts = rewardCounts;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class Manage {
        private int purchaseId;
        private int userId;
        private String rewardName;
        private int purchaseAmount;
        private String purchaseStatus;
        private LocalDateTime purchaseDate;
        private String purchaseName;
        private String purchasePhone;
        private String purchaseAddress;

    }
    public static CrowdOrderDetailsDTO.Manage convertToManageDTO(CrowdOrderDetails crowdOrderDetails) {
        return Manage.builder()
                .purchaseId(crowdOrderDetails.getPurchaseId())
                .userId(crowdOrderDetails.getUserId())
                .rewardName(crowdOrderDetails.getRewardName())
                .purchaseAmount(crowdOrderDetails.getPurchaseAmount())
                .purchaseStatus(crowdOrderDetails.getPurchaseStatus())
                .purchaseDate(crowdOrderDetails.getPurchaseDate())
                .purchaseName(crowdOrderDetails.getPurchaseName())
                .purchasePhone(crowdOrderDetails.getPurchasePhone())
                .purchaseAddress(crowdOrderDetails.getPurchaseAddress())
                .build();
    }

}
