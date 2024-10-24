package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaReplyDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicCrowdQnaReplyServiceImpl implements DynamicCrowdQnaReplyService {

    private final DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository;

    @Autowired
    public DynamicCrowdQnaReplyServiceImpl(DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository){
        this.dynamicCrowdQnaReplyRepository = dynamicCrowdQnaReplyRepository;
    }

//    @Override
//    public void createDynamicCrowdQnaReplyTable(int crowdId) {
//        dynamicCrowdQnaReplyRepository.createDynamicCrowdQnaReplyTable(crowdId);
//    }

    @Override
    public List<DynamicCrowdQnaReplyDTO.Find> findAll(int crowdId) {
        return dynamicCrowdQnaReplyRepository.findAll(crowdId);
    }

    @Override
    public List<DynamicCrowdQnaReplyDTO.Find> findAllNotDeleted(int crowdId){
        return dynamicCrowdQnaReplyRepository.findAllNotDeleted(crowdId);
    }

    @Override
    public List<DynamicCrowdQnaReplyDTO.Find> findAllByCrowdQnaId(int crowdId, int crowdQnaId) {
        return dynamicCrowdQnaReplyRepository.findAllByCrowdQnaId(crowdId, crowdQnaId);
    }

    @Override
    public DynamicCrowdQnaReplyDTO.Find findById(int crowdId, int qnaReplyId) {
        return dynamicCrowdQnaReplyRepository.findById(crowdId, qnaReplyId);
    }

    @Override
    public void deleteByQnaReplyId(int crowdId, int qnaReplyId) {
        dynamicCrowdQnaReplyRepository.deleteByQnaReplyId(crowdId, qnaReplyId);
    }

    @Override
    public void deleteAllByQnaId(int crowdId, int crowdQnaId) {
        dynamicCrowdQnaReplyRepository.deleteAllByQnaId(crowdId, crowdQnaId);
    }

    @Override
    public void deletedAll(int crowdId) {
        dynamicCrowdQnaReplyRepository.deleteAll(crowdId);
    }

    @Override
    public void save(DynamicCrowdQnaReplyDTO.Update dynamicCrowdQnaReplyDTOSave) {
        dynamicCrowdQnaReplyDTOSave.setPublishedAt(LocalDateTime.now());
        dynamicCrowdQnaReplyDTOSave.setUpdatedAt(LocalDateTime.now());
        dynamicCrowdQnaReplyRepository.save(dynamicCrowdQnaReplyDTOSave);
    }

    @Override
    public void update(DynamicCrowdQnaReplyDTO.Update dynamicCrowdBoardReplyDTOUpdate) {
        dynamicCrowdBoardReplyDTOUpdate.setUpdatedAt(LocalDateTime.now());
        dynamicCrowdQnaReplyRepository.update(dynamicCrowdBoardReplyDTOUpdate);
    }


}
