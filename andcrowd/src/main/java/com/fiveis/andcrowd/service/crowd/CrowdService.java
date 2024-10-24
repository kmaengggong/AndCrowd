package com.fiveis.andcrowd.service.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CrowdService {

    Optional<CrowdDTO.FindById> findByCrowdId(int crowdId);

    List<CrowdDTO.FindAllByUserId> findAllByUserIdList(int userId);

    List<CrowdDTO.FindById> findAllByIsDeletedFalse();
    List<CrowdDTO.FindById> findAll();

    void deleteByCrowdId(int crowdId);

    void save(Crowd crowd);

//    public CrowdDTO.FindById convertToAndFindByCrowdId(Crowd crowd);

    void update(Crowd crowd);
    void updateStatus(int crowdId, int crowdStatus);

    void updateStatusForExpiredCrowd();

    Page<CrowdDTO.FindById> searchPageList(Integer crowdStatus, String sortField, Integer pageNumber, Integer crowdCategoryId, String keyword, Pageable pageable);

    List<CrowdDTO.FindById> findByViewCountAndLikeSum();

    int updateView(int crowdId);

    void increaseLike(Integer crowdId);

    void decreaseLike(Integer crowdId);

    void updateLike(Integer crowdId, int userId);

    boolean isLiked(int crowdId, int userId);

    int totalCount(String searchKeyword);

}