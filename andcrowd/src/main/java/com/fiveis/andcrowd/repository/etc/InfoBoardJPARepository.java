package com.fiveis.andcrowd.repository.etc;

import com.fiveis.andcrowd.dto.crowd.CrowdDTO;
import com.fiveis.andcrowd.dto.etc.InfoBoardDTO;
import com.fiveis.andcrowd.entity.etc.InfoBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoBoardJPARepository extends JpaRepository<InfoBoard, Integer> {
    List<InfoBoard> findAllByIsDeletedFalseOrderByInfoIdDesc();
}
