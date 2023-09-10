package com.rosatom.myvote.model.dto;

import jakarta.persistence.SecondaryTable;
import lombok.Data;

@Data
public class LangCheckerRequest {
    private String message;
}
