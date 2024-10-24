package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdQnaDTO;
import com.fiveis.andcrowd.repository.crowd.DynamicCrowdQnaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DynamicCrowdQnaRepositoryTest {

    @Autowired
    DynamicCrowdQnaRepository dynamicCrowdQnaRepository;

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
//        dynamicCrowdQnaRepository.createDynamicCrowdQnaTable(crowdId);
//        DynamicCrowdQnaDTO.Save newQna = new DynamicCrowdQnaDTO.Save();
//        newQna.setCrowdId(crowdId);
//        newQna.setQnaTitle(title);
//        newQna.setQnaContent(content);
//        dynamicCrowdQnaRepository.save(newQna);
//
//        // then
//        List<DynamicCrowdQnaDTO.Find> crowdBoardList = dynamicCrowdQnaRepository.findAll(crowdId);
//        assertThat(crowdBoardList.get(0).getCrowdId()).isEqualTo(crowdId);
//    }


    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 전체 crowd_Qna 조회시 데이터가 3개일 것이다.")
    public void findAllTest(){

        // given
        int crowdId = 1;

        // when
        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaRepository.findAll(crowdId);

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
        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaRepository.findAllByIsDeletedFalse(crowdId);

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
        DynamicCrowdQnaDTO.Find crowdQna = dynamicCrowdQnaRepository.findById(crowdId, crowdQnaId);

        // then
        assertThat(crowdQna.getCrowdId()).isEqualTo(crowdId);
        assertThat(crowdQna.getCrowdQnaId()).isEqualTo(crowdQnaId);
        assertThat(crowdQna.getQnaTitle()).isEqualTo(title);
        assertThat(crowdQna.getQnaContent()).isEqualTo(content);

    }

    @Test
    @Transactional
    @DisplayName("추가된 글 데이터는 전체 데이터의 첫번째 인덱스의 각요소들은 입력한 데이터와 일치할 것이다.")
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
        dynamicCrowdQnaRepository.save(newQna);

        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaRepository.findAll(crowdId);

        DynamicCrowdQnaDTO.Find crowdQna = crowdQnaList.get(0);

        // then
        assertThat(title).isEqualTo(crowdQna.getQnaTitle());
        assertThat(content).isEqualTo(crowdQna.getQnaContent());
        assertThat(crowdId).isEqualTo(crowdQna.getCrowdId());
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
        dynamicCrowdQnaRepository.update(updateQna);

        List<DynamicCrowdQnaDTO.Find> crowdQnaList = dynamicCrowdQnaRepository.findAll(crowdId);

        DynamicCrowdQnaDTO.Find crowdQna = crowdQnaList.get(1);

        // then
        assertThat(title).isEqualTo(crowdQna.getQnaTitle());
        assertThat(content).isEqualTo(crowdQna.getQnaContent());
        assertThat(crowdId).isEqualTo(crowdQna.getCrowdId());
        assertThat(crowdQna.getUpdatedAt()).isAfter(crowdQna.getPublishedAt());
    }

   @Test
    @Transactional
    @DisplayName("crowdId 1번글의 2번째 글 삭제시 데이터 값이 NULL 값일 것이고 전체 글의 갯수는 2개일것이다.")
    public void deleteByCrowdBoardIdTest(){

        // given
        int crowdId = 1;
        int crowdQnaId = 2;

        // when
        dynamicCrowdQnaRepository.deleteByCrowdQnaId(crowdId, crowdQnaId);
        DynamicCrowdQnaDTO.Find crowdQna = dynamicCrowdQnaRepository.findById(crowdId, crowdQnaId);

        // then
        assertThat(crowdQna.isDeleted()).isTrue();
    }
}
