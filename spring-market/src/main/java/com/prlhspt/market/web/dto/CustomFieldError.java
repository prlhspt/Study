package com.prlhspt.market.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomFieldError {
    private String fieldName;
    private Object rejectValue;
    private String message;
}
