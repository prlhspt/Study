package com.prlhspt.market.jwt.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prlhspt.market.web.dto.CustomFieldError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorDto {

    private final int status;
    private final String message;
    private List<CustomFieldError> fieldErrors = new ArrayList<>();

    ObjectMapper mapper = new ObjectMapper();

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String fieldName, Object rejectValue, String message) {
        CustomFieldError error = new CustomFieldError(fieldName, rejectValue, message);
        fieldErrors.add(error);
    }

    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public String convertToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
