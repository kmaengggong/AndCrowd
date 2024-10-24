package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdBoardDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicCrowdBoardService {
    void createDynamicCrowdBoardTable(int crowdId);

    List<DynamicCrowdBoardDTO.Find> findAll(int crowdId);

    List<DynamicCrowdBoardDTO.Find> findAllByIsDeletedFalse(@Param("offset") int offset, @Param("limit") int limit, int crowdId);

    DynamicCrowdBoardDTO.Find findById(int crowdId, int crowdBoardId);
    void deleteByCrowdBoardId(int crowdId, int crowdBoardId);

    void save(DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOSave);

    void update(DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOUpdate);

    void deleteByCrowdId(int crowdId);

    int countAll(int crowdId);
}
