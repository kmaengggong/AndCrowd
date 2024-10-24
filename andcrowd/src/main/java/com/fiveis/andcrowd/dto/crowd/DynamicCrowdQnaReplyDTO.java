package com.fiveis.andcrowd.dto.crowd;

import lombok.*;

import java.time.LocalDateTime;

public class DynamicCrowdQnaReplyDTO {
    @Getter @Setter @ToString @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Find {
        private int crowdId;
        private int qnaReplyId;
        private int crowdQnaId;
        private int userId;
        private String qnaReplyContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }

    @Getter @Setter @ToString @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private int crowdId;
        private int qnaReplyId;
        private int crowdQnaId;
        private int userId;
        private String qnaReplyContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }
}
