package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserLikeDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserLike;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicUserLikeService {
    List<ProjectDTO.Find> findAll(String userEmail);  // 글의 이미지, 제목, 내용을 ProjectDTO를 통해 가져옴
    DynamicUserLikeDTO.Find findById(String userEmail, int uLikeId);
    DynamicUserLikeDTO.Find findByProject(String userEmail,
                                          int projectId,
                                          int projectType);
    boolean save(String userEmail, DynamicUserLike dynamicUserLike);
    void deleteById(String userEmail, int uLikeId);
    void deleteByProjectId(String userEmail, int projectId, int projectType);
    void deleteTableByUserEmail(String userEmail);
}
