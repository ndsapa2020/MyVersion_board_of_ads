package com.board_of_ads.util;

import java.util.Map;

public class Error {
    private Integer code;
    private String text;
    private Map<String, Object> meta;

    public Error(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Error(Integer code, String text, Map<String, Object> meta) {
        this.code = code;
        this.text = text;
        this.meta = meta;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }
}