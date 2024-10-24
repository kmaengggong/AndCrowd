package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicAndMemberRepository {

    void save(DynamicAndMemberDTO.Update dynamicAndMemberDTOUpdate);

    DynamicAndMemberDTO.FindByAndMemberId findByAndMemberId(@Param("andId") int andId, @Param("memberId") int memberId);

    List<DynamicAndMemberDTO.FindByAndMemberId> findAll(int andId);

    List<DynamicAndMemberDTO.FindByAndMemberId> findAllNotDeletedByUserId(@Param("andId") int andId, @Param("userId") int userId);

    void deleteById(@Param("andId") int andId, @Param("memberId") int memberId);

    void deleteByUserId(@Param("andId") int andId, @Param("userId") int userId);

    void createAndMemberTable ();
    void dropAndMemberTable();
    void insertTestData();

    void update(DynamicAndMemberDTO.Update andMemberUpdateDTO);
    List<DynamicAndMemberDTO.FindByAndMemberId> findAllNotDeleted(int andId);

}