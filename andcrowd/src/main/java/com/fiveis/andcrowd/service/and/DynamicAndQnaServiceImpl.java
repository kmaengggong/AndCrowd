package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndQnaReplyRepository;
import com.fiveis.andcrowd.repository.and.DynamicAndQnaRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicAndQnaServiceImpl implements DynamicAndQnaService{

    DynamicAndQnaRepository dynamicAndQnaRepository;
    DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository;

    @Autowired
    public DynamicAndQnaServiceImpl(DynamicAndQnaRepository dynamicAndQnaRepository, DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository){
        this.dynamicAndQnaRepository = dynamicAndQnaRepository;
        this.dynamicAndQnaReplyRepository = dynamicAndQnaReplyRepository;
    }

    @Override
    public List<DynamicAndQnaDTO.FindById> findAll(int andId) {
        return dynamicAndQnaRepository.findAll(andId);
    }

    @Override
    public List<DynamicAndQnaDTO.FindById> findAllNotDeleted(@Param("offset") int offset, @Param("limit") int limit, int andId) {
        return dynamicAndQnaRepository.findAllNotDeleted(offset, limit, andId);
    }

    @Override
    public DynamicAndQnaDTO.FindById findByAndQnaId(int andId, int andQnaId) {
        return dynamicAndQnaRepository.findByAndQnaId(andId, andQnaId);
    }

    @Override
    public void save(DynamicAndQnaDTO.Update andQnaInsertDTO) {
        andQnaInsertDTO.setPublishedAt(LocalDateTime.now());
        andQnaInsertDTO.setUpdatedAt(LocalDateTime.now());
        dynamicAndQnaRepository.save(andQnaInsertDTO);
    }

    @Override
    public void update(DynamicAndQnaDTO.Update andQnaUpdateDTO) {
        andQnaUpdateDTO.setUpdatedAt(LocalDateTime.now());
        dynamicAndQnaRepository.update(andQnaUpdateDTO);
    }

    @Override
    public void deleteByAndQnaId(int andId, int andQnaId) {
        dynamicAndQnaReplyRepository.deleteByAndQnaId(andId, andQnaId);
        dynamicAndQnaRepository.deleteByAndQnaId(andId, andQnaId);
    }

    @Override
    public void deleteAll(int andId) {
        dynamicAndQnaRepository.deleteAll(andId);
    }

    @Override
    public int countAll(int andId) {
        return dynamicAndQnaRepository.countNotDeleted(andId);
    }



}
