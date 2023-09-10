package com.rosatom.myvote.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TopPositiveQuestionDto {
    Map<String, Integer> questions;
}
