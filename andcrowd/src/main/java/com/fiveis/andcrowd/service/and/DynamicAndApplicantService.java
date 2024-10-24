package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndApplicantDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicAndApplicantService {
    List<DynamicAndApplicantDTO.FindById> findAll(int andId);
    List<DynamicAndApplicantDTO.FindById> findAllNotDeleted(int andId);
    DynamicAndApplicantDTO.FindById findByAndApplicantId(@Param("and_id") int andId, @Param("andApplyId") int andApplyId);
    List<DynamicAndApplicantDTO.FindById> findByUserId(@Param("and_id") int andId, @Param("userId") int userId);
    List<DynamicAndApplicantDTO.FindById> findByAndRoleId(@Param("and_id") int andId, @Param("andRoleId") int andRoleId);
    List<DynamicAndApplicantDTO.FindByIdWithCount> findByAndRoleIdWithCount(@Param("and_id") int andId);

    void save(DynamicAndApplicantDTO.Update andApplicantInsertDTO);
    void update(DynamicAndApplicantDTO.Update andApplicantUpdateDTO);
    void updateApplyStatus(@Param("andId") int andId, @Param("andApplyId") int andApplyId, @Param("andApplyStatus") int andApplyStatus);
    void deleteByAndApplicantId(@Param("andId") int andId, @Param("andApplyId") int andApplyId);

}
