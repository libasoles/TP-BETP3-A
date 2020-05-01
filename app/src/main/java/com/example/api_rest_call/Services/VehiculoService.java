package com.example.api_rest_call.Services;

import com.example.api_rest_call.Vehiculos.Vehiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VehiculoService {

    String BASE_ENDPOINT = "/app/api";

    @GET(BASE_ENDPOINT + "/read")
    Call<List<Vehiculo>> getVehiculos();

    @GET(BASE_ENDPOINT + "/read/{id}")
    Call<Vehiculo> getVehiculo(@Path("id") String id);

    @FormUrlEncoded
    @PUT(BASE_ENDPOINT + "/update/{id}")
    Call<Void> updateVehiculo(
            @Path("id") String id,
            @Field("marca") String marca,
            @Field("modelo") String modelo
    );
}
