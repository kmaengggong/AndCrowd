package com.fiveis.andcrowd.entity.etc;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adId;  // 광고 ID

    @Column(nullable = false)
    private String adName;  // 광고 이름

    @Column(nullable = false)
    private int adPrice;  // 광고 가격
}
