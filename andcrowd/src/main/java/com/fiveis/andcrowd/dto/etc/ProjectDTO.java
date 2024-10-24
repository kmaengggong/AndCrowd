package com.fiveis.andcrowd.dto.etc;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProjectDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int projectId;
        private int projectType;
        private String projectHeaderImg;
        private String projectTitle;
        private String projectContent;
        private LocalDate projectEndDate;
    }
}
