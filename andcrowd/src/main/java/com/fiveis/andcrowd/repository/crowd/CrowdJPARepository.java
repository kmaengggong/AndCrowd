package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.entity.crowd.Crowd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrowdJPARepository extends JpaRepository<Crowd, Integer>, JpaSpecificationExecutor<Crowd> {

    // userId를 기준으로 전체 글을 얻어오는 메서드를 쿼리메서드 방식으로 생성
    List<CrowdDTO.FindAllByUserId> findAllByUserId(int userId);
    List<Crowd> findAllByIsDeletedFalse();

    Page<Crowd> findByCrowdCategoryIdAndCrowdStatusAndIsDeletedFalseAndCrowdTitleContaining(Integer crowdCategoryId, Integer crowdStatus, String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE Crowd c SET c.viewCount = c.viewCount + 1 WHERE c.crowdId = :crowdId")
    int updateView(@Param("crowdId") Integer crowdId);

    @Modifying
    @Query("UPDATE Crowd c SET c.likeSum = c.likeSum + 1 WHERE c.crowdId = :crowdId")
    void increaseLike(@Param("crowdId") Integer crowdId);

    @Modifying
    @Query("UPDATE Crowd c SET c.likeSum = c.likeSum - 1 WHERE c.crowdId = :crowdId")
    void decreaseLike(@Param("crowdId") Integer crowdId);

}