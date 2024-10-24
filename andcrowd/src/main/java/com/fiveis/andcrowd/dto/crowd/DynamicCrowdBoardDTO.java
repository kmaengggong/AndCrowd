package com.fiveis.andcrowd.dto.crowd;

import com.fiveis.andcrowd.entity.crowd.CrowdCategory;
import lombok.*;

import java.time.LocalDateTime;

public class DynamicCrowdBoardDTO {

    @Getter @Setter @ToString @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Find {
        private int crowdId;

        private int crowdBoardId;

        private int userId;

        private int crowdBoardTag;

        private String crowdBoardTitle;

        private String crowdBoardContent;

        private String crowdImg;

        private LocalDateTime publishedAt;

        private LocalDateTime updatedAt;

        private int viewCount;

        private boolean isDeleted;
    }

//    @Getter @Setter @ToString @Builder
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class Save {
//
//        private int crowdId;
//
//        private int crowdBoardTag;
//
//        private String crowdBoardTitle;
//
//        private String crowdBoardContent;
//
//        private String crowdImg;
//
//    }

    @Getter @Setter @ToString @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private int crowdId;

        private int crowdBoardId;

        private int userId;

        private int crowdBoardTag;

        private String crowdBoardTitle;

        private String crowdBoardContent;

        private String crowdImg;

        private LocalDateTime publishedAt;

        private LocalDateTime updatedAt;

        private int viewCount;

        private boolean isDeleted;

    }

}
