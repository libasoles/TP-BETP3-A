package com.example.api_rest_call.Services.VehicleRepository;

public interface OnSuccess<T> {
    void execute(T response);
}
