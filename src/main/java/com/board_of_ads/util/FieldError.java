package com.board_of_ads.util;

public class FieldError {

    private String field;

    private String text;

    public FieldError(String field, String text) {
        this.field = field;
        this.text = text;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
