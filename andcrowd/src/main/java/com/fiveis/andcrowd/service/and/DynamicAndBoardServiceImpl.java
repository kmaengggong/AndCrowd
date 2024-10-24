package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndBoardDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndBoardRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicAndBoardServiceImpl implements DynamicAndBoardService {
    DynamicAndBoardRepository dynamicAndBoardRepository;

    @Autowired
    public DynamicAndBoardServiceImpl(DynamicAndBoardRepository dynamicAndBoardRepository) {
        this.dynamicAndBoardRepository = dynamicAndBoardRepository;
    }

    @Override
    public List<DynamicAndBoardDTO.FindById> findAll(int andId) {
        return dynamicAndBoardRepository.findAll(andId);
    }

    @Override
    public List<DynamicAndBoardDTO.FindById> findAllNotDeleted(@Param("offset") int offset, @Param("limit") int limit, int andId) {
        return dynamicAndBoardRepository.findAllNotDeleted(offset, limit, andId);
    }

    @Override
    public DynamicAndBoardDTO.FindById findByAndBoardId(int andId, int andBoardId) {
        return dynamicAndBoardRepository.findByAndBoardId(andId, andBoardId);
    }

    @Override
    public void save(DynamicAndBoardDTO.Update andBoardInsertDTO) {
        andBoardInsertDTO.setPublishedAt(LocalDateTime.now());
        andBoardInsertDTO.setUpdatedAt(LocalDateTime.now());
        dynamicAndBoardRepository.save(andBoardInsertDTO);
    }

    @Override
    public void update(DynamicAndBoardDTO.Update andBoardUpdateDTO) {
        andBoardUpdateDTO.setUpdatedAt(LocalDateTime.now());
        dynamicAndBoardRepository.update(andBoardUpdateDTO);
    }

    @Override
    public void deleteAll(int andId) {
        dynamicAndBoardRepository.deleteAll(andId);
    }

    @Override
    public void deleteByAndBoardId(int andId, int andBoardId) {
        dynamicAndBoardRepository.deleteByAndBoardId(andId, andBoardId);
    }
    @Override
    public void deleteByAndId(int andId) {
        dynamicAndBoardRepository.deleteByAndId(andId);
    }

    @Override
    public void createAndBoardTable() {
        dynamicAndBoardRepository.createAndBoardTable();
    }

    @Override
    public void dropAndBoardTable() {
        dynamicAndBoardRepository.dropAndBoardTable();
    }

    @Override
    public void insertTestData() {
        dynamicAndBoardRepository.insertTestData();
    }

    @Override
    public int countAll(int andId) {
        return dynamicAndBoardRepository.countNotDeleted(andId);
    }
}
