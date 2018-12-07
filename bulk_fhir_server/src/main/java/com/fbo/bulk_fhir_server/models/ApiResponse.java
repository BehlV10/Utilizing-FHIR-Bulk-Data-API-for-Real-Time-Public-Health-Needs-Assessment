package com.fbo.bulk_fhir_server.models;

public class ApiResponse<T> {

    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
