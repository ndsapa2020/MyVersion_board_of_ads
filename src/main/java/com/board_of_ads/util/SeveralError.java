package com.board_of_ads.util;

import java.util.List;
import java.util.Map;

public class SeveralError extends Error {

    private List<FieldError> errors;

    public SeveralError(Integer code, String text, List<FieldError> fieldErrors) {
        super(code, text);
        this.errors = fieldErrors;
    }

    public SeveralError(Integer code, String text, Map<String, Object> meta, List<FieldError> fieldErrors) {
        super(code, text, meta);
        this.errors = fieldErrors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }
}
