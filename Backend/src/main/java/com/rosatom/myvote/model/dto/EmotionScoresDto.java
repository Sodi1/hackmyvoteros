package com.rosatom.myvote.model.dto;

import lombok.Data;

@Data
public class EmotionScoresDto {
    private Double neutral;
    private Double positive;
    private Double negative;
}
