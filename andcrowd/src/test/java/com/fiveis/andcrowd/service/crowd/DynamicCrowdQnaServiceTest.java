package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
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
public class DynamicCrowdQnaServiceTest {

    @Autowired
    DynamicCrowdQnaService dynamicCrowdQnaService;

    @Autowired
    DynamicCrowdQnaReplyRepository dynamicCrowdQnaReplyRepository;

    // createTable의 경우 @Transactional을 사용하여도 생성된 테이블이 롤백되지 않아 정상작동 확인후 주석처리
//    @Test
//    @Transactional
//    @DisplayName("2번 crowd게시글 생성시 crowd_qna_2 이라는 이름의 테이블이 생성되며, 글이 정상적으로 추가된다.")
//    public void createDynamicCrowdQnaTableTest(){
//        // given
//        int crowdId = 2;
//        String title = "추가된 제목";
//        String content = "추가된 본문";
//
//        // when
//        dynamicCrowdQnaService.createDynamicCrowdQnaTable(crowdId);
//        DynamicCrowdQnaDTO.Save newQna = new DynamicCrowdQnaDTO.Save();
//        newQna.setCrowdId(crowdId);
//        newQna.setQnaTitle(title);
//        newQna.setQnaContent(content);
//        dynamicCrowdQnaService.save(newQna);
//
//        // then
//        List<DynamicCrowdQnaDTO.Find> crowdBoardList = dynamicCrowdQnaService.findAll(crowdId);
//        assertThat(crowdBoardList.get(0).getCrowdId()).isEqualTo(crowdId);
//    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 전체 crowd_Qna 조회시 데이터가 3개일 것이다.")
    public void findAllTest(){

        // given
        int crowdId = 1;

        // when
        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaService.findAll(crowdId);

        // then
        assertThat(crowdQnaList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 전체 crowd_Qna 조회시 데이터가 2개일 것이다.")
    public void findAllByIsDeletedFalseTest(){

        // given
        int crowdId = 1;

        // when
        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaService.findAllByIsDeletedFalse(crowdId);

        // then
        assertThat(crowdQnaList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 2번째 글의 제목은 '2번글제목', 본문은 '2번글본문' 일것이다.")
    public void findByIdTest(){

        // given
        int crowdId = 1;
        int crowdQnaId = 2;
        String title = "2번글제목";
        String content = "2번글본문";

        // when
        DynamicCrowdQnaDTO.Find crowdQna = dynamicCrowdQnaService.findById(crowdId, crowdQnaId);

        // then
        assertThat(crowdQna.getCrowdId()).isEqualTo(crowdId);
        assertThat(crowdQna.getCrowdQnaId()).isEqualTo(crowdQnaId);
        assertThat(crowdQna.getQnaTitle()).isEqualTo(title);
        assertThat(crowdQna.getQnaContent()).isEqualTo(content);

    }

    @Test
    @Transactional
    @DisplayName("추가된 글의 데이터는 전체 데이터의 첫번째 인덱스의 각요소들은 입력한 데이터와 일치할 것이다.")
    public void saveTest(){
        // given
        int crowdId = 1;
        String title = "추가된 제목";
        String content = "추가된 본문";
        int crowdQnaId = 4;


        // when
        // 빌더로 리팩토링 예정
        DynamicCrowdQnaDTO.Save newQna = new DynamicCrowdQnaDTO.Save();
        newQna.setCrowdId(crowdId);
        newQna.setQnaTitle(title);
        newQna.setQnaContent(content);
        dynamicCrowdQnaService.save(newQna);

        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaService.findAll(crowdId);

        DynamicCrowdQnaDTO.Find crowdQna = crowdQnaList.get(0);

        // then
        assertThat(title).isEqualTo(crowdQna.getQnaTitle());
        assertThat(content).isEqualTo(crowdQna.getQnaContent());
    }

    @Test
    @Transactional
    @DisplayName("2번글에 수정된 내용을 입력시 입력된 내용으로 수정된다.")
    public void updateTest(){

        // given
        int crowdId = 1;
        int crowdQnaId = 2;
        String title = "수정된 제목";
        String content = "수정된 본문";


        // when
        // 빌더로 리팩토링 예정
        DynamicCrowdQnaDTO.Update updateQna = new DynamicCrowdQnaDTO.Update();
        updateQna.setCrowdId(crowdId);
        updateQna.setQnaTitle(title);
        updateQna.setQnaContent(content);
        updateQna.setCrowdQnaId(crowdQnaId);
        dynamicCrowdQnaService.update(updateQna);

        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaService.findAll(crowdId);

        DynamicCrowdQnaDTO.Find crowdQna = crowdQnaList.get(1);

        // then
        assertThat(title).isEqualTo(crowdQna.getQnaTitle());
        assertThat(content).isEqualTo(crowdQna.getQnaContent());
        assertThat(crowdId).isEqualTo(crowdQna.getCrowdId());
        assertThat(crowdQna.getUpdatedAt()).isAfter(crowdQna.getPublishedAt());
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 1번째 Qna 삭제시 연관된 reply의 is_deleted가 전부 true가 될것이며, 2번째글 또한 is_deleted가 true 가 될것이다.")
    public void deleteByCrowdBoardIdTest(){

        // given
        int crowdId = 1;
        int crowdQnaId = 1;

        // when
        dynamicCrowdQnaService.deleteByCrowdQnaId(crowdId, crowdQnaId);
        List<DynamicCrowdQnaReplyDTO.Find> replies = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId);
        DynamicCrowdQnaDTO.Find crowdQna = dynamicCrowdQnaService.findById(crowdId, crowdQnaId);

        // then
        assertThat(replies.get(0).isDeleted()).isTrue();
        assertThat(replies.get(1).isDeleted()).isTrue();
        assertThat(replies.get(2).isDeleted()).isTrue();
        assertThat(crowdQna.isDeleted()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("crowd 1번글이 삭제될경우 관련된 crowdQna, crowdQnaReply의 is_deleted가 전부 true가 된다")
    public void deleteAllTest(){
        // given
        int crowdId = 1;
        int crowdQnaId1 = 1;
        int crowdQnaId2 = 2;
        int crowdQnaId3 = 3;

        // when
        dynamicCrowdQnaService.deleteAllByCrowdId(crowdId);
        List<DynamicCrowdQnaDTO.Find> qnaList = dynamicCrowdQnaService.findAll(crowdId);
        List<DynamicCrowdQnaReplyDTO.Find> replies = dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId1);
        replies.addAll(dynamicCrowdQnaReplyRepository.findAll(crowdId,crowdQnaId2));
        replies.addAll(dynamicCrowdQnaReplyRepository.findAll(crowdId, crowdQnaId3));

        // then
        int qnaListSize = qnaList.size();
        for(int indexNum = 0; indexNum < qnaListSize; indexNum++){
            assertThat(qnaList.get(indexNum).isDeleted()).isTrue();
        }

        int repliesSize = replies.size();
        for(int indexNum = 0; indexNum < repliesSize; indexNum++){
            assertThat(replies.get(indexNum).isDeleted()).isTrue();
        }
    }
}
