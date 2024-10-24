package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserFollowDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserFollow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicUserFollowRepository {
    void createDynamicUserFollowTable(String userEmail);
    List<DynamicUserFollowDTO.Find> findAll(String userEmail);
    DynamicUserFollowDTO.Find findById(@Param("userEmail") String userEmail, @Param("uFollowId") int uFollowId);
    DynamicUserFollowDTO.Find findByUserId(@Param("userEmail") String userEmail, @Param("userId") int userId);
    void save(@Param("userEmail") String userEmail, @Param("dynamicUserFollow") DynamicUserFollow dynamicUserFollow);
    void deleteById(@Param("userEmail") String userEmail, @Param("uFollowId") int uFollowId);
    void deleteByUserId(@Param("userEmail") String userEmail, @Param("userId") int userId);
    void deleteTableByUserEmail(@Param("userEmail") String userEmail);
}
