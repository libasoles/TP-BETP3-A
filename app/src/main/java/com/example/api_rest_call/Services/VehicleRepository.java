package com.example.api_rest_call.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRepository {
    VehicleService vehicleService;

    public VehicleRepository() {
        vehicleService = HTTPServiceBuilder.buildService(VehicleService.class);

    }

    public void delete(String id, OnSuccess<Void> onSuccessCallback, OnError onErrorCallback) {
        Call<Void> http_call = vehicleService.deleteVehicle(id);

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onSuccessCallback.execute(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onErrorCallback.execute();
            }
        });
    }
}
