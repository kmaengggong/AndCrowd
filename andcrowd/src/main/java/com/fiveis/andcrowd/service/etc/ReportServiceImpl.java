package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.ReportDTO;
import com.fiveis.andcrowd.entity.etc.Report;
import com.fiveis.andcrowd.repository.etc.ReportJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    private static ReportJPARepository reportJPARepository;

    @Autowired
    public  ReportServiceImpl(ReportJPARepository reportJPARepository){
        this.reportJPARepository = reportJPARepository;
    }

    @Override
    public List<ReportDTO.Find> findAll() {
        List<Report> reportList = reportJPARepository.findAll();
        List<ReportDTO.Find> findList = new ArrayList<>();
        for(Report report : reportList){
            findList.add(ReportDTO.convertToReportDTOFind(report));
        }
        return findList;
    }

    @Override
    public ReportDTO.Find findById(int reportId) {
        if(reportJPARepository.findById(reportId).isEmpty()) return null;
        return ReportDTO.convertToReportDTOFind(reportJPARepository.findById(reportId).get());
    }

    @Override
    public void save(Report report) {
        reportJPARepository.save(report);
    }

    @Override
    public void update(ReportDTO.Update update) {
        Report report = reportJPARepository.findById(update.getReportId()).get();
        report.setReportStatus(update.getReportStatus());
        reportJPARepository.save(report);
    }

    @Override
    public void deleteById(int reportId) {
        reportJPARepository.deleteById(reportId);
    }
}
