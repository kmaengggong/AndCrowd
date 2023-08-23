package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdSponsorDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdSponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicCrowdSponsorServiceImpl implements DynamicCrowdSponsorService {

    private final DynamicCrowdSponsorRepository dynamicCrowdSponsorRepository;

    @Autowired
    public DynamicCrowdSponsorServiceImpl(DynamicCrowdSponsorRepository dynamicCrowdSponsorRepository){
        this.dynamicCrowdSponsorRepository = dynamicCrowdSponsorRepository;
    }

    @Override
    public void createDynamicCrowdSponsorTable() {
        dynamicCrowdSponsorRepository.createDynamicCrowdSponsorTable();
    }

    @Override
    public List<DynamicCrowdSponsorDTO.FindById> findAll(int crowdId) {
        return dynamicCrowdSponsorRepository.findAll(crowdId);
    }

    @Override
    public List<DynamicCrowdSponsorDTO.FindById> findAllNotDeleted(int crowdId) {
        return dynamicCrowdSponsorRepository.findAll(crowdId);
    }

    @Override
    public DynamicCrowdSponsorDTO.FindById findBySponsorId(int crowdId, int sponsorId) {
        return dynamicCrowdSponsorRepository.findBySponsorId(crowdId, sponsorId);
    }

    @Override
    public void save(DynamicCrowdSponsorDTO.Update crowdSponsorInsertDTO) {
        dynamicCrowdSponsorRepository.save(crowdSponsorInsertDTO);
    }

    @Override
    public void update(DynamicCrowdSponsorDTO.Update crowdSponsorUpdateDTO) {
        dynamicCrowdSponsorRepository.update(crowdSponsorUpdateDTO);
    }

    @Override
    public void deleteByCrowdSponsorId(int crowdId, int sponsorId) {
        dynamicCrowdSponsorRepository.deleteBySponsorId(crowdId, sponsorId);
    }
}