package com.fiveis.andcrowd.repository.and;
import com.fiveis.andcrowd.entity.and.And;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AndJPARepositoryTest {

    @Autowired
    private AndJPARepository andRepository;
    @Test
    @BeforeEach
    public void saveTest(){
        // Given

        // When
        // Given
        And newAnd = And.builder()
                .userId(1)
                .andCategoryId(1)
                .andTitle("Test Title")
                .andHeaderImg("header.jpg")
                .andContent("Test Content")
                .andEndDate(LocalDateTime.now().plusDays(7))
                .needNumMem(5)
                .build();

        andRepository.save(newAnd);

        // Then
    }

    @Test
    @Transactional
    public void testSoftDeleteById() {
        // Given
        And newAnd2 = And.builder()
                .userId(3)
                .andCategoryId(5)
                .crowdId(1)
                .andTitle("Test Title2")
                .andHeaderImg("header.jpg2")
                .andContent("Test Content2")
                .andEndDate(LocalDateTime.now().plusDays(10))
                .needNumMem(5)
                .build();

        andRepository.save(newAnd2);
        andRepository.flush();

        int andIdToDelete = newAnd2.getAndId();

        // When
        andRepository.deleteById(andIdToDelete);
        andRepository.flush();

        // Then
        And deletedAnd = andRepository.findById(andIdToDelete).orElse(null);
        assertNotNull(deletedAnd);
        assertTrue(deletedAnd.isDeleted());
    }


}
