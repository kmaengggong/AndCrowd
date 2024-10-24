package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.entity.etc.Report;
import com.fiveis.andcrowd.repository.etc.ReportJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReportJPARepositoryTest {
    @Autowired
    ReportJPARepository reportJPARepository;

    @Test
    @BeforeEach
    @DisplayName("C: ")
    public void saveTest(){
        // Given
        int reportId = 1;
        int userId = 1;
        int projectId = 1;
        int projectType = 0;
        String reportContent = "신고 부탁드립니다.";
        LocalDateTime reportDate = LocalDateTime.now();
        int reportStatus = 0;

        Report report = Report.builder()
                .reportId(reportId)
                .userId(userId)
                .projectId(projectId)
                .projectType(projectType)
                .reportContent(reportContent)
                .reportDate(reportDate)
                .reportStatus(reportStatus)
                .build();

        // When
        reportJPARepository.save(report);

        // Then
    }

    // JPA 이기 때문에 기타 기능 테스트 생략. 후에 기능 생기면 추가할 예정
}
