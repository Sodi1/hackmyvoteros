package com.rosatom.myvote.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SentenceDto {
    @JsonProperty("source_sentence")
    private String sourceSentence;
    private List<String> sentences;
}
