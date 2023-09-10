package com.rosatom.myvote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmotionalPercentageByQuestion {
    private String name;
    private Double percentNegative;
    private Double percentPositive;
    private Double percentNeutral;
    private Integer count;
}
