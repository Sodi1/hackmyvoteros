package com.rosatom.myvote.controller;


import com.rosatom.myvote.model.entity.AnswerEntity;
import com.rosatom.myvote.service.AnswerService;
import com.rosatom.myvote.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/answer")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping
    public ResponseEntity<List<AnswerEntity>> findAll(@RequestParam("file_id") Long fileId, @RequestParam(value = "question_ids", required = false) List<Long> questionIds){
        return ResponseEntity.ok(answerService.findAllByFileId(fileId, questionIds));
    }
}
