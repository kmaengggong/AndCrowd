package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaReplyRepository;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicCrowdQnaServiceImpl implements DynamicCrowdQnaService {

    DynamicCrowdQnaRepository dynamicCrowdQnaRepository;
    DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository;

    @Autowired
    public DynamicCrowdQnaServiceImpl(DynamicCrowdQnaRepository dynamicCrowdQnaRepository,
                                      DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository){
        this.dynamicCrowdQnaRepository = dynamicCrowdQnaRepository;
        this.dynamicCrowdQnaReplyRepository = dynamicCrowdQnaReplyRepository;
    }


    @Override
    public void createDynamicCrowdQnaTable(int crowdId) {
        dynamicCrowdQnaRepository.createDynamicCrowdQnaTable(crowdId);
    }

    @Override
    public List<DynamicCrowdQnaDTO.Find> findAll(int crowdId) {
        return dynamicCrowdQnaRepository.findAll(crowdId);
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
    public void save(DynamicCrowdQnaDTO.Save dynamicCrowdQnaDTOSave) {
        dynamicCrowdQnaRepository.save(dynamicCrowdQnaDTOSave);
    }

    @Override
    public void update(DynamicCrowdQnaDTO.Update dynamicCrowdBoardDTOUpdate) {
        dynamicCrowdQnaRepository.update(dynamicCrowdBoardDTOUpdate);
    }
}
