package com.example.api_rest_call.Services;

public interface OnSuccess<T> {
    void execute(T response);
}
