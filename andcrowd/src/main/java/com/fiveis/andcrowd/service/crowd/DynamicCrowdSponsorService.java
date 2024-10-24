package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdSponsorDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicCrowdSponsorService {

    void createDynamicCrowdSponsorTable();
    List<DynamicCrowdSponsorDTO.FindById> findAll(int crowdId);
    List<DynamicCrowdSponsorDTO.FindById> findAllNotDeleted(int crowdId);
    DynamicCrowdSponsorDTO.FindById findBySponsorId(int crowdId, int sponsorId);
//    List<DynamicCrowdSponsorDTO.FindById> findByUserId(@Param("crowd_id") int crowdId, @Param("user_id") int userId);

    void save(DynamicCrowdSponsorDTO.Update crowdSponsorInsertDTO);
    void update(DynamicCrowdSponsorDTO.Update crowdSponsorUpdateDTO);
    void deleteByCrowdSponsorId(@Param("crowdId") int crowdId, @Param("sponsorId") int sponsorId);
}