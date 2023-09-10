package com.rosatom.myvote.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpellCheckDto {
    @JsonProperty("texts")
    private List<String> texts;
}
