package com.board_of_ads.util;

public class ErrorResponse<T> extends Response<T> {

    private Error error;

    public ErrorResponse(Error error) {
        this.success = false;
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}