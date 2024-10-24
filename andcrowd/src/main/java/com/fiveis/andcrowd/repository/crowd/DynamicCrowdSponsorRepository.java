package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdSponsorDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicCrowdSponsorRepository {

    List<DynamicCrowdSponsorDTO.FindById> findAll(int crowdId);
//    List<DynamicCrowdSponsorDTO.FindById> findAllNotDeleted(@Param("crowd_id") int crowdId);
    DynamicCrowdSponsorDTO.FindById findBySponsorId(@Param("crowdId") int crowdId, @Param("sponsorId") int sponsorId);

    void save(DynamicCrowdSponsorDTO.Update dynamicSponsorInsertDTO);
    void update(DynamicCrowdSponsorDTO.Update dynamicSponsorUpdateDTO);
    void deleteByCrowdSponsorId(@Param("crowdId") int crowdId, @Param("sponsorId") int sponsorId);

    void createDynamicCrowdSponsorTable();
    void testCreateDynamicCrowdSponsorTable();
    void dropCrowdSponsorTable();
    void insertTestData();
}
