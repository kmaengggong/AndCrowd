package com.fiveis.andcrowd.entity.crowd;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicCrowdSponsor { // 크라우드 후원자

    private int sponsorId;

    private int crowdId;

    private int purchaseId;

    private boolean isDeleted;
}