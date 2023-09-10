package com.rosatom.myvote.service;

import com.rosatom.myvote.model.entity.AnswerEntity;
import com.rosatom.myvote.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<AnswerEntity> findAllByFileId(Long fileId, List<Long> questionIds) {

        return answerRepository.findAllByFileId(fileId, questionIds, questionIds == null ? 0 : questionIds.size());
    }
}
