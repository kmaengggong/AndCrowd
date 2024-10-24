package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicAndQnaService {
    List<DynamicAndQnaDTO.FindById> findAll(int andId);
    List<DynamicAndQnaDTO.FindById> findAllNotDeleted(@Param("offset") int offset, @Param("limit") int limit,  int andId);
    DynamicAndQnaDTO.FindById findByAndQnaId(int andId, int andQnaId);
    void save(DynamicAndQnaDTO.Update andQnaInsertDTO);
    void update(DynamicAndQnaDTO.Update andQnaUpdateDTO);
    void deleteByAndQnaId(int andId, int andQnaId);
    void deleteAll(@Param("andId") int andId);

    int countAll(int andId);
}
