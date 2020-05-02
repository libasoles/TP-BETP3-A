package com.example.api_rest_call.Vehicles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.R;
import com.example.api_rest_call.Services.VehicleRepository.VehicleRepository;
import com.google.android.material.textfield.TextInputEditText;

public class EditVehicleActivity extends AppCompatActivity {

    Vehicle vehicle;
    VehicleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edición de vehículo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.loading);

        repository = new VehicleRepository();
        String vehicle_id = getIntent().getStringExtra("id");
        fetchVehicle(vehicle_id);
    }

    private void fetchVehicle(String id) {
        repository.getById(
                id,
                (Vehicle vehicle) -> populateView(vehicle),
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void populateView(Vehicle vehicle) {
        this.vehicle = vehicle;

        setContentView(R.layout.activity_vehicle_edition);

        TextView marca = findViewById(R.id.marca);
        TextView modelo = findViewById(R.id.modelo);

        marca.setText(vehicle.getMarca());
        modelo.setText(vehicle.getModelo());
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
        String marca = ((TextInputEditText) findViewById(R.id.marca)).getText().toString();
        String modelo = ((TextInputEditText) findViewById(R.id.modelo)).getText().toString();

        vehicle.setMarca(marca);
        vehicle.setModelo(modelo);

        repository.update(
                vehicle,
                (Void v) -> redirectToDetailsView(),
                () -> displayError("Hubo un error guardando los datos")
        );

        return true;
    }

    private void redirectToDetailsView() {
        Intent intent = new Intent(this, VehicleDetailActivity.class);
        intent.putExtra("id", vehicle.getId());
        startActivity(intent);
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
