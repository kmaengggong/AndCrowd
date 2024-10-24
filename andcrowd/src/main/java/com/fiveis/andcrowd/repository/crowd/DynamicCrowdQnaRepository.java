package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicCrowdQnaRepository {
    void createDynamicCrowdQnaTable(int crowdId);

    List<DynamicCrowdQnaDTO.Find> findAll(int crowdId);

    List<DynamicCrowdQnaDTO.Find> findAllByIsDeletedFalse(@Param("offset") int offset, @Param("limit") int limit, @Param("crowdId") int crowdId);

    DynamicCrowdQnaDTO.Find findById(@Param("crowdId") int crowdId, @Param("crowdQnaId") int crowdQnaId);

    void deleteByCrowdQnaId(@Param("crowdId") int crowdId, @Param("crowdQnaId") int crowdQnaId);

    void save(DynamicCrowdQnaDTO.Update dynamicCrowdQnaDTOSave);

    void update(DynamicCrowdQnaDTO.Update dynamicCrowdBoardDTOUpdate);

    void deleteAll(@Param("crowdId") int crowdId);

    int countNotDeleted(int crowdId);
}
