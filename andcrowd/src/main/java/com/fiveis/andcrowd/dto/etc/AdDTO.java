package com.fiveis.andcrowd.dto.etc;

import com.fiveis.andcrowd.entity.etc.Ad;
import lombok.*;

public class AdDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int adId;
        private String adName;
        private int adPrice;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{
        private int adId;
        private String adName;
        private int adPrice;
    }

    public static AdDTO.Find convertToAdDTOFind(Ad ad){
        return AdDTO.Find.builder()
                .adId(ad.getAdId())
                .adName(ad.getAdName())
                .adPrice(ad.getAdPrice())
                .build();
    }
}
