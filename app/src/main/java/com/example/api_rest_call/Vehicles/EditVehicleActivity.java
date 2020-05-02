package com.example.api_rest_call.Vehicles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.R;
import com.example.api_rest_call.Services.HTTPServiceBuilder;
import com.example.api_rest_call.Services.VehicleHTTPService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditVehicleActivity extends AppCompatActivity {

    Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edición de vehículo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_vehicles_edition);

        fetchVehiculo();
    }

    public void fetchVehiculo() {
        String id_vehiculo = getIntent().getStringExtra("id");
        VehicleHTTPService vehicleHTTPService = HTTPServiceBuilder.buildService(VehicleHTTPService.class);
        Call<Vehicle> http_call = vehicleHTTPService.getVehicle(id_vehiculo);

        http_call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                vehicle = response.body();

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
                Toast.makeText(EditVehicleActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);

        return true;
    }

    public boolean onCancelButtonClick(View view) {
        finish();
        return true;
    }

    public boolean onSaveButtonClick(View view) {
        VehicleHTTPService vehicleHTTPService = HTTPServiceBuilder.buildService(VehicleHTTPService.class);

        String marca = ((TextInputEditText) findViewById(R.id.marca)).getText().toString();
        String modelo = ((TextInputEditText) findViewById(R.id.modelo)).getText().toString();

        Call<Void> http_call = vehicleHTTPService.updateVehicle(
                vehicle.getId(),
                marca,
                modelo
        );

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                redirectToDetailsView();
            }

            private void redirectToDetailsView() {
                Log.i("YES!", "REDIRIGIENDO!");
                Intent intent = new Intent(EditVehicleActivity.this, VehicleDetailActivity.class);
                intent.putExtra("id", vehicle.getId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditVehicleActivity.this, "Hubo un error guardando los datos", Toast.LENGTH_LONG).show();
                Log.i("ERROR", t.getMessage());
            }
        });

        return true;
    }

}
