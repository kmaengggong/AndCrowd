package com.fiveis.andcrowd.entity.etc;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdPayment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adPaymentId;  // 광고 결제 ID

    @Column(nullable = false)
    private int adId;  // 광고 ID

    @Column(nullable = false)
    private int userId;  // 결제한 유저 ID

    @Column(nullable = false)
    private int projectId;  // 프로젝트 ID

    @Column(nullable = false)
    private int projectType;  // 프로젝트 유형

    @Column(nullable = false)
    private LocalDateTime purchasedAt;  // 결제일

    @Column(nullable = false)
    private LocalDateTime expiredAt;  // 만료일

    @Column(nullable = false)
    private int adPaymentStatus;   // 결제 상태(0: 결제대기, 1: 결제완료, 2: 환불)

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt){
        this.purchasedAt = purchasedAt;
    }

    public void setAdPaymentStatus(int adPaymentStatus) {
        this.adPaymentStatus = adPaymentStatus;
    }
}
