package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaReplyRepository;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicCrowdQnaServiceImpl implements DynamicCrowdQnaService {

    private final DynamicCrowdQnaRepository dynamicCrowdQnaRepository;
    private final DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository;

    @Autowired
    public DynamicCrowdQnaServiceImpl(DynamicCrowdQnaRepository dynamicCrowdQnaRepository,
                                      DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository){
        this.dynamicCrowdQnaRepository = dynamicCrowdQnaRepository;
        this.dynamicCrowdQnaReplyRepository = dynamicCrowdQnaReplyRepository;
    }

//    @Override
//    public void createDynamicCrowdQnaTable(int crowdId) {
//        dynamicCrowdQnaRepository.createDynamicCrowdQnaTable(crowdId);
//    }

    @Override
    public List<DynamicCrowdQnaDTO.Find> findAll(int crowdId) {
        return dynamicCrowdQnaRepository.findAll(crowdId);
    }

    @Override
    public List<DynamicCrowdQnaDTO.Find> findAllByIsDeletedFalse(@Param("offset") int offset, @Param("limit") int limit, int crowdId){
        return dynamicCrowdQnaRepository.findAllByIsDeletedFalse(offset, limit, crowdId);
    }

    @Override
    public DynamicCrowdQnaDTO.Find findById(int crowdId, int crowdQnaId) {
        return dynamicCrowdQnaRepository.findById(crowdId, crowdQnaId);
    }

    // crowdQna 삭제전 연관된 Reply 삭제 로직 추가
    @Override
    public void deleteByCrowdQnaId(int crowdId, int crowdQnaId) {
        dynamicCrowdQnaReplyRepository.deleteAllByQnaId(crowdId, crowdQnaId);
        dynamicCrowdQnaRepository.deleteByCrowdQnaId(crowdId, crowdQnaId);
    }

    @Override
    public void save(DynamicCrowdQnaDTO.Update dynamicCrowdQnaDTOSave) {
        dynamicCrowdQnaDTOSave.setPublishedAt(LocalDateTime.now());
        dynamicCrowdQnaDTOSave.setUpdatedAt(LocalDateTime.now());
        dynamicCrowdQnaRepository.save(dynamicCrowdQnaDTOSave);
    }

    @Override
    public void update(DynamicCrowdQnaDTO.Update dynamicCrowdBoardDTOUpdate) {
        dynamicCrowdBoardDTOUpdate.setUpdatedAt(LocalDateTime.now());
        dynamicCrowdQnaRepository.update(dynamicCrowdBoardDTOUpdate);
    }

    // crowd글이 삭제될경우 연관된 Qna와 QnaReply가 전부 삭제되는 기능
    @Override
    public void deleteAllByCrowdId(int crowdId){
        dynamicCrowdQnaReplyRepository.deleteAll(crowdId);
        dynamicCrowdQnaRepository.deleteAll(crowdId);
    }

    @Override
    public int countAll(int crowdId) {
        return dynamicCrowdQnaRepository.countNotDeleted(crowdId);
    }
}
