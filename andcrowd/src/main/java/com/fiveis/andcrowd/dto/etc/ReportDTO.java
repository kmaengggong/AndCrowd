package com.fiveis.andcrowd.dto.etc;

import com.fiveis.andcrowd.entity.etc.Report;
import lombok.*;

import java.time.LocalDateTime;

public class ReportDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        int reportId;
        int userId;
        int projectId;
        int projectType;
        String reportContent;
        LocalDateTime reportDate;
        int reportStatus;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update{
        int reportId;
        int reportStatus;
    }

    public static ReportDTO.Find convertToReportDTOFind(Report report){
        return ReportDTO.Find.builder()
                .reportId(report.getReportId())
                .userId(report.getUserId())
                .projectId(report.getProjectId())
                .projectType(report.getProjectType())
                .reportContent(report.getReportContent())
                .reportDate(report.getReportDate())
                .reportStatus(report.getReportStatus())
                .build();
    }
}
