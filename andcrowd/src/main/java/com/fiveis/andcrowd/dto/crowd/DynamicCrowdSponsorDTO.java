package com.fiveis.andcrowd.dto.crowd;

import lombok.*;

public class DynamicCrowdSponsorDTO { // find(findAll, findById), Update
    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindById {
        private int sponsorId;
        private int crowdId;
        private int purchaseId;
        private boolean isDeleted;
    }

    @Getter @Setter
    @Builder @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private int sponsorId;
        private int crowdId;
        private int purchaseId;
        private boolean isDeleted;
    }
  
}
