package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.DynamicAndQnaReplyDTO;
import com.fiveis.andcrowd.repository.and.DynamicAndQnaReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicAndQnaReplyServiceImpl implements DynamicAndQnaReplyService{

    DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository;

    @Autowired
    public DynamicAndQnaReplyServiceImpl(DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository){
        this.dynamicAndQnaReplyRepository = dynamicAndQnaReplyRepository;
    }

    @Override
    public List<DynamicAndQnaReplyDTO.FindById> findAll(int andId) {
        return dynamicAndQnaReplyRepository.findAll(andId);
    }

    @Override
    public List<DynamicAndQnaReplyDTO.FindById> findAllNotDeleted(int andId) {
        return dynamicAndQnaReplyRepository.findAllNotDeleted(andId);
    }

    @Override
    public List<DynamicAndQnaReplyDTO.FindById> findAllByAndQnaId(int andId, int andQnaId) {
        return dynamicAndQnaReplyRepository.findAllByAndQnaId(andId, andQnaId);
    }

    @Override
    public DynamicAndQnaReplyDTO.FindById findByAndReplyId(int andId, int andReplyId) {
        return dynamicAndQnaReplyRepository.findByAndReplyId(andId, andReplyId);
    }

    @Override
    public void save(DynamicAndQnaReplyDTO.Update andReplySaveDTO) {
        andReplySaveDTO.setPublishedAt(LocalDateTime.now());
        andReplySaveDTO.setUpdatedAt(LocalDateTime.now());
        dynamicAndQnaReplyRepository.save(andReplySaveDTO);
    }

    @Override
    public void update(DynamicAndQnaReplyDTO.Update andReplyUpdateDTO) {
        andReplyUpdateDTO.setUpdatedAt(LocalDateTime.now());
        dynamicAndQnaReplyRepository.update(andReplyUpdateDTO);
    }

    @Override
    public void deleteByAndReplyId(int andId, int andReplyId) {
        dynamicAndQnaReplyRepository.deleteByAndReplyId(andId, andReplyId);
    }

    @Override
    public void deleteByAndQnaId(int andId, int andQnaId) {
        dynamicAndQnaReplyRepository.deleteByAndQnaId(andId, andQnaId);
    }

    @Override
    public void deleteAll(int andId) {
        dynamicAndQnaReplyRepository.deleteAll(andId);
    }
}
