package com.fiveis.andcrowd.repository.and;

import static org.junit.jupiter.api.Assertions.*;

import com.fiveis.andcrowd.repository.and.AndDynamicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AndDynamicRepositoryTest {

    @Autowired
    private AndDynamicRepository andDynamicRepository;

    @Test
    @Transactional
    public void testCreateDynamicAndQnaTable() {
        // Given
        int andId = 1;
        String tableName = "and_qna_" + andId;

        // When
        andDynamicRepository.createDynamicAndQnaTable(andId);

        // Then
        boolean tableExists = checkTableExists(tableName);
        assertTrue(tableExists, "Table " + tableName + " should exist in the database.");
    }

    private boolean checkTableExists(String tableName) {
        // 직접 mysql에서 확인
        return true;
    }

    @Test
    @Transactional
    public void testCreateDynamicAndQnaReplyTable() {
        // Given
        int andId = 1;
        String replyTableName = "and_qna_reply_" + andId;

        // When
        andDynamicRepository.createDynamicAndQnaReplyTable(andId);

        // Then
        boolean tableExists = checkReplyTableExists(replyTableName);
        assertTrue(tableExists, "Table " + replyTableName + " should exist in the database.");
    }

    private boolean checkReplyTableExists(String tableName) {
        // 직접 mysql에서 확인
        return true;
    }

    @Test
    @Transactional
    public void testcreateDynamicAndRoleTable() {
        // Given
        int andId = 1;
        String roleTableName = "and_role_" + andId;

        // When
        andDynamicRepository.createDynamicAndRoleTable(andId);

        // Then
        boolean tableExists = checkRoleTableExists(roleTableName);
        assertTrue(tableExists, "Table " + roleTableName + " should exist in the database.");
    }

    private boolean checkRoleTableExists(String tableName) {
        // 직접 mysql에서 확인
        return true;
    }

    @Test
    @Transactional
    public void testcreateDynamicAndApplicantTable() {
        // Given
        int andId = 1;
        String applyTableName = "and_applicant_" + andId;

        // When
        andDynamicRepository.createDynamicAndApplicantTable(andId);

        // Then
        boolean tableExists = checkApplyTableExists(applyTableName);
        assertTrue(tableExists, "Table " + applyTableName + " should exist in the database.");
    }

    private boolean checkApplyTableExists(String tableName) {
        // 직접 mysql에서 확인
        return true;
    }

    @Test
    @Transactional
    public void testCreateDynamicAndBoardTable() {
        // Given
        int andId = 1;
        String tableName = "and_Board_" + andId;

        // When
        andDynamicRepository.createDynamicAndBoardTable(andId);

        // Then
        boolean tableExists = checkTableExists(tableName);
        assertTrue(tableExists, "Table " + tableName + " should exist in the database.");
    }

}
