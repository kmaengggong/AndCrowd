package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndRoleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicAndRoleService {
    List<DynamicAndRoleDTO.FindById> findAll(int andId);
    List<DynamicAndRoleDTO.FindById> findAllNotDeleted(int andId);
    DynamicAndRoleDTO.FindById findByAndRoleId(int andId, int andRoleId);
    List<DynamicAndRoleDTO.AndRoleWithApplicantsDTO> getRolesWithApplicantCounts(int andId);
    void save(DynamicAndRoleDTO.Update andRoleInsertDTO);
    void update(DynamicAndRoleDTO.Update andRoleUpdateDTO);
    void deleteByAndRoleId(@Param("andId") int andId, @Param("andRoleId") int andRoleId);


}
