package com.fiveis.andcrowd.dto.and;

import lombok.*;

import java.time.LocalDateTime;

public class DynamicAndQnaReplyDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindById {
        private int andReplyId;
        private int andQnaId;
        private int andId;
        private int userId;
        private String andReplyContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private int andReplyId;
        private int andQnaId;
        private int andId;
        private int userId;
        private String andReplyContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }
}
