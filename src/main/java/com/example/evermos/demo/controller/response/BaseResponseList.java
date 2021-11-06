package com.example.evermos.demo.controller.response;

import java.util.List;

public class BaseResponseList<T,M> {
    private final List<T> data;
    private final M metadata;

    public BaseResponseList(List<T> data, M metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public List<T> getData() {
        return data;
    }

    public M getMetadata() {
        return metadata;
    }
}
