package com.fiveis.andcrowd.controller.etc;

import com.fiveis.andcrowd.dto.etc.AdPaymentDTO;
import com.fiveis.andcrowd.entity.etc.AdPayment;
import com.fiveis.andcrowd.service.etc.AdPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ad/payment")
public class AdPaymentController {

    private final AdPaymentService adPaymentService;

    @Autowired
    public AdPaymentController(AdPaymentService adPaymentService){
        this.adPaymentService = adPaymentService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<AdPaymentDTO.Find>> findAll(){
        List<AdPaymentDTO.Find> adPaymentList = adPaymentService.findAll();
        return ResponseEntity.ok(adPaymentList);
    }

    @RequestMapping(value = "/findById/{adPaymentId}", method = RequestMethod.GET)
    public ResponseEntity<AdPaymentDTO.Find> findById(@PathVariable int adPaymentId){
        AdPaymentDTO.Find adPayment = adPaymentService.findById(adPaymentId);
        return ResponseEntity.ok(adPayment);
    }

    @RequestMapping(value = "/findByUserId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<AdPaymentDTO.Find>> findAllByUserId(@PathVariable int userId){
        List<AdPaymentDTO.Find> adPaymentListByUserId = adPaymentService.findAllByUserId(userId);
        return ResponseEntity.ok(adPaymentListByUserId);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<String> insert(@RequestBody AdPayment adPayment){
        adPaymentService.save(adPayment);
        return ResponseEntity.ok("결제가 성공하였습니다.");
    }

    // delete의 경우 RequestMethod가 delete로 URL경로에 delete를 안붙여도 되나
    // find 기능과 통일성을 위해 추가
    @RequestMapping(value = "/delete/{adPaymentId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable int adPaymentId){
        adPaymentService.deleteById(adPaymentId);
        return ResponseEntity.ok("결제내역이 삭제되었습니다.");
    }
    // update의 경우 RequestMethod가 patch로 URL경로에 update를 안붙여도 되나
    // find 기능과 통일성을 위해 추가
    @RequestMapping(value = "/update/{adPaymentId}", method = RequestMethod.PATCH)
    public ResponseEntity<String> update(@RequestBody AdPaymentDTO.Update updateAdPayment){
        adPaymentService.update(updateAdPayment);
        return ResponseEntity.ok("결제내역이 수정되었습니다.");
    }
}
