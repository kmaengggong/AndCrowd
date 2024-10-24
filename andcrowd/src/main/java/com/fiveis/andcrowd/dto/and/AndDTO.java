package com.fiveis.andcrowd.dto.and;

import com.fiveis.andcrowd.entity.and.And;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AndDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find {

        private int andId;
        private int userId;
        private int andCategoryId;
        private int crowdId;
        private String andTitle;
        private String andHeaderImg;
        private String andContent;
        private LocalDate andEndDate;
        private int needNumMem;
//        private String andImg1;
//        private String andImg2;
//        private String andImg3;
//        private String andImg4;
//        private String andImg5;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private int andLikeCount;
        private int andViewCount;
        private int andStatus;
        private int adId;
        private boolean isDeleted;

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private int andId;
        private int userId;
        private int andCategoryId;
        private int crowdId;
        private String andTitle;
        private String andHeaderImg;
        private String andContent;
        private LocalDate andEndDate;
        private int needNumMem;
//        private String andImg1;
//        private String andImg2;
//        private String andImg3;
//        private String andImg4;
//        private String andImg5;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private int adId;
        private boolean isDeleted;

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAllByUserId {
        private int andId;
        private int userId;
        private String andTitle;
        private String andHeaderImg;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private int andStatus;
        private int adId;
        private boolean isDeleted;

    }

    public static AndDTO.Find convertToAndFindDTO(And and) {
        AndDTO.Find convertedDTO = AndDTO.Find.builder()
                .andId(and.getAndId())
                .userId(and.getUserId())
                .andCategoryId(and.getAndCategoryId())
                .crowdId(and.getCrowdId())
                .andTitle(and.getAndTitle())
                .andHeaderImg(and.getAndHeaderImg())
                .andContent(and.getAndContent())
                .andEndDate(and.getAndEndDate())
                .needNumMem(and.getNeedNumMem())
//                .andImg1(and.getAndImg1())
//                .andImg2(and.getAndImg2())
//                .andImg3(and.getAndImg3())
//                .andImg4(and.getAndImg4())
//                .andImg5(and.getAndImg5())
                .publishedAt(and.getPublishedAt())
                .updatedAt(and.getUpdatedAt())
                .andLikeCount(and.getAndLikeCount())
                .andViewCount(and.getAndViewCount())
                .andStatus(and.getAndStatus())
                .adId(and.getAdId())
                .isDeleted(and.isDeleted())
                .build();
        return convertedDTO;
    }


}
