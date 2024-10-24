package com.fiveis.andcrowd.dto.crowd;

import lombok.*;

import java.time.LocalDateTime;

public class DynamicCrowdQnaDTO {

    @Getter @Setter @ToString @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Find {
        private int crowdQnaId;
        private int crowdId;
        private int userId;
        private String qnaTitle;
        private String qnaContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }

    @Getter @Setter @ToString @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private int crowdQnaId;
        private int crowdId;
        private int userId;
        private String qnaTitle;
        private String qnaContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }
}
