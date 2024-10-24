package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserLikeDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicUserLikeRepository {
    void createDynamicUserLikeTable(String userEmail);
    List<DynamicUserLikeDTO.Find> findAll(String userEmail);
    DynamicUserLikeDTO.Find findById(@Param("userEmail") String userEmail, @Param("uLikeId") int uLikeId);
    DynamicUserLikeDTO.Find findByProject(@Param("userEmail") String userEmail,
                                            @Param("projectId") int projectId,
                                            @Param("projectType") int projectType);
    void save(@Param("userEmail") String userEmail, @Param("dynamicUserLike") DynamicUserLike dynamicUserLike);
    void deleteById(@Param("userEmail") String userEmail, @Param("uLikeId") int uLikeId);

    void deleteByProjectId(@Param("userEmail") String userEmail,
                           @Param("projectId") int projectId,
                           @Param("projectType") int projectType);
    void deleteTableByUserEmail(@Param("userEmail") String userEmail);
}
