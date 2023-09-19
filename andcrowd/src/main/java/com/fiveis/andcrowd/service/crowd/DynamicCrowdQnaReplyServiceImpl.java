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
    public List<DynamicCrowdQnaReplyDTO.Find> findAll(int crowdId, int crowdQnaId) {
        return dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);
    }

    @Override
    public List<DynamicCrowdQnaReplyDTO.Find> findAllByIsDeletedFalse(int crowdId, int crowdQnaId){
        return dynamicCrowdQnaReplyRepository.findAllByIsDeletedFalse(crowdId, crowdQnaId);
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
