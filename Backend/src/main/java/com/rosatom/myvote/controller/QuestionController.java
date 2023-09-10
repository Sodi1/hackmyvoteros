package com.rosatom.myvote.controller;


import com.rosatom.myvote.model.entity.QuestionEntity;
import com.rosatom.myvote.service.QuestionService;
import com.rosatom.myvote.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionEntity>> findAll(@RequestParam("file_id") Long fileId){
        return ResponseEntity.ok(questionService.findAll(fileId));
    }

    @GetMapping("/find")
    public ResponseEntity<List<QuestionEntity>> find(@RequestParam("file_id") Long fileId, @RequestParam("query") String query){
        return ResponseEntity.ok(questionService.find(fileId, query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionEntity> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(questionService.findById(id));
    }
}
