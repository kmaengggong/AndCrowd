package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserMakerDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserMaker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicUserMakerRepository {
    void createDynamicUserMakerTable(String userEmail);
    List<DynamicUserMakerDTO.Find> findAll(String userEmail);
    DynamicUserMakerDTO.Find findById(@Param("userEmail") String userEmail, @Param("uMakerId") int uMakerId);
    DynamicUserMakerDTO.Find findByProject(@Param("userEmail") String userEmail,
                                           @Param("projectId") int projectId,
                                           @Param("projectType") int projectType);

    void save(@Param("userEmail") String userEmail, @Param("dynamicUserMaker") DynamicUserMaker dynamicUserMaker);
    void deleteById(@Param("userEmail") String userEmail, @Param("uMakerId") int uMakerId);
    void deleteByProjectId(@Param("userEmail") String userEmail,
                           @Param("projectId") int projectId,
                           @Param("projectType") int projectType);
    void deleteTableByUserEmail(@Param("userEmail") String userEmail);
}
