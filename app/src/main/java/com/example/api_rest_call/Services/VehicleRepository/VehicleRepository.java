package com.example.api_rest_call.Services.VehicleRepository;

import android.util.Log;

import com.example.api_rest_call.Services.HTTPServiceBuilder;
import com.example.api_rest_call.Services.VehicleHTTPService;
import com.example.api_rest_call.Vehicles.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRepository {
    VehicleHTTPService vehicleHTTPService;

    public VehicleRepository() {
        vehicleHTTPService = HTTPServiceBuilder.buildService(VehicleHTTPService.class);
    }

    public void getAll(OnSuccess<List<Vehicle>> onSuccessCallback, OnError onErrorCallback) {
        Call<List<Vehicle>> http_call = vehicleHTTPService.getVehicles();

        http_call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.body() == null)
                    return;

                onSuccessCallback.execute(response.body());
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Log.i("HTTP ERROR", t.getMessage());
                onErrorCallback.execute();
            }
        });
    }

    public void getById(String id, OnSuccess<Vehicle> onSuccessCallback, OnError onErrorCallback) {
        Call<Vehicle> http_call = vehicleHTTPService.getVehicle(id);

        http_call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                Vehicle vehicle = response.body();
                onSuccessCallback.execute(vehicle);
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Log.i("HTTP ERROR", t.getMessage());
                onErrorCallback.execute();
            }
        });
    }

    public void delete(String id, OnSuccess<Void> onSuccessCallback, OnError onErrorCallback) {
        Call<Void> http_call = vehicleHTTPService.deleteVehicle(id);

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onSuccessCallback.execute(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("HTTP ERROR", t.getMessage());
                onErrorCallback.execute();
            }
        });
    }
}
