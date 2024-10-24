package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.ReportDTO;
import com.fiveis.andcrowd.entity.etc.Report;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
    List<ReportDTO.Find> findAll();
    ReportDTO.Find findById(int reportId);
    void save(Report report);
    void update(ReportDTO.Update update);
    void deleteById(int reportId);
}
