package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaReplyDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaReplyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DynamicCrowdQnaReplyRepositoryTest {

    @Autowired
    DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository;

    // createTable의 경우 @Transactional을 사용하여도 생성된 테이블이 롤백되지 않아 정상작동 확인후 주석처리
//    @Test
//    @Transactional
//    @DisplayName("2번 crowd게시글 생성시 crowd_qna_reply_2 이라는 이름의 테이블이 생성되며, 글이 정상적으로 추가된다.")
//    public void createDynamicCrowdQnaReplyTableTest(){
//        // given
//        int crowdId = 1;
//        int crowdQnaId = 1;
//        String content = "추가된 댓글";
//
//        // when
//        dynamicCrowdQnaReplyRepository.createDynamicCrowdQnaReplyTable(crowdId);
//        DynamicCrowdQnaReplyDTO.Save newQnaReply = new DynamicCrowdQnaReplyDTO.Save();
//        newQnaReply.setCrowdQnaId(crowdQnaId);
//        newQnaReply.setQnaReplyContent(content);
//        newQnaReply.setCrowdId(crowdId);
//        dynamicCrowdQnaReplyRepository.save(newQnaReply);
//
//        // then
//        List<DynamicCrowdQnaReplyDTO.Find> crowdBoardList = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);
//        assertThat(crowdBoardList.get(0).getCrowdId()).isEqualTo(crowdId);
//    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 1번 qna의 reply 조회시 데이터가 3개일 것이다.")
    public void findAllTest(){
        // given
        int crowdId = 1;
        int crowdQnaId = 1;

        // when
        List<DynamicCrowdQnaReplyDTO.Find> crowdQnaReplyList = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);

        // then
        assertThat(crowdQnaReplyList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 1번 qna의 reply 조회시 데이터가 2개일 것이다.")
    public void findAllByIsDeletedFalseTest(){
        // given
        int crowdId = 1;
        int crowdQnaId = 1;

        // when
        List<DynamicCrowdQnaReplyDTO.Find> crowdQnaReplyList = dynamicCrowdQnaReplyRepository.findAllByIsDeletedFalse(crowdId, crowdQnaId);

        // then
        assertThat(crowdQnaReplyList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 crowd_qna_reply 2번째 댓글의 내용은'2번댓글' 일것이다.")
    public void findByIdTest(){

        // given
        int crowdId = 1;
        int qnaReplyId = 2;
        String content = "2번댓글";

        // when
        DynamicCrowdQnaReplyDTO.Find qnaReply = dynamicCrowdQnaReplyRepository.findById(crowdId, qnaReplyId);

        // then
        assertThat(qnaReply.getQnaReplyId()).isEqualTo(qnaReplyId);
        assertThat(qnaReply.getQnaReplyContent()).isEqualTo(content);

    }

    @Test
    @Transactional
    @DisplayName("추가된 댓글의 qnaReplyId는 6번일것이며, content가 입력된 값과 일치할 것이다.")
    public void saveTest(){
        // given
        int crowdId = 1;
        int crowdQnaId = 1;
        String content = "추가된댓글";


        // when
        // 빌더로 리팩토링 예정
        DynamicCrowdQnaReplyDTO.Save newQnaReply = new DynamicCrowdQnaReplyDTO.Save();
        newQnaReply.setCrowdQnaId(crowdQnaId);
        newQnaReply.setQnaReplyContent(content);
        newQnaReply.setCrowdId(crowdId);
        dynamicCrowdQnaReplyRepository.save(newQnaReply);

        List<DynamicCrowdQnaReplyDTO.Find> crowdQnaReplyList = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);

        DynamicCrowdQnaReplyDTO.Find qnaReply = crowdQnaReplyList.get(3);

        // then
        assertThat(content).isEqualTo(qnaReply.getQnaReplyContent());
        assertThat(crowdId).isEqualTo(qnaReply.getCrowdQnaId());
    }

    @Test
    @Transactional
    @DisplayName("2번댓글에 수정된 내용을 입력시 입력된 내용으로 수정된다.")
    public void updateTest(){

        // given
        int crowdId = 1;
        int crowdQnaId = 1;
        int qnaReplyId = 2;
        String content = "수정된본문";


        // when
        // 빌더로 리팩토링 예정
        DynamicCrowdQnaReplyDTO.Update updateQnaReply = new DynamicCrowdQnaReplyDTO.Update();
        updateQnaReply.setCrowdId(crowdId);
        updateQnaReply.setQnaReplyContent(content);
        updateQnaReply.setQnaReplyId(qnaReplyId);
        dynamicCrowdQnaReplyRepository.update(updateQnaReply);

        List<DynamicCrowdQnaReplyDTO.Find> crowdQnaReplyList = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);

        DynamicCrowdQnaReplyDTO.Find crowdQnaReply = crowdQnaReplyList.get(1);

        // then
        assertThat(content).isEqualTo(crowdQnaReply.getQnaReplyContent());
        assertThat(crowdQnaId).isEqualTo(crowdQnaReply.getCrowdQnaId());
    }

    @Test
    @Transactional
    @DisplayName("2번댓글 삭제시 crowdQna 1번글의 2번 댓글의 isDeleted 는 true일것이다.")
    public void deleteByCrowdBoardIdTest(){

        // given
        int crowdId = 1;
        int qnaReplyId = 2;

        // when
        dynamicCrowdQnaReplyRepository.deleteByQnaReplyId(crowdId, qnaReplyId);
        DynamicCrowdQnaReplyDTO.Find qnaReply = dynamicCrowdQnaReplyRepository.findById(crowdId, qnaReplyId);

        // then
        assertThat(qnaReply.isDeleted()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("crowdQnaId 1번글의 reply의 is_deleted 가 전부 true가 된다")
    public void deleteAllByQnaIdTest(){
        // given
        int crowdId = 1;
        int crowdQnaId = 1;

        // when
        dynamicCrowdQnaReplyRepository.deleteAllByQnaId(crowdId, crowdQnaId);
        List<DynamicCrowdQnaReplyDTO.Find> replies = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);

        // then
        assertThat(replies.get(0).isDeleted()).isTrue();
        assertThat(replies.get(1).isDeleted()).isTrue();
        assertThat(replies.get(2).isDeleted()).isTrue();

    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글의 reply의 is_deleted 가 전부 true가 된다")
    public void deleteAllTest(){
        // given
        int crowdId = 1;
        int crowdQnaId1 = 1;
        int crowdQnaId2 = 2;
        int crowdQnaId3 = 3;

        // when
        dynamicCrowdQnaReplyRepository.deleteAll(crowdId);
        List<DynamicCrowdQnaReplyDTO.Find> replies1 = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId1);
        List<DynamicCrowdQnaReplyDTO.Find> replies2 = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId2);
        List<DynamicCrowdQnaReplyDTO.Find> replies3 = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId3);

        // then
        assertThat(replies1.get(0).isDeleted()).isTrue();
        assertThat(replies1.get(1).isDeleted()).isTrue();
        assertThat(replies1.get(2).isDeleted()).isTrue();
        assertThat(replies2.get(0).isDeleted()).isTrue();
        assertThat(replies3.get(0).isDeleted()).isTrue();

    }
}
