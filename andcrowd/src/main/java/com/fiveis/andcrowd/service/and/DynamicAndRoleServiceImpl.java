package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndApplicantDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndRoleDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndApplicantRepository;
import com.fiveis.andcrowd.repository.and.DynamicAndRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicAndRoleServiceImpl implements DynamicAndRoleService{

    DynamicAndRoleRepository dynamicAndRoleRepository;
    DynamicAndApplicantRepository dynamicAndApplicantRepository;

    @Autowired
    public DynamicAndRoleServiceImpl(DynamicAndRoleRepository dynamicAndRoleRepository,
                                     DynamicAndApplicantRepository dynamicAndApplicantRepository){
        this.dynamicAndRoleRepository = dynamicAndRoleRepository;
        this.dynamicAndApplicantRepository = dynamicAndApplicantRepository;
    }

    public List<DynamicAndRoleDTO.AndRoleWithApplicantsDTO> getRolesWithApplicantCounts(int andId){
        List<DynamicAndRoleDTO.FindById> roles = dynamicAndRoleRepository.findAllNotDeleted(andId);
        List<DynamicAndApplicantDTO.FindByIdWithCount> applyRoleCounts = dynamicAndApplicantRepository.findByAndRoleIdWithCount(andId);

        // 역할과 지원 인원 수를 매핑
        Map<Integer, Integer> roleCountMap = new HashMap<>();
        for (DynamicAndApplicantDTO.FindByIdWithCount count : applyRoleCounts) {
            roleCountMap.put(count.getAndRoleId(), count.getCount());
        }

        // 역할 정보에 지원 인원 수 추가 및 변환
        List<DynamicAndRoleDTO.AndRoleWithApplicantsDTO> rolesWithCounts = new ArrayList<>();
        for (DynamicAndRoleDTO.FindById role : roles) {
            DynamicAndRoleDTO.AndRoleWithApplicantsDTO roleWithCount = new DynamicAndRoleDTO.AndRoleWithApplicantsDTO();
            roleWithCount.setAndRoleId(role.getAndRoleId());
            roleWithCount.setAndId(role.getAndId());
            roleWithCount.setAndRole(role.getAndRole());
            roleWithCount.setAndRoleLimit(role.getAndRoleLimit());
            roleWithCount.setApplicantCount(roleCountMap.getOrDefault(role.getAndRoleId(), 0));
            rolesWithCounts.add(roleWithCount);
        }

        return rolesWithCounts;
    }

    @Override
    public List<DynamicAndRoleDTO.FindById> findAll(int andId) {
        return dynamicAndRoleRepository.findAll(andId);
    }

    @Override
    public List<DynamicAndRoleDTO.FindById> findAllNotDeleted(int andId) {
        return dynamicAndRoleRepository.findAllNotDeleted(andId);
    }

    @Override
    public DynamicAndRoleDTO.FindById findByAndRoleId(int andId, int andRoleId) {
        return dynamicAndRoleRepository.findByAndRoleId(andId, andRoleId);
    }

    @Override
    public void save(DynamicAndRoleDTO.Update andRoleInsertDTO) {
        dynamicAndRoleRepository.save(andRoleInsertDTO);
    }

    @Override
    public void update(DynamicAndRoleDTO.Update andRoleUpdateDTO) {
        dynamicAndRoleRepository.update(andRoleUpdateDTO);
    }

    @Override
    public void deleteByAndRoleId(int andId, int andRoleId) {
        dynamicAndRoleRepository.deleteByAndRoleId(andId, andRoleId);
    }
}
