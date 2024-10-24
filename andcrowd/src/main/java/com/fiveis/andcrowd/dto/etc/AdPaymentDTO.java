package com.fiveis.andcrowd.dto.etc;

import com.fiveis.andcrowd.entity.etc.AdPayment;
import lombok.*;

import java.time.LocalDateTime;

public class AdPaymentDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int adPaymentId;
        private int adId;
        private int userId;
        private int projectId;
        private int projectType;
        private LocalDateTime purchasedAt;
        private LocalDateTime expiredAt;
        private int adPaymentStatus;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{
        private int adPaymentId;
        private LocalDateTime expiredAt;
        private int adPaymentStatus;
    }

    public static AdPaymentDTO.Find converToAdPaymentDTOFind(AdPayment adPayment){
        return Find.builder()
                .adPaymentId(adPayment.getAdPaymentId())
                .adId(adPayment.getAdId())
                .userId(adPayment.getUserId())
                .projectId(adPayment.getProjectId())
                .projectType(adPayment.getProjectId())
                .purchasedAt(adPayment.getPurchasedAt())
                .expiredAt(adPayment.getExpiredAt())
                .adPaymentStatus(adPayment.getAdPaymentStatus())
                .build();
    }
}
