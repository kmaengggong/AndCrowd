package com.fiveis.andcrowd.dto.and;

import lombok.*;

public class DynamicAndApplicantDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindById {
        private int andApplyId;
        private int andId;
        private int userId;
        private int andRoleId;
        private String andApplyTitle;
        private String andApplyContent;
        private String andApplyFile;
        private int andApplyStatus;
        private boolean isDeleted;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private int andApplyId;
        private int andId;
        private int userId;
        private int andRoleId;
        private String andApplyTitle;
        private String andApplyContent;
        private String andApplyFile;
        private int andApplyStatus; // 1: 합격, 2: 보류, 3: 탈락
        private boolean isDeleted;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindByIdWithCount {
        private int andRoleId;
        private int count;
    }
}
