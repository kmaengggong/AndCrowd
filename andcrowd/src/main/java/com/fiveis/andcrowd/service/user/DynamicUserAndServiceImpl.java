package com.fiveis.andcrowd.service.user;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserAndDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import com.fiveis.andcrowd.repository.user.DynamicUserAndRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicUserAndServiceImpl implements DynamicUserAndService{
    private final DynamicUserAndRepository dynamicUserAndRepository;
    private final AndJPARepository andJPARepository;

    @Autowired
    public DynamicUserAndServiceImpl(DynamicUserAndRepository dynamicUserAndRepository,
                                     AndJPARepository andJPARepository){
        this.dynamicUserAndRepository = dynamicUserAndRepository;
        this.andJPARepository = andJPARepository;
    }

    public List<AndDTO.Find> findAll(String userEmail){
        List<DynamicUserAndDTO.Find> findList = dynamicUserAndRepository.findAll(userEmail);
        List<AndDTO.Find> andList = new ArrayList<>();
        for(DynamicUserAndDTO.Find find : findList){
            if(andJPARepository.findById(find.getAndId()).isEmpty()) continue;
            andList.add(AndDTO.convertToAndFindDTO(andJPARepository.findById(find.getAndId()).get()));
        }
        return andList;
    }

    public DynamicUserAndDTO.Find findById(String userEmail, int uAndId){
        return dynamicUserAndRepository.findById(userEmail, uAndId);
    }

    public boolean save(String userEmail, DynamicUserAnd dynamicUserAnd){
        // 존재하지 않는 andId
        if(andJPARepository.findById(dynamicUserAnd.getAndId()).isEmpty()) return false;
        // user_and에 이미 존재
        if(dynamicUserAndRepository.findByAndId(userEmail, dynamicUserAnd.getAndId()) != null) return false;
        // 그 외에는 저장 성공
        dynamicUserAndRepository.save(userEmail, dynamicUserAnd);
        return true;
    }

    public void deleteById(String userEmail, int uAndId){
        dynamicUserAndRepository.deleteById(userEmail, uAndId);
    }

    public void deleteByAndId(String userEmail, int andId){
        dynamicUserAndRepository.deleteByAndId(userEmail, andId);
    }
    public void deleteTableByUserEmail(String userEmail){
        dynamicUserAndRepository.deleteTableByUserEmail(userEmail);
    }
}
