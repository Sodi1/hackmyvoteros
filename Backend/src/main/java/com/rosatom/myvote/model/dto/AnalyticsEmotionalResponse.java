package com.rosatom.myvote.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalyticsEmotionalResponse {
    private List<List<Object>> emotions;
}
