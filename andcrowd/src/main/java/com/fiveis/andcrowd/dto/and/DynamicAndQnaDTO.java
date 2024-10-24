package com.fiveis.andcrowd.dto.and;

import lombok.*;

import java.time.LocalDateTime;


public class DynamicAndQnaDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindById {
        private int andQnaId;
        private int andId;
        private int userId;
        private String andQnaTitle;
        private String andQnaContent;
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
        private int andQnaId;
        private int andId;
        private int userId;
        private String andQnaTitle;
        private String andQnaContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }


}
