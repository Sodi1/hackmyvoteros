package com.rosatom.myvote.controller;

import com.rosatom.myvote.utils.WebUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello!");
    }

}

