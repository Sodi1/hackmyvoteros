package com.rosatom.myvote.controller;

import com.rosatom.myvote.model.dto.LangCheckerRequest;
import com.rosatom.myvote.model.dto.LangCheckerResponse;
import com.rosatom.myvote.service.langchecker.LangCheckerService;
import com.rosatom.myvote.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/lang-checker")
public class LangCheckerController {

    private final LangCheckerService langCheckerService;

    @PostMapping
    public ResponseEntity<LangCheckerResponse> check(@RequestBody LangCheckerRequest request){
        return ResponseEntity.ok(langCheckerService.tokenize(request));
    }
}
