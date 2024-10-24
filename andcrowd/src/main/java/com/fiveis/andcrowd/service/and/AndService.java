package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.entity.and.And;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AndService {

    Optional<AndDTO.Find> findById(int andId);

    List<AndDTO.FindAllByUserId> findAllByUserId(int userId);

    List<AndDTO.Find> findAllByIsDeletedFalse();
    List<AndDTO.Find> findAll();

    void deleteById(int andId);

    void save(And and);
    void update(And and);

    void updateStatus(int andId, int andStatus);
    void updateNeedNumMem(int andId, int needNumMem);

    void updateStatusForExpiredItems();

    int updateView(int andId);

    void increaseLike(Integer andId);

    void decreaseLike(Integer andId);

    void updateLike(Integer andId, int userId);

    boolean isLiked(int andId, int userId);

    void updateFollow(int myId, int userId);

    boolean isFollowed(int andId, int userId);

    int totalCount(String searchKeyword);

}