package com.rosatom.myvote.controller;

import com.rosatom.myvote.model.entity.FileEntity;
import com.rosatom.myvote.service.FileService;
import com.rosatom.myvote.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/file")
public class FileController {

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileEntity>> findAll(){
        return ResponseEntity.ok(fileService.findAll());
    }

    @DeleteMapping("/{file_id}")
    public ResponseEntity<Void> delete(@PathVariable("file_id") Long fileId){
        fileService.delete(fileId);
        return ResponseEntity.ok().build();
    }
}
