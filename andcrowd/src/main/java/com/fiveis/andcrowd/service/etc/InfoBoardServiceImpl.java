package com.fiveis.andcrowd.service.etc;

import com.fiveis.andcrowd.dto.etc.InfoBoardDTO;
import com.fiveis.andcrowd.entity.etc.InfoBoard;
import com.fiveis.andcrowd.repository.etc.InfoBoardJPARepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fiveis.andcrowd.dto.etc.InfoBoardDTO.convertToInfoFindDTO;

@Service
public class InfoBoardServiceImpl implements InfoBoardService{

    InfoBoardJPARepository infoBoardJPARepository;

    public InfoBoardServiceImpl(InfoBoardJPARepository infoBoardJPARepository) {
        this.infoBoardJPARepository = infoBoardJPARepository;
    }

    @Override
    public Optional<InfoBoardDTO.Find> findById(int infoId) {
        Optional<InfoBoard> infoBoardOptional = infoBoardJPARepository.findById(infoId);
        return infoBoardOptional.map(InfoBoardDTO::convertToInfoFindDTO);
    }

    @Override
    public List<InfoBoardDTO.Find> findAll(){
        List<InfoBoard> infoList = infoBoardJPARepository.findAll();
        List<InfoBoardDTO.Find> findList = new ArrayList<>();

        for(InfoBoard infoBoard : infoList) {
            InfoBoardDTO.Find result = convertToInfoFindDTO(infoBoard);
            findList.add(result);
        }
        return findList;
    }

    @Override
    public List<InfoBoardDTO.Find> findAllByIsDeletedFalseOrderByInfoIdDesc() {
        List<InfoBoard> infoList = infoBoardJPARepository.findAllByIsDeletedFalseOrderByInfoIdDesc();
        List<InfoBoardDTO.Find> findAllNotDeletedList = new ArrayList<>();

        for(InfoBoard infoBoard : infoList) {
            InfoBoardDTO.Find result = convertToInfoFindDTO(infoBoard);
            findAllNotDeletedList.add(result);
        }
        return findAllNotDeletedList;
    }

    @Override
    @Transactional
    public void deleteById(int infoId) {
        Optional<InfoBoard> infoBoardOptional = infoBoardJPARepository.findById(infoId);
        if(infoBoardOptional.isPresent()) {
            InfoBoard infoBoard = infoBoardOptional.get();
            infoBoard.setDeleted(true);
            infoBoardJPARepository.save(infoBoard);
        }
    }

    @Override
    public void update(InfoBoard infoBoard) {
        Optional<InfoBoard> infoBoardOptional = infoBoardJPARepository.findById(infoBoard.getInfoId());
        if(infoBoardOptional.isPresent()) {
            InfoBoard updateInfo = infoBoardOptional.get();
            if(infoBoard.getInfoTitle() != null) updateInfo.setInfoTitle(infoBoard.getInfoTitle());
            if(infoBoard.getInfoContent() != null) updateInfo.setInfoContent(infoBoard.getInfoContent());
            updateInfo.setUpdatedAt(LocalDateTime.now());
            infoBoardJPARepository.save(updateInfo);
        } else {
            throw new EntityNotFoundException("존재하지않는 게시글 입니다.");
        }
    }

    @Override
    public void save(InfoBoard infoBoard) {
        infoBoardJPARepository.save(infoBoard);
    }
}
