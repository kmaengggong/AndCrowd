package com.fiveis.andcrowd.service;

import com.fiveis.andcrowd.dto.DynamicAndMemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicAndMemberService {
    void save(DynamicAndMemberDTO.Update dynamicAndMemberDTOUpdate);

    DynamicAndMemberDTO.FindByAndMemberId findByAndMemberId(int andId, int memberId);

    List<DynamicAndMemberDTO.FindByAndMemberId> findAll(int andId);

    void deleteById(int andId, int memberId);

    void createAndMemberTable();
    void dropAndMemberTable();

    void insertTestData();
}
