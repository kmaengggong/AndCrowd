package com.fiveis.andcrowd.dto.crowd;

import com.fiveis.andcrowd.entity.crowd.Crowd;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CrowdDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class FindById {
        // findById시 필요한 멤버변수
        private int crowdId;
        private int adId;
        private int andId;
        private int crowdCategoryId;
        private String crowdContent;
        private LocalDate crowdEndDate;
        private int crowdGoal;
//        private String crowdImg1;
//        private String crowdImg2;
//        private String crowdImg3;
//        private String crowdImg4;
//        private String crowdImg5;
        private int crowdStatus; // 1:모집중, 2:반려, 3:종료, 4: 작성중, 0:심사중
        private String crowdTitle;
        private String headerImg;
        private boolean isDeleted;
        private int likeSum;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private int userId;
        private int viewCount;

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        // UpdateRequest시 필요한 멤버변수 : 크라우드Id, 카테고리Id, 본문, 마감일, 사진 1-5, 제목, 대표사진
        private int crowdId;
        private int crowdCategoryId;
        private String crowdContent;
        private LocalDate crowdEndDate;
//        private String crowdImg1;
//        private String crowdImg2;
//        private String crowdImg3;
//        private String crowdImg4;
//        private String crowdImg5;
        private String crowdTitle;
        private String headerImg;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class FindAllByUserId {
        //response(findAll)시 필요한 멤버변수 : 크라우드Id, 크라우드분야Id, 본문, 마감일, 목표금액, 상태, 제목, 대표사진, 업로드시간, 수정시간, 유저Id, 삭제여부
        private int crowdId;
        private int crowdCategoryId;
        private String crowdContent;
        private LocalDate crowdEndDate;
        private int crowdGoal;
        private int crowdStatus; // 1:모집중, 2:반려, 3:종료, 4: 작성중, 0:심사중
        private String crowdTitle;
        private String headerImg;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private int userId;
        private boolean isDeleted;

    }

    public static CrowdDTO.FindById convertToCrowdFindDTO(Crowd crowd) {
        CrowdDTO.FindById convertedDTO = FindById.builder()
                .crowdId(crowd.getCrowdId())
                .adId(crowd.getAdId())
                .andId(crowd.getAndId())
                .crowdCategoryId(crowd.getCrowdCategoryId())
                .crowdContent(crowd.getCrowdContent())
                .crowdEndDate(crowd.getCrowdEndDate())
                .crowdGoal(crowd.getCrowdGoal())
//                .crowdImg1(crowd.getCrowdImg1())
//                .crowdImg2(crowd.getCrowdImg2())
//                .crowdImg3(crowd.getCrowdImg3())
//                .crowdImg4(crowd.getCrowdImg4())
//                .crowdImg5(crowd.getCrowdImg5())
                .crowdStatus(crowd.getCrowdStatus())
                .crowdTitle(crowd.getCrowdTitle())
                .headerImg(crowd.getHeaderImg())
                .isDeleted(crowd.isDeleted())
                .likeSum(crowd.getLikeSum())
                .publishedAt(crowd.getPublishedAt())
                .updatedAt(crowd.getUpdatedAt())
                .userId(crowd.getUserId())
                .viewCount(crowd.getViewCount())
                .build();
        return convertedDTO;
    }

}
