package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaReplyDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicCrowdQnaReplyService {
//    void createDynamicCrowdQnaReplyTable(@Param("crowdId") int crowdId);
    List<DynamicCrowdQnaReplyDTO.Find> findAll(int crowdId);
    List<DynamicCrowdQnaReplyDTO.Find> findAllNotDeleted(int crowdId);
    List<DynamicCrowdQnaReplyDTO.Find> findAllByCrowdQnaId(int crowdId, int crowdQnaId);
    DynamicCrowdQnaReplyDTO.Find findById(int crowdId, int qnaReplyId);
    void save(DynamicCrowdQnaReplyDTO.Update dynamicCrowdQnaReplyDTOSave);
    void update(DynamicCrowdQnaReplyDTO.Update dynamicCrowdBoardReplyDTOUpdate);
    void deleteByQnaReplyId(int crowdId, int qnaReplyId);
    void deleteAllByQnaId(int crowdId, int crowdQnaId);
    void deletedAll(int crowdId);
}
