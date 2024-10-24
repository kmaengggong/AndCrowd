package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicCrowdRewardRepository {

    List<DynamicCrowdRewardDTO.FindAllById> findAll(int crowdId);
    List<DynamicCrowdRewardDTO.FindAllById> findAllNotDeleted(@Param("crowdId") int crowdId);
    DynamicCrowdRewardDTO.FindAllById findByRewardId(@Param("crowdId") int crowdId, @Param("rewardId") int rewardId);

    void save(DynamicCrowdRewardDTO.Update dynamicRewardInsertDTO);
    void update(DynamicCrowdRewardDTO.Update dynamicRewardUpdateDTO);
    void deleteByCrowdRewardId(@Param("crowdId") int crowdId, @Param("rewardId") int rewardId);
    void deleteAll(@Param("crowdId") int crowdId);

    void createDynamicCrowdRewardTable(int crowdId);
    void testCreateDynamicCrowdRewardTable();
    void dropCrowdRewardTable();
    void insertTestData();
    void updateRewardLeft(DynamicCrowdRewardDTO.UpdateRewardLeft updateRewardLeft);

}
