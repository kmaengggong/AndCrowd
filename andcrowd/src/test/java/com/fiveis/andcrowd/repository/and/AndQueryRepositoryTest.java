//package com.fiveis.andcrowd.repository.and;
//
//import com.fiveis.andcrowd.dto.and.AndDTO;
//import com.fiveis.andcrowd.entity.and.And;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.jdbc.datasource.init.ScriptUtils;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import javax.sql.DataSource;
//
//
//@SpringBootTest
//public class AndQueryRepositoryTest {
//
//    @Autowired
//    @Qualifier("andQueryRepositoryImpl")
//    private AndQueryRepository andQueryRepository;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @BeforeEach
//    public void setup() throws SQLException {
//        try (Connection connection = dataSource.getConnection()) {
//            ClassPathResource scriptResource = new ClassPathResource("/dummy_data.sql");
//            ScriptUtils.executeSqlScript(connection, scriptResource);
//        }
//    }
//
//    @AfterEach
//    public void cleanup() throws SQLException {
//        try (Connection connection = dataSource.getConnection()) {
//            ClassPathResource scriptResource = new ClassPathResource("/drop_dummy_data.sql");
//            ScriptUtils.executeSqlScript(connection, scriptResource);
//        }
//    }
//
//    @Test
//    public void testInfiniteScroll() {
//        // given
//        int categoryId = 1;
//        int andStatus = 0;
//        String sortField = "publishedAt";
//        String sortOrder = "desc";
//        int pageSize = 2;
//
//        int pageNumber = 0;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        // when
//        Slice<AndDTO.Find> result = andQueryRepository.findAllByCategoryAndStatusAndSort(
//                categoryId, andStatus, sortField, sortOrder, pageable);
//
//        while (result.hasContent()) {
//            for (AndDTO.Find andDTO : result.getContent()) {
//                System.out.println(andDTO.toString());
//            }
//
//            pageNumber++;
//            pageable = PageRequest.of(pageNumber, pageSize);
//            result = andQueryRepository.findAllByCategoryAndStatusAndSort(
//                    categoryId, andStatus, sortField, sortOrder, pageable);
//        }
//    }
//
//    @Test
//    @DisplayName("No-Offset 방식을 사용하면 lastStoreId값 -1 부터 page size 만큼 가져옴")
//    public void testFindAll() {
//        // given
//        Slice<AndDTO.Find> result = andQueryRepository.findAllByCategoryAndStatusAndSort(1, 0, "andEndDate", "asc", PageRequest.ofSize(3));
//
//        // when
//        int first = result.getContent().get(0).getAndId();
//        int last = result.getContent().get(2).getAndId();
//
//        // then
//        Assertions.assertThat(first).isEqualTo(1);
//        Assertions.assertThat(last).isEqualTo(7);
//
//    }
//
//    @Test
//    @DisplayName("마지막 페이지에서는 isLast가 true, 마지막이 아니면 isLast가 false")
//    void checkLast()
//    {
//        // Given
//        Slice<AndDTO.Find> getLastPage = andQueryRepository.findAllByCategoryAndStatusAndSort(
//                1, 0, "andEndDate", "asc", PageRequest.ofSize(3));
//        Slice<AndDTO.Find> getMiddlePage = andQueryRepository.findAllByCategoryAndStatusAndSort(
//                1, 0, "andEndDate", "asc", PageRequest.ofSize(2));
//
//        // When
//        boolean isLastPage = getLastPage.isLast();
//        boolean isNotLastPage = getMiddlePage.isLast();
//
//        // Then
//        Assertions.assertThat(isLastPage).isTrue();
//        Assertions.assertThat(isNotLastPage).isFalse();
//    }
//
//}
