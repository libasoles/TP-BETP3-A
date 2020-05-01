package com.example.api_rest_call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VehiculoService {

    String API_ROUTE = "/app/api/read";

    @GET(API_ROUTE)
    Call<List<Vehiculo>> getAutos();
}
