//package com.fiveis.andcrowd.service.crowd;
//
//import com.fiveis.andcrowd.dto.crowd.CrowdCategoryDTO;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class CrowdCategoryServiceTest {
//    @Autowired
//    CrowdCategoryService crowdCategoryService;
//
//    @Test
//    @Transactional
//    @DisplayName("전체글 조회시 데이터의 갯수는 4개일것이다.")
//    public void finaAllTest(){
//        //when
//        List<CrowdCategoryDTO.Find> categoryList = crowdCategoryService.findAll();
//
//        //then
//        assertThat(categoryList.size()).isEqualTo(4);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("2번 카테고리 조회시 crowdCategoryName는 제조일것이다.")
//    public void findByIdTest(){
//        //given
//        int crowdCategoryId = 2;
//        String crowdCategoryName = "제조";
//
//        //when
//        CrowdCategoryDTO.Find crowdCategory = crowdCategoryService.findById(crowdCategoryId);
//
//        //then
//        assertThat(crowdCategory.getCrowdCategoryName()).isEqualTo(crowdCategoryName);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("새로운 카테고리 저장후 전체 category의 마지막 인덱스 조회시 categoryName이 일치할것이다.")
//    public void saveTest(){
//        // given
//        String crowdCategoryName = "개발";
//
//        // when
//        CrowdCategoryDTO.Save newCategory = new CrowdCategoryDTO.Save();
//        newCategory.setCrowdCategoryName(crowdCategoryName);
//        crowdCategoryService.save(newCategory);
//        List<CrowdCategoryDTO.Find> categoryList = crowdCategoryService.findAll();
//
//        // then
//        assertThat(categoryList.get(categoryList.size()-1).getCrowdCategoryName()).isEqualTo(crowdCategoryName);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("crowdCategoryId 3번의 categoryName을 수정시 수정된 내용과 일치한다")
//    public void updateTest(){
//        // given
//        int crowdCategoryId = 3;
//        String crowdCategoryName = "개발";
//
//        // when
//        CrowdCategoryDTO.Update updateCategory = CrowdCategoryDTO.Update.builder()
//                .crowdCategoryId(crowdCategoryId)
//                .crowdCategoryName(crowdCategoryName)
//                .build();
//        crowdCategoryService.update(updateCategory);
//
//        CrowdCategoryDTO.Find category = crowdCategoryService.findById(crowdCategoryId);
//
//        // then
//        assertThat(category.getCrowdCategoryName()).isEqualTo(crowdCategoryName);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("crowdCategoryId 3번 삭제시 전체 crowdCategory 갯수는 3개일것이다.")
//    public void deleteByIdTest(){
//        // given
//        int crowdCategoryId = 3;
//
//        // when
//        crowdCategoryService.deleteById(crowdCategoryId);
//        List<CrowdCategoryDTO.Find> categoryList = crowdCategoryService.findAll();
//
//        // then
//        assertThat(categoryList.size()).isEqualTo(3);
//    }
//}
