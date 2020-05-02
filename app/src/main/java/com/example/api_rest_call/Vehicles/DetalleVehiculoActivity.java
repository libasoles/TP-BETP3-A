package com.example.api_rest_call.Vehicles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.Services.HTTPServiceBuilder;
import com.example.api_rest_call.Services.VehicleService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.api_rest_call.R;

public class DetalleVehiculoActivity extends AppCompatActivity {

    String id_vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle de vehículo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_vehicle_detail);

        id_vehiculo = getIntent().getStringExtra("id");

        fetchVehiculo();
    }

    public void fetchVehiculo() {
        VehicleService vehicleService = HTTPServiceBuilder.buildService(VehicleService.class);
        Call<Vehicle> http_call = vehicleService.getVehicle(id_vehiculo);

        http_call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                Vehicle vehicle = response.body();

                populateView(vehicle);
            }

            private void populateView(Vehicle vehicle) {
                TextView marca = findViewById(R.id.marca);
                TextView modelo = findViewById(R.id.modelo);

                marca.setText(vehicle.getMarca());
                modelo.setText(vehicle.getModelo());
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Toast.makeText(DetalleVehiculoActivity.this, "Hubo un error leyendo los datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);

        return true;
    }

    public void onEditButtonClick(View view) {
        Intent intent = new Intent(this, EditVehicleActivity.class);
        intent.putExtra("id", id_vehiculo);
        startActivity(intent);
    }
}
