package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicCrowdRewardService {

    void createDynamicCrowdRewardTable(int crowdId);
    List<DynamicCrowdRewardDTO.FindAllById> findAll(int crowdId);
    List<DynamicCrowdRewardDTO.FindAllById> findAllNotDeleted(int crowdId);
    DynamicCrowdRewardDTO.FindAllById findByRewardId(@Param("crowdId") int crowdId, @Param("rewardId") int rewardId);
//    List<DynamicCrowdRewardDTO.FindAllById> findByUserId(@Param("crowd_id") int crowdId, @Param("user_id") int userId);

    void save(DynamicCrowdRewardDTO.Update crowdRewardInsertDTO);
    void update(DynamicCrowdRewardDTO.Update crowdRewardUpdateDTO);
    void deleteByCrowdRewardId(@Param("crowdId") int crowdId, @Param("rewardId") int rewardId);
    void deleteAll(int crowdId);
}