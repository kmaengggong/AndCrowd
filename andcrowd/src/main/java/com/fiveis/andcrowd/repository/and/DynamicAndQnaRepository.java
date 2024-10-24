package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicAndQnaRepository {
    List<DynamicAndQnaDTO.FindById> findAll(int andId);
    List<DynamicAndQnaDTO.FindById> findAllNotDeleted(@Param("offset") int offset, @Param("limit") int limit, @Param("andId") int andId);
    DynamicAndQnaDTO.FindById findByAndQnaId(@Param("andId") int andId, @Param("andQnaId") int andQnaId);
    void save(DynamicAndQnaDTO.Update andQnaInsertDTO);
    void update(DynamicAndQnaDTO.Update andQnaUpdateDTO);
    void deleteByAndQnaId(@Param("andId") int andId, @Param("andQnaId") int andQnaId);
    void deleteAll(@Param("andId") int andId);

    void createAndQnaTable ();
    void dropAndQnaTable();
    void insertTestData();

    int countNotDeleted(int andId);

}
