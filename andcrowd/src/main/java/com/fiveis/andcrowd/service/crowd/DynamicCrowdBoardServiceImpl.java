package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdBoardDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdBoardRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicCrowdBoardServiceImpl implements DynamicCrowdBoardService{

    private final DynamicCrowdBoardRepository dynamicCrowdBoardRepository;

    @Autowired
    public DynamicCrowdBoardServiceImpl(DynamicCrowdBoardRepository dynamicCrowdBoardRepository){
        this.dynamicCrowdBoardRepository = dynamicCrowdBoardRepository;
    }
    @Override
    public void createDynamicCrowdBoardTable(int crowdId) {
        dynamicCrowdBoardRepository.createDynamicCrowdBoardTable(crowdId);
    }

    @Override
    public List<DynamicCrowdBoardDTO.Find> findAll(int crowdId) {
        return dynamicCrowdBoardRepository.findAll(crowdId);
    }

    @Override
    public List<DynamicCrowdBoardDTO.Find> findAllByIsDeletedFalse(@Param("offset") int offset, @Param("limit") int limit, int crowdId){
        return dynamicCrowdBoardRepository.findAllByIsDeletedFalse(offset, limit, crowdId);
    }

    @Override
    public DynamicCrowdBoardDTO.Find findById(int crowdId, int crowdBoardId) {
        return dynamicCrowdBoardRepository.findById(crowdId, crowdBoardId);
    }

    @Override
    public void deleteByCrowdBoardId(int crowdId, int crowdBoardId) {
        dynamicCrowdBoardRepository.deleteByCrowdBoardId(crowdId, crowdBoardId);
    }

    @Override
    public void save(DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOSave) {
        dynamicCrowdBoardDTOSave.setPublishedAt(LocalDateTime.now());
        dynamicCrowdBoardDTOSave.setUpdatedAt(LocalDateTime.now());
        dynamicCrowdBoardRepository.save(dynamicCrowdBoardDTOSave);
    }

    @Override
    public void update(DynamicCrowdBoardDTO.Update dynamicCrowdBoardDTOUpdate) {
        dynamicCrowdBoardDTOUpdate.setUpdatedAt(LocalDateTime.now());
        dynamicCrowdBoardRepository.update(dynamicCrowdBoardDTOUpdate);
    }

    @Override
    public void deleteByCrowdId(int crowdId) {
        dynamicCrowdBoardRepository.deleteByCrowdId(crowdId);
    }

    @Override
    public int countAll(int crowdId) {
        return dynamicCrowdBoardRepository.countNotDeleted(crowdId);
    }
}
