package com.example.evermos.demo.controller.response;

import org.springframework.lang.NonNull;

public class BaseResponse<T,M> {
    @NonNull
    private final T data;
    private final M metadata;

    public BaseResponse(T data, M metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public T getData() {
        return data;
    }

    public M getMetadata() {
        return metadata;
    }
}
