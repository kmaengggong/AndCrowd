package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaReplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface DynamicCrowdQnaReplyRepository {
    void createDynamicCrowdQnaReplyTable(int crowdId);

//    List<DynamicCrowdQnaReplyDTO.Find> findAll(@Param("crowdId") int crowdId, @Param("crowdQnaId") int crowdQnaId);
    List<DynamicCrowdQnaReplyDTO.Find> findAll(int crowdId);
//    List<DynamicCrowdQnaReplyDTO.Find> findAllByIsDeletedFalse(@Param("crowdId") int crowdId, @Param("crowdQnaId") int crowdQnaId);
    List<DynamicCrowdQnaReplyDTO.Find> findAllNotDeleted(int crowdId);

    List<DynamicCrowdQnaReplyDTO.Find> findAllByCrowdQnaId(@Param("crowdId")int crowdId, @Param("crowdQnaId")int crowdQnaId);

    DynamicCrowdQnaReplyDTO.Find findById(@Param("crowdId") int crowdId, @Param("qnaReplyId") int qnaReplyId);

    void save(DynamicCrowdQnaReplyDTO.Update dynamicCrowdQnaReplyDTOSave);

    void update(DynamicCrowdQnaReplyDTO.Update dynamicCrowdBoardReplyDTOUpdate);

    void deleteByQnaReplyId(@Param("crowdId") int crowdId, @Param("qnaReplyId") int qnaReplyId);

    // qna글 삭제시 연관된 reply의 is_delete를 전부 true로 바꾸는 기능
    void deleteAllByQnaId(@Param("crowdId") int crowdId, @Param("crowdQnaId") int crowdQnaId);

    // crowd글 삭제시 연관된 reply의 is_deleted를 전부 true로 바꾸는 기능
    void deleteAll(@Param("crowdId") int crowdId);
}
