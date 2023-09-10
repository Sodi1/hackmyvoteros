package com.rosatom.myvote.service;

import com.rosatom.myvote.model.entity.QuestionEntity;
import com.rosatom.myvote.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionEntity> findAll(Long fileId){
        return questionRepository.findAllByFileId(fileId);
    }

    public List<QuestionEntity> find(Long fileId, String query) {
        return questionRepository.findByQuery(fileId, query);
    }

    public QuestionEntity findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
}
