package com.rosatom.myvote.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfanityEntry {
    private String uncensored;
    private String censored;
    @JsonProperty("original_profane_word")
    private String originalProfaneWord;
    @JsonProperty("bad_words_percent")
    private Double badWordsPercent;
}
