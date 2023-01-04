package com.example.store.exception;

import com.example.store.response.ResponseJson;

public class ServiceException extends RuntimeException {
    private ResponseJson.State state;

    public ServiceException() {
    }

    public ServiceException(ResponseJson.State state, String message) {
        super(message);
        this.state = state;
    }

    public ResponseJson.State getState() {
        return state;
    }
}
