package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.DynamicCrowdBoardDTO;
import com.fiveis.andcrowd.service.crowd.DynamicCrowdBoardService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DynamicCrowdBoardServiceTest {

    @Autowired
    DynamicCrowdBoardService dynamicCrowdBoardService;

    @Test
    @Transactional
    @DisplayName("2번 crowd게시글 생성시 crowd_board_2 이라는 이름의 테이블이 생성되며, 글이 정상적으로 추가된다.")
    public void createDynamicCrowdBoardTableTest(){
        // given
        int crowdId = 2;
        int tag = 1;
        String title = "추가된 제목";
        String content = "추가된 본문";
        String img = "추가된 이미지";

        // when
        dynamicCrowdBoardService.createDynamicCrowdBoardTable(crowdId);
        DynamicCrowdBoardDTO.Save newBoard = new DynamicCrowdBoardDTO.Save();
        newBoard.setCrowdId(crowdId);
        newBoard.setCrowdBoardTag(tag);
        newBoard.setCrowdBoardTitle(title);
        newBoard.setCrowdBoardContent(content);
        newBoard.setCrowdImg(img);
        dynamicCrowdBoardService.save(newBoard);

        // then
        List<DynamicCrowdBoardDTO.Find> crowdBoardList = dynamicCrowdBoardService.findAll(crowdId);
        assertThat(crowdBoardList.get(0).getCrowdId()).isEqualTo(crowdId);
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 전체글 조회시 3개일 것이다.")
    public void findAllTest(){

        // given
        int crowdId = 1;

        // when
        List<DynamicCrowdBoardDTO.Find> crowdBoardList = dynamicCrowdBoardService.findAll(crowdId);

        // then
        assertThat(crowdBoardList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 2번째 글의 제목은 '2번글제목', 본문은 '2번글본문' 일것이다.")
    public void findByIdTest(){

        // given
        int crowdId = 1;
        int crowdBoardId = 2;
        String title = "2번글제목";
        String content = "2번글본문";

        // when
        //Map<String, Integer> params = new HashMap<>();
        //params.put("crowdId", crowdId);
        //params.put("crowdBoardId", crowdBoardId);
        DynamicCrowdBoardDTO.Find crowdBoard = dynamicCrowdBoardService.findById(crowdId, crowdBoardId);

        // then
        assertThat(crowdBoard.getCrowdId()).isEqualTo(crowdId);
        assertThat(crowdBoard.getCrowdBoardId()).isEqualTo(crowdBoardId);
        assertThat(crowdBoard.getCrowdBoardTitle()).isEqualTo(title);
        assertThat(crowdBoard.getCrowdBoardContent()).isEqualTo(content);

    }

    @Test
    @Transactional
    @DisplayName("추가된 글의 데이터는. 전체 데이터의 첫번째 인덱스의 각요소들은 입력한 데이터와 일치할 것이다.")
    public void saveTest(){
        // given
        int crowdId = 1;
        int tag = 1;
        String title = "추가된 제목";
        String content = "추가된 본문";
        String img = "추가된 이미지";


        // when
        // 빌더로 리팩토링 예정
        DynamicCrowdBoardDTO.Save newBoard = new DynamicCrowdBoardDTO.Save();
        newBoard.setCrowdId(crowdId);
        newBoard.setCrowdBoardTag(tag);
        newBoard.setCrowdBoardTitle(title);
        newBoard.setCrowdBoardContent(content);
        newBoard.setCrowdImg(img);
        dynamicCrowdBoardService.save(newBoard);

        List<DynamicCrowdBoardDTO.Find> crowdBoardList = dynamicCrowdBoardService.findAll(crowdId);

        DynamicCrowdBoardDTO.Find crowdBoard = crowdBoardList.get(0);

        // then
        assertThat(title).isEqualTo(crowdBoard.getCrowdBoardTitle());
        assertThat(content).isEqualTo(crowdBoard.getCrowdBoardContent());
        assertThat(img).isEqualTo(crowdBoard.getCrowdImg());
        assertThat(crowdId).isEqualTo(crowdBoard.getCrowdId());
        assertThat(tag).isEqualTo(crowdBoard.getCrowdBoardTag());
    }

    @Test
    @Transactional
    @DisplayName("2번글에 수정된 내용을 입력시 입력된 내용으로 수정된다.")
    public void updateTest(){

        // given
        int crowdId = 1;
        int crowdBoardId = 2;
        int tag = 1;
        String title = "수정된 제목";
        String content = "수정된 본문";
        String img = "수정된 이미지";


        // when
        // 빌더로 리팩토링 예정
        DynamicCrowdBoardDTO.Update newBoard = new DynamicCrowdBoardDTO.Update();
        newBoard.setCrowdId(crowdId);
        newBoard.setCrowdBoardTag(tag);
        newBoard.setCrowdBoardTitle(title);
        newBoard.setCrowdBoardContent(content);
        newBoard.setCrowdImg(img);
        newBoard.setCrowdBoardId(crowdBoardId);
        dynamicCrowdBoardService.update(newBoard);

        List<DynamicCrowdBoardDTO.Find> crowdBoardList = dynamicCrowdBoardService.findAll(crowdId);

        DynamicCrowdBoardDTO.Find crowdBoard = crowdBoardList.get(0);

        // then
        assertThat(title).isEqualTo(crowdBoard.getCrowdBoardTitle());
        assertThat(content).isEqualTo(crowdBoard.getCrowdBoardContent());
        assertThat(img).isEqualTo(crowdBoard.getCrowdImg());
        assertThat(crowdId).isEqualTo(crowdBoard.getCrowdId());
        assertThat(tag).isEqualTo(crowdBoard.getCrowdBoardTag());
        assertThat(crowdBoard.getUpdatedAt()).isAfter(crowdBoard.getPublishedAt());
    }

    @Test
    @Transactional
    @DisplayName("crowdId 1번글의 2번째 글 삭제시 isdDeleted가 true가 될것이다.")
    public void deleteByCrowdBoardIdTest(){

        // given
        int crowdId = 1;
        int crowdBoardId = 2;

        // when
        dynamicCrowdBoardService.deleteByCrowdBoardId(crowdId, crowdBoardId);
        DynamicCrowdBoardDTO.Find crowdBoard = dynamicCrowdBoardService.findById(crowdId, crowdBoardId);


        // then
        assertThat(crowdBoard.isDeleted()).isTrue();
    }
}
