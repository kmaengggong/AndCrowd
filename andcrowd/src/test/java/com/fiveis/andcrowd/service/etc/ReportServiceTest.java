package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.ReportDTO;
import com.fiveis.andcrowd.entity.etc.Report;
import com.fiveis.andcrowd.service.etc.ReportService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ReportServiceTest {
    @Autowired
    ReportService reportService;

    @Test
    @BeforeEach
    @DisplayName("C: 각 테스트마다 임시 데이터 저장")
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
        reportService.save(report);

        // Then
    }

    @Test
    @Transactional
    @DisplayName("R: 모든 신고 내역 조회")
    public void findAllTest(){
        // Given
        int size = 1;

        // When
        List<ReportDTO.Find> findList = reportService.findAll();

        // Then
        Assertions.assertEquals(size, findList.size());
    }

    @Test
    @Transactional
    @DisplayName("R: 1번 신고 내역 조회")
    public void findByIdTest() {
        // Given
        int reportId = 1;

        // When
        ReportDTO.Find find = reportService.findById(reportId);

        // Then
        Assertions.assertEquals(reportId, find.getReportId());
    }

    @Test
    @Transactional
    @DisplayName("U: 1번 신고 내역 변경 후 조회")
    public void updateTest() {
        // Given
        int reportId = 1;
        int reportStatus = 1;

        ReportDTO.Update update = ReportDTO.Update.builder()
                .reportId(reportId)
                .reportStatus(reportStatus)
                .build();

        // When
        reportService.update(update);
        ReportDTO.Find find = reportService.findById(reportId);

        // Then
        Assertions.assertEquals(reportStatus, find.getReportStatus());
    }

    @Test
    @Transactional
    @DisplayName("D: 1번 신고 내역 삭제 후 조회")
    public void deleteByIdTest(){
        // Given
        int reportId = 1;

        // When
        reportService.deleteById(reportId);

        // Then
        Assertions.assertNull(reportService.findById(reportId));
    }
}
