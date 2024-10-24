package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserAndDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicUserAndService {
    List<AndDTO.Find> findAll(String userEmail);  // 모임의 헤더 이미지, 제목, 내용 가져옴
    DynamicUserAndDTO.Find findById(String userEmail, int uAndId);
    boolean save(String userEmail, DynamicUserAnd dynamicUserAnd);
    void deleteById(String userEmail, int uAndId);
    void deleteByAndId(String userEmail, int andId);
    void deleteTableByUserEmail(String userEmail);
}
