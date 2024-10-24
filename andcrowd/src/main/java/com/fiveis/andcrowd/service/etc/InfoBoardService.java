package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.InfoBoardDTO;
import com.fiveis.andcrowd.entity.etc.InfoBoard;

import java.util.List;
import java.util.Optional;

public interface InfoBoardService {

    Optional<InfoBoardDTO.Find> findById(int infoId);
    List<InfoBoardDTO.Find> findAll();
    List<InfoBoardDTO.Find> findAllByIsDeletedFalseOrderByInfoIdDesc();

    void deleteById(int infoId);
    void save(InfoBoard infoBoard);
    void update(InfoBoard infoBoard);
}
