package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndBoardDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicAndBoardService {
    List<DynamicAndBoardDTO.FindById> findAll(int andId);
    List<DynamicAndBoardDTO.FindById> findAllNotDeleted(@Param("offset") int offset, @Param("limit") int limit, int andId);
    DynamicAndBoardDTO.FindById findByAndBoardId(int andId, int andBoardId);
    void deleteByAndId(int andId);
    void deleteByAndBoardId(int andId, int andBoardId);
    void save(DynamicAndBoardDTO.Update dynamicAndBoardUpdateDTO);
    void update(DynamicAndBoardDTO.Update dynamicAndBoardUpdateDTO);
    void deleteAll(int andId);

    void createAndBoardTable();
    void dropAndBoardTable();
    void insertTestData();

    int countAll(int andId);
}
