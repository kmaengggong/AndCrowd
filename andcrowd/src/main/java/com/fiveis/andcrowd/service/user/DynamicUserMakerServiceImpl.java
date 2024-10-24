package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserMakerDTO;
import com.fiveis.andcrowd.dto.etc.ProjectDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import com.fiveis.andcrowd.entity.user.DynamicUserMaker;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import com.fiveis.andcrowd.repository.crowd.CrowdJPARepository;
import com.fiveis.andcrowd.repository.user.DynamicUserMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicUserMakerServiceImpl implements DynamicUserMakerService{
    private final DynamicUserMakerRepository dynamicUserMakerRepository;
    private final AndJPARepository andJPARepository;
    private final CrowdJPARepository crowdJPARepository;

    @Autowired
    public DynamicUserMakerServiceImpl(DynamicUserMakerRepository dynamicUserMakerRepository,
                                       AndJPARepository andJPARepository,
                                       CrowdJPARepository crowdJPARepository){
            this.dynamicUserMakerRepository = dynamicUserMakerRepository;
            this.andJPARepository = andJPARepository;
            this.crowdJPARepository = crowdJPARepository;
    }

    public List<ProjectDTO.Find> findAll(String userEmail){
        List<DynamicUserMakerDTO.Find> findList = dynamicUserMakerRepository.findAll(userEmail);
        List<ProjectDTO.Find> projectList = new ArrayList<>();
        for(DynamicUserMakerDTO.Find find : findList){
            // 모임
            if(find.getProjectType() == 0){
                if(andJPARepository.findById(find.getProjectId()).isEmpty()) continue;
                And and = andJPARepository.findById(find.getProjectId()).get();
                ProjectDTO.Find projectFind = ProjectDTO.Find.builder()
                        .projectId(and.getAndId())
                        .projectType(0)
                        .projectHeaderImg(and.getAndHeaderImg())
                        .projectTitle(and.getAndTitle())
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
                        .build();
                projectList.add(projectFind);
            }
        }
        return projectList;
    }

    public List<AndDTO.Find> findAllAnd(String userEmail){
        List<DynamicUserMakerDTO.Find> findList = dynamicUserMakerRepository.findAll(userEmail);
        List<AndDTO.Find> andList = new ArrayList<>();
        for(DynamicUserMakerDTO.Find find : findList){
            // 모임
            if(find.getProjectType() == 0){
                if(andJPARepository.findById(find.getProjectId()).isEmpty()) continue;
                AndDTO.Find and = AndDTO.convertToAndFindDTO(andJPARepository.findById(find.getProjectId()).get());
                andList.add(and);
            }
        }
        return andList;
    }

    public List<CrowdDTO.FindById> findAllCrowd(String userEmail){
        List<DynamicUserMakerDTO.Find> findList = dynamicUserMakerRepository.findAll(userEmail);
        List<CrowdDTO.FindById> crowdList = new ArrayList<>();
        for(DynamicUserMakerDTO.Find find : findList){
            // 모임
            if(find.getProjectType() == 1){
                if(crowdJPARepository.findById(find.getProjectId()).isEmpty()) continue;
                CrowdDTO.FindById crowd = CrowdDTO.convertToCrowdFindDTO(crowdJPARepository.findById(find.getProjectId()).get());
                crowdList.add(crowd);
            }
        }
        return crowdList;
    }

    public DynamicUserMakerDTO.Find findById(String userEmail, int uMakerId){
        return dynamicUserMakerRepository.findById(userEmail, uMakerId);
    }

    public boolean save(String userEmail, DynamicUserMaker dynamicUserMaker){
        // 존재하지 않는 projectId
        if(dynamicUserMaker.getProjectType() == 0){
            if(andJPARepository.findById(dynamicUserMaker.getProjectId()).isEmpty()) return false;
        }
        else{
            if(crowdJPARepository.findById(dynamicUserMaker.getProjectId()).isEmpty()) return false;
        }
        // user_maker에 이미 존재
        if(dynamicUserMakerRepository.findByProject(userEmail,
                dynamicUserMaker.getProjectId(),
                dynamicUserMaker.getProjectType()) != null) return false;
        // 그 외에는 저장 성공
        dynamicUserMakerRepository.save(userEmail, dynamicUserMaker);
        return true;
    }
    public void deleteById(String userEmail, int uMakerId){
        dynamicUserMakerRepository.deleteById(userEmail, uMakerId);
    }

    public void deleteByProjectId(String userEmail, int projectId, int projectType){
        dynamicUserMakerRepository.deleteByProjectId(userEmail, projectId, projectType);
    }

    public void deleteTableByUserEmail(String userEmail){
        dynamicUserMakerRepository.deleteTableByUserEmail(userEmail);
    }
}
