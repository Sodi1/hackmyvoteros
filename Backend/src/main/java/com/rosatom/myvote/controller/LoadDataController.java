package com.rosatom.myvote.controller;

import com.rosatom.myvote.service.LoadDataService;
import com.rosatom.myvote.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/data")
public class LoadDataController {

    private final LoadDataService loadDataService;

    @PostMapping("/upload")
    public ResponseEntity<Void> load(@RequestPart("file") MultipartFile file) {
        loadDataService.save(file);
        return ResponseEntity.ok().build();
    }

}
