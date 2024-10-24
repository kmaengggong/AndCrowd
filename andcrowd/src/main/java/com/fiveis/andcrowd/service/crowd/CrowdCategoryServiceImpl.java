package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdCategoryDTO;
import com.fiveis.andcrowd.entity.crowd.CrowdCategory;
import com.fiveis.andcrowd.repository.crowd.CrowdCategoryJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrowdCategoryServiceImpl implements CrowdCategoryService{

    private final CrowdCategoryJPARepository crowdCategoryJPARepository;

    @Autowired
    public CrowdCategoryServiceImpl(CrowdCategoryJPARepository crowdCategoryJPARepository){
        this.crowdCategoryJPARepository = crowdCategoryJPARepository;
    }

    // 엔터티를 DTO로 변환해서 반환해주는 메서드
    // stream().map() 를 사용하여 각 요소마다 DTO쪽에 선언된 Entity -> DTO 변환 메서드를 적용시켜줌
    // 이후 List 형태로 복원하여 리턴
    @Override
    public List<CrowdCategoryDTO.Find> findAllByIsDeletedFalse() {
        List<CrowdCategory> categoryList = crowdCategoryJPARepository.findAllByIsDeletedFalse();
        List<CrowdCategoryDTO.Find> findAllNotDeletedList = new ArrayList<>();

        for(CrowdCategory crowdCategory : categoryList) {
            CrowdCategoryDTO.Find dto = CrowdCategoryDTO.convertToCrowdCategoryFindDTO(crowdCategory);
            findAllNotDeletedList.add(dto);
        }
        return findAllNotDeletedList;
    }


    @Override
    public Optional<CrowdCategoryDTO.Find> findById(int crowdCategoryId) {
        Optional<CrowdCategory> optioanlCrowdCategory = crowdCategoryJPARepository.findById(crowdCategoryId);
            return optioanlCrowdCategory.map(this::convertToCrowdCategoryFindDTO);
    }

    @Override
    public void save(CrowdCategory crowdCategory) {
       CrowdCategory saveCategory = crowdCategoryJPARepository.save(crowdCategory);
    }

    @Override
    public void update(CrowdCategory crowdCategory) {
        crowdCategoryJPARepository.save(crowdCategory);

//        // convertToCrowdCategoryUpdateDTO 메서드를 통해 Entity를 DTO로 변환
//        CrowdCategoryDTO.Update category = CrowdCategoryDTO.Update.(
//                crowdCategoryJPARepository.findById(crowdCategoryDTOUpdate.getCrowdCategoryId()).get());
//
//        // DTO로 변환된 객체에 수정내용 반영
//        category.setCrowdCategoryName(crowdCategoryDTOUpdate.getCrowdCategoryName());
//
//        // 수정된 객체를 Entity 형태로 변환
//        CrowdCategory crowdCategoryEntity = CrowdCategoryDTO.Update.convertToCrowdCategoryEntity(category);
//
//        // save 기능 수행
//        crowdCategoryJPARepository.save(crowdCategoryEntity);
    }

    @Override
    public CrowdCategoryDTO.Find convertToCrowdCategoryFindDTO(CrowdCategory crowdCategory) {
        CrowdCategoryDTO.Find convertedDTO = CrowdCategoryDTO.Find.builder()
                .crowdCategoryId(crowdCategory.getCrowdCategoryId())
                .crowdCategoryName(crowdCategory.getCrowdCategoryName())
                .isDeleted(crowdCategory.isDeleted())
                .build();
        return convertedDTO;
    }

    @Override
    public void deleteById(int crowdCategoryId) {
        crowdCategoryJPARepository.deleteById(crowdCategoryId);
    }
}
