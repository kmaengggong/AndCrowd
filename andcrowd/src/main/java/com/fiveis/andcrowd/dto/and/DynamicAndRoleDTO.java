package com.fiveis.andcrowd.dto.and;

import lombok.*;

import java.time.LocalDateTime;

public class DynamicAndRoleDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindById {
        private int andRoleId;
        private int andId;
        private String andRole;
        private int andRoleLimit;
        private boolean isDeleted;
    }
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AndRoleWithApplicantsDTO {
        private int andRoleId;
        private int andId;
        private String andRole;
        private int andRoleLimit;
        private int applicantCount; // 추가: 지원 인원 수
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private int andRoleId;
        private int andId;
        private String andRole;
        private int andRoleLimit;
        private boolean isDeleted;
    }
}
