package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndApplicantDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import com.fiveis.andcrowd.repository.and.DynamicAndApplicantRepository;
import com.fiveis.andcrowd.repository.and.DynamicAndMemberRepository;
import com.fiveis.andcrowd.repository.and.DynamicAndRoleRepository;
import com.fiveis.andcrowd.repository.user.DynamicUserAndRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import com.fiveis.andcrowd.service.user.DynamicUserAndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fiveis.andcrowd.entity.user.User.toTableName;

@Service
public class DynamicAndApplicantServiceImpl implements DynamicAndApplicantService{

    DynamicAndApplicantRepository dynamicAndApplicantRepository;
    DynamicAndMemberRepository dynamicAndMemberRepository;
    AndJPARepository andJPARepository;
    DynamicUserAndService dynamicUserAndService;
    UserJPARepository userJPARepository;

    @Autowired
    public DynamicAndApplicantServiceImpl(
            DynamicAndApplicantRepository dynamicAndApplicantRepository,
            DynamicAndMemberRepository dynamicAndMemberRepository,
            AndJPARepository andJPARepository,
            DynamicUserAndService dynamicUserAndService,
            UserJPARepository userJPARepository){
        this.dynamicAndApplicantRepository = dynamicAndApplicantRepository;
        this.dynamicAndMemberRepository = dynamicAndMemberRepository;
        this.andJPARepository = andJPARepository;
        this.dynamicUserAndService = dynamicUserAndService;
        this.userJPARepository = userJPARepository;
    }


    @Override
    public List<DynamicAndApplicantDTO.FindById> findAll(int andId) {
        return dynamicAndApplicantRepository.findAll(andId);
    }

    @Override
    public List<DynamicAndApplicantDTO.FindById> findAllNotDeleted(int andId) {
        return dynamicAndApplicantRepository.findAllNotDeleted(andId);
    }

    @Override
    public DynamicAndApplicantDTO.FindById findByAndApplicantId(int andId, int andApplyId) {
        return dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId);
    }

    @Override
    public List<DynamicAndApplicantDTO.FindById> findByUserId(int andId, int userId) {
        return dynamicAndApplicantRepository.findByUserId(andId, userId);
    }

    @Override
    public List<DynamicAndApplicantDTO.FindById> findByAndRoleId(int andId, int andRoleId) {
        return dynamicAndApplicantRepository.findByAndRoleId(andId, andRoleId);
    }

    @Override
    public List<DynamicAndApplicantDTO.FindByIdWithCount> findByAndRoleIdWithCount(int andId) {
        return dynamicAndApplicantRepository.findByAndRoleIdWithCount(andId);
    }

    @Override
    public void save(DynamicAndApplicantDTO.Update andApplicantInsertDTO) {
        dynamicAndApplicantRepository.save(andApplicantInsertDTO);
    }

    @Override
    public void update(DynamicAndApplicantDTO.Update andApplicantUpdateDTO) {
        dynamicAndApplicantRepository.update(andApplicantUpdateDTO);
    }

    @Override
    public void updateApplyStatus(int andId, int andApplyId, int andApplyStatus) {

        // 상태 변경 전의 지원
        DynamicAndApplicantDTO.FindById beforeUpdate = dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId);
        System.out.println("beforeUpdate: "+ beforeUpdate);
        // 상태 변경
        dynamicAndApplicantRepository.updateApplyStatus(andId, andApplyId, andApplyStatus);

        // 상태 변경 후의 지원
        DynamicAndApplicantDTO.FindById afterUpdate = dynamicAndApplicantRepository.findByAndApplicantId(andId, andApplyId);
        System.out.println("afterUpdate: "+afterUpdate);

        String userEmail = toTableName(userJPARepository.findByUserId(afterUpdate.getUserId()).get().getUserEmail());
        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
                        .andId(andId)
                        .build();

        // 승인 상태로 상태가 변경된 경우
        if(afterUpdate.getAndApplyStatus() == 1){
            DynamicAndMemberDTO.Update newMember = DynamicAndMemberDTO.Update.builder()
                            .andId(andId)
                            .userId(afterUpdate.getUserId())
                            .andApplyId(andApplyId)
                            .isDeleted(false)
                            .build();
            dynamicAndMemberRepository.save(newMember);
            dynamicUserAndService.save(userEmail, dynamicUserAnd);
            // 승인 상태에서 다른 상태로 변경된 경우
        } else if (beforeUpdate.getAndApplyStatus() == 1 && afterUpdate.getAndApplyStatus() != 1) {
            dynamicAndMemberRepository.deleteByUserId(afterUpdate.getAndId(), afterUpdate.getUserId());
        }
    }

    @Override
    public void deleteByAndApplicantId(int andId, int andApplyId) {
        dynamicAndApplicantRepository.deleteByAndApplicantId(andId, andApplyId);
    }
}
