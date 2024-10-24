package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.user.DynamicUserLikeDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.entity.user.DynamicUserLike;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import com.fiveis.andcrowd.repository.crowd.CrowdJPARepository;
import com.fiveis.andcrowd.repository.user.DynamicUserLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fiveis.andcrowd.entity.user.User.toTableName;

@Service
public class DynamicUserLikeServiceImpl implements DynamicUserLikeService{
    private final DynamicUserLikeRepository dynamicUserLikeRepository;
    private final AndJPARepository andJPARepository;
    private final CrowdJPARepository crowdJPARepository;

    @Autowired
    public DynamicUserLikeServiceImpl(DynamicUserLikeRepository dynamicUserLikeRepository,
                                      AndJPARepository andJPARepository,
                                      CrowdJPARepository crowdJPARepository){
        this.dynamicUserLikeRepository = dynamicUserLikeRepository;
        this.andJPARepository = andJPARepository;
        this.crowdJPARepository = crowdJPARepository;
    }

    public List<ProjectDTO.Find> findAll(String userEmail){
        List<DynamicUserLikeDTO.Find> findList = dynamicUserLikeRepository.findAll(userEmail);
        List<ProjectDTO.Find> projectList = new ArrayList<>();
        for(DynamicUserLikeDTO.Find find : findList){
            // 모임
            if(find.getProjectType() == 0){
                if(andJPARepository.findById(find.getProjectId()).isEmpty()) continue;
                And and = andJPARepository.findById(find.getProjectId()).get();
                ProjectDTO.Find projectFind = ProjectDTO.Find.builder()
                        .projectId(and.getAndId())
                        .projectType(0)
                        .projectHeaderImg(and.getAndHeaderImg())
                        .projectTitle(and.getAndTitle())
                        .projectContent(and.getAndContent())
                        .projectEndDate(and.getAndEndDate())
                        .build();
                projectList.add(projectFind);
            }
            // 펀딩
            else{
                if(crowdJPARepository.findById(find.getProjectId()).isEmpty()) continue;
                Crowd crowd = crowdJPARepository.findById(find.getProjectId()).get();
                ProjectDTO.Find projectFind = ProjectDTO.Find.builder()
                        .projectId(crowd.getCrowdId())
                        .projectType(1)
                        .projectHeaderImg(crowd.getHeaderImg())
                        .projectTitle(crowd.getCrowdTitle())
                        .projectContent(crowd.getCrowdContent())
                        .projectEndDate(crowd.getCrowdEndDate())
                        .build();
                projectList.add(projectFind);
            }
        }
        return projectList;
    }

    public DynamicUserLikeDTO.Find findById(String userEmail, int uLikeId){
        return dynamicUserLikeRepository.findById(userEmail, uLikeId);
    }

    @Override
    public DynamicUserLikeDTO.Find findByProject(String userEmail, int projectId, int projectType) {
        String convertedUserEmail = toTableName(userEmail);
        return dynamicUserLikeRepository.findByProject(convertedUserEmail, projectId, projectType);
    }

    public boolean save(String userEmail, DynamicUserLike dynamicUserLike){
        // 존재하지 않는 projectId
        if(dynamicUserLike.getProjectType() == 0){
            if(andJPARepository.findById(dynamicUserLike.getProjectId()).isEmpty()) return false;
        }
        else{
            if(crowdJPARepository.findById(dynamicUserLike.getProjectId()).isEmpty()) return false;
        }
        // user_like에 이미 존재
        if(dynamicUserLikeRepository.findByProject(userEmail,
                dynamicUserLike.getProjectId(),
                dynamicUserLike.getProjectType()) != null) return false;
        // 그 외에는 저장 성공
        dynamicUserLikeRepository.save(userEmail, dynamicUserLike);
        return true;
    }

    public void deleteById(String userEmail, int uLikeId){
        dynamicUserLikeRepository.deleteById(userEmail, uLikeId);
    }

    public void deleteByProjectId(String userEmail, int projectId, int projectType){
        dynamicUserLikeRepository.deleteByProjectId(userEmail, projectId, projectType);
    }

    public void deleteTableByUserEmail(String userEmail){
        dynamicUserLikeRepository.deleteTableByUserEmail(userEmail);
    }
}
