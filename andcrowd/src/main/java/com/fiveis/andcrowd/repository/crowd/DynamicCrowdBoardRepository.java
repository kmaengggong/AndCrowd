package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicCrowdBoardRepository {

    List<DynamicCrowdBoardDTO.Find> findAll(int crowdId);
    List<DynamicCrowdBoardDTO.Find> findAllByIsDeletedFalse(@Param("offset") int offset, @Param("limit") int limit, @Param("crowdId") int crowdId);
    DynamicCrowdBoardDTO.Find findById(@Param("crowdId") int crowdId, @Param("crowdBoardId") int crowdBoardId);
    void save(DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOSave);
    void update(DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOUpdate);

    void deleteByCrowdId(@Param("crowdId") int crowdId);
    void deleteByCrowdBoardId(@Param("crowdId") int crowdId, @Param("crowdBoardId") int crowdBoardId);
    void createDynamicCrowdBoardTable(int crowdId);

    int countNotDeleted(int crowdId);

}
