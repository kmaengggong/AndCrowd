package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicAndMemberService {
    void save(DynamicAndMemberDTO.Update dynamicAndMemberDTOUpdate);
    List<DynamicAndMemberDTO.FindByAndMemberId> findAllNotDeleted(int andId);
    List<DynamicAndMemberDTO.FindByAndMemberId> findAllNotDeletedByUserId(int andId, int userId);


    DynamicAndMemberDTO.FindByAndMemberId findByAndMemberId(int andId, int memberId);

    List<DynamicAndMemberDTO.FindByAndMemberId> findAll(int andId);

    void deleteById(int andId, int memberId);
    void deleteByUserId(int andId, int userId);

    void update(DynamicAndMemberDTO.Update andMemberUpdateDTO);

    List<UserDTO.UserChatInfo> findAllNotdeletedWithProfile(int andId);
}
