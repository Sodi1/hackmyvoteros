package com.rosatom.myvote.service;

import com.rosatom.myvote.model.entity.FileEntity;
import com.rosatom.myvote.repository.AnswerRepository;
import com.rosatom.myvote.repository.FileRepository;
import com.rosatom.myvote.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FileService {
    private final FileRepository fileRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    public FileEntity save(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    public void delete(Long fileId) {
        questionRepository.deleteByFileId(fileId);
        answerRepository.deleteByFileId(fileId);
        fileRepository.deleteById(fileId);
    }
}
