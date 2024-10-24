package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdRewardDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicCrowdRewardServiceImpl implements DynamicCrowdRewardService {

    private final DynamicCrowdRewardRepository dynamicCrowdRewardRepository;

    @Autowired
    public DynamicCrowdRewardServiceImpl(DynamicCrowdRewardRepository dynamicCrowdRewardRepository){
        this.dynamicCrowdRewardRepository = dynamicCrowdRewardRepository;
    }

    @Override
    public void createDynamicCrowdRewardTable(int crowdId) {
        dynamicCrowdRewardRepository.createDynamicCrowdRewardTable(crowdId);
    }

    @Override
    public List<DynamicCrowdRewardDTO.FindAllById> findAll(int crowdId) {
        return dynamicCrowdRewardRepository.findAll(crowdId);
    }

    @Override
    public List<DynamicCrowdRewardDTO.FindAllById> findAllNotDeleted(int crowdId) {
        return dynamicCrowdRewardRepository.findAllNotDeleted(crowdId);
    }

    @Override
    public DynamicCrowdRewardDTO.FindAllById findByRewardId(int crowdId, int rewardId) {
        return dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);
    }

    @Override
    public void save(DynamicCrowdRewardDTO.Update crowdRewardInsertDTO) {
        dynamicCrowdRewardRepository.save(crowdRewardInsertDTO);
    }

    @Override
    public void update(DynamicCrowdRewardDTO.Update crowdRewardUpdateDTO) {
        dynamicCrowdRewardRepository.update(crowdRewardUpdateDTO);
    }

    @Override
    public void deleteByCrowdRewardId(int crowdId, int rewardId) {
        dynamicCrowdRewardRepository.deleteByCrowdRewardId(crowdId, rewardId);
        DynamicCrowdRewardDTO.FindAllById crowdRewardDTO = dynamicCrowdRewardRepository.findByRewardId(crowdId, rewardId);
        if(crowdRewardDTO != null && !crowdRewardDTO.isDeleted()) {
            DynamicCrowdRewardDTO.Update updateDTO = DynamicCrowdRewardDTO.Update.builder()
                    .rewardId(rewardId)
                    .crowdId(crowdId)
                    .isDeleted(true)
                    .build();

            dynamicCrowdRewardRepository.update(updateDTO);
        }
    }

    @Override
    public void deleteAll(int crowdId) {
        dynamicCrowdRewardRepository.deleteAll(crowdId);
    }
}