package com.board_of_ads.util;

public class SuccessResponse<T> extends Response<T> {

    private T data;

    public SuccessResponse() {
        this.success = true;
    }

    public SuccessResponse(T data) {
        this.success = true;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
