package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserMakerDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserMaker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicUserMakerService {
    List<ProjectDTO.Find> findAll(String userEmail);  // 글의 이미지, 제목, 내용을 ProjectDTO를 통해 가져옴
    List<AndDTO.Find> findAllAnd(String userEmail);
    List<CrowdDTO.FindById> findAllCrowd(String userEmail);
    DynamicUserMakerDTO.Find findById(String userEmail, int uMakerId);
    boolean save(String userEmail, DynamicUserMaker dynamicUserMaker);
    void deleteById(String userEmail, int uMakerId);
    void deleteByProjectId(String userEmail, int projectId, int projectType);
    void deleteTableByUserEmail(String userEmail);
}
