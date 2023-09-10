package com.rosatom.myvote.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class LangCheckerResponse {
    private List<String> tokens;
    private String message;
}
