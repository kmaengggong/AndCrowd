package com.fiveis.andcrowd.repository.and;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AndDynamicRepository {
    void createDynamicAndQnaTable(int andId);
    void createDynamicAndQnaReplyTable(int andId);
    void createDynamicAndRoleTable(int andId);
    void createDynamicAndApplicantTable(int andId);
    void createDynamicAndBoardTable(int andId);
    void createDynamicAndMemberTable(int andId);
}
