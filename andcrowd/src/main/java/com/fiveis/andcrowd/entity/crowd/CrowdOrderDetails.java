package com.fiveis.andcrowd.entity.crowd;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crowd_order_details")
public class CrowdOrderDetails { // 크라우드 펀딩 후원결제

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseId; // 펀딩 결제 ID

    //@Column(nullable = false)
    private int userId; // 결제한 유저 ID

    //@Column(nullable = false)
    private int crowdId; // 펀딩 ID

    //@Column(nullable = false)
    private int rewardId; // 리워드 ID
    private String rewardName;  // 리워드 이름

    @Column(nullable = false)
    private String merchantUid;  // 실제 결제 ID

    @Column(nullable = false)
    private String purchaseName; // 결제자 이름

    @Column(nullable = false)
    private String purchasePhone; // 결제자 핸드폰번호

    @Column(nullable = false)
    private String purchaseAddress; // 결제자 주소

    @Column(nullable = false)
    private LocalDateTime purchaseDate; // 결제일

    @Column(nullable = false)
    private String purchaseStatus; // 결제상태 (결제대기, 결제완료, 결제취소)

    @Column(nullable = false)
    private int purchaseAmount;

    @ColumnDefault("false")
    private boolean isDeleted; // 삭제여부

    public void setPurchaseDate(LocalDateTime purchaseDate){
        this.purchaseDate = LocalDateTime.now();
    }

}