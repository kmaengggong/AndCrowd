package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserAndDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicUserAndRepository {
    void createDynamicUserAndTable(String userEmail);
    List<DynamicUserAndDTO.Find> findAll(String userEmail);
    DynamicUserAndDTO.Find findById(@Param("userEmail") String userEmail, @Param("uAndId") int uAndId);
    DynamicUserAndDTO.Find findByAndId(@Param("userEmail") String userEmail, @Param("andId") int andId);
    void save(@Param("userEmail") String userEmail, @Param("dynamicUserAnd") DynamicUserAnd dynamicUserAnd);
    void deleteById(@Param("userEmail") String userEmail, @Param("uAndId") int uAndId);
    void deleteByAndId(@Param("userEmail") String userEmail, @Param("andId") int andId);
    void deleteTableByUserEmail(@Param("userEmail") String userEmail);
}
