package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicAndQnaReplyRepository {
    List<DynamicAndQnaReplyDTO.FindById> findAll(int andId);
    List<DynamicAndQnaReplyDTO.FindById> findAllNotDeleted(int andId);
    List<DynamicAndQnaReplyDTO.FindById> findAllByAndQnaId(@Param("and_id") int andId, @Param("andQnaId") int andQnaId);
    DynamicAndQnaReplyDTO.FindById findByAndReplyId(@Param("and_id") int andId, @Param("andReplyId") int andReplyId);
    void save(DynamicAndQnaReplyDTO.Update andReplySaveDTO);
    void update(DynamicAndQnaReplyDTO.Update andReplyUpdateDTO);
    void deleteByAndReplyId(@Param("andId") int andId, @Param("andReplyId") int andReplyId);
    void deleteByAndQnaId(@Param("andId") int andId, @Param("andQnaId") int andQnaId);
    void deleteAll(@Param("andId") int andId);

    void createAndQnaReplyTable ();
    void dropAndQnaReplyTable();
    void insertTestData();

}
