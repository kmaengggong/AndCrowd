package com.fiveis.andcrowd.controller.etc;

import com.fiveis.andcrowd.dto.etc.ReportDTO;
import com.fiveis.andcrowd.entity.etc.Report;
import com.fiveis.andcrowd.service.and.AndService;
import com.fiveis.andcrowd.service.crowd.CrowdService;
import com.fiveis.andcrowd.service.etc.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/report")
public class ReportController {
    private final ReportService reportService;
    private final AndService andService;
    private final CrowdService crowdService;

    @RequestMapping(value="/create", method= RequestMethod.POST)
    public ResponseEntity<?> createReport(@RequestBody Report report){
        try{
            reportService.save(report);
            return ResponseEntity.ok("Report save");
        } catch(Error e){
            return ResponseEntity.badRequest().body("Report save error");
        }
    }

    @RequestMapping(value="/{reportId}", method= RequestMethod.GET)
    public ResponseEntity<?> findByReportId(@PathVariable int reportId){
        try{
            ReportDTO.Find find = reportService.findById(reportId);
            return ResponseEntity.ok(find);
        } catch(Error e){
            return ResponseEntity.badRequest().body("Report find error");
        }
    }

    @RequestMapping(value="/list", method= RequestMethod.GET)
    public ResponseEntity<?> findAllReport(){
        try{
            List<ReportDTO.Find> reportList = reportService.findAll();
            return ResponseEntity.ok(reportList);
        } catch(Error e){
            return ResponseEntity.badRequest().body("Report findAll error");
        }
    }

    @RequestMapping(value="/{reportId}", method= RequestMethod.PATCH)
    public ResponseEntity<?> createReport(@PathVariable int reportId,
                                          @RequestBody ReportDTO.Update update){
        try{
            int status = update.getReportStatus();

            switch(status){
                case 1:
                    ReportDTO.Find find = reportService.findById(reportId);
                    if(find.getProjectType() == 0){
                        andService.deleteById(find.getProjectId());
                    }
                    else{
                        crowdService.deleteByCrowdId(find.getProjectId());
                    }
                    break;
                case 2:
                    break;
                default:
                    return ResponseEntity.badRequest().body("Report update error: Invalid status");
            }
            reportService.update(update);
            return ResponseEntity.ok("Report updated");
        } catch(Error e){
            return ResponseEntity.badRequest().body("Report update error");
        }
    }

    @RequestMapping(value="/{reportId}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteReport(@PathVariable int reportId){
        try{
            reportService.deleteById(reportId);
            return ResponseEntity.ok("Report deleted");
        } catch(Error e){
            return ResponseEntity.badRequest().body("Report delete error");
        }
    }

}
