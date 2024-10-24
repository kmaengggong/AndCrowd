package com.fiveis.andcrowd.entity.etc;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;  // 신고 ID

    @Column(nullable = false)
    private int userId;  // 신고한 유저 ID

    @Column(nullable = false)
    private int projectId;  // 신고한 프로젝트 ID

    @Column(nullable = false)
    private int projectType;  // 신고한 프로젝트 유형

    @Column(nullable = false)
    private String reportContent;  // 신고 내용

    @Column(nullable = false)
    private LocalDateTime reportDate;  // 신고일

    @Column(nullable = false)
    private int reportStatus;  // 신고 상태(0: 처리중, 1: 처리완료, 2: 처리반려)

    @PrePersist
    public void setDefaultValue(){
        this.reportDate = LocalDateTime.now();
    }

    public void setReportStatus(int reportStatus) {
        this.reportStatus = reportStatus;
    }
}
