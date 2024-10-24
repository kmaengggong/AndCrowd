package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicCrowdQnaService {
//    void createDynamicCrowdQnaTable(@Param("crowdId") int crowdId);
    List<DynamicCrowdQnaDTO.Find> findAll(int crowdId);
    List<DynamicCrowdQnaDTO.Find> findAllByIsDeletedFalse(@Param("offset") int offset, @Param("limit") int limit, int crowdId);
    DynamicCrowdQnaDTO.Find findById(int crowdId, int crowdQnaId);
    void save(DynamicCrowdQnaDTO.Update dynamicCrowdQnaDTOSave);
    void update(DynamicCrowdQnaDTO.Update dynamicCrowdBoardDTOUpdate);
    void deleteByCrowdQnaId(int crowdId, int crowdQnaId);
    void deleteAllByCrowdId( int crowdId);

    int countAll(int crowdId);

}
