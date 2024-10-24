package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.AndcrowdApplication;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 DB 사용 비활성화
@ContextConfiguration(classes = AndcrowdApplication.class)
public class AndJPARepositoryFindAllTest {
    @Autowired
    private AndJPARepository andJPARepository;

    @Test
    public void testFindAllNotDeleted() {
        List<And> andList = andJPARepository.findAllByIsDeletedFalse();

        assertEquals(1, andList.size());
    }
}
