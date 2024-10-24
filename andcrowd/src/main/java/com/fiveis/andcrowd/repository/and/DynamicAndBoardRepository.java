package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndApplicantDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicAndBoardRepository {
    List<DynamicAndBoardDTO.FindById> findAll(int andId);
    List<DynamicAndBoardDTO.FindById> findAllNotDeleted(@Param("offset") int offset, @Param("limit") int limit, @Param("andId") int andId);
    DynamicAndBoardDTO.FindById findByAndBoardId(@Param("andId") int andId, @Param("andBoardId") int andBoardId);
    void save(DynamicAndBoardDTO.Update andBoardInsertDTO);
    void update(DynamicAndBoardDTO.Update andBoardUpdateDTO);
    void deleteByAndId(@Param("andId") int andId);
    void deleteByAndBoardId(@Param("andId") int andId, @Param("andBoardId") int andBoardId);
    void deleteAll(@Param("andId") int andId);

    void createAndBoardTable();
    void dropAndBoardTable();
    void insertTestData();
    void createAndTestBoardTable();

    int countNotDeleted(int andId);

}
