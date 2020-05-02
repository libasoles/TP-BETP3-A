package com.example.api_rest_call.Vehicles;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api_rest_call.R;
import com.example.api_rest_call.Services.VehicleRepository.VehicleRepository;
import com.google.android.material.textfield.TextInputEditText;

public class CreateVehicleActivity extends AppCompatActivity {

    VehicleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Creación de vehículo");

        repository = new VehicleRepository();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_create_vehicle);
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

        Vehicle vehicle = new Vehicle();
        vehicle.setMarca(marca);
        vehicle.setModelo(modelo);

        repository.create(
                vehicle,
                (Void v) -> redirectToListView(),
                () -> displayError("Hubo un error guardando los datos")
        );

        return true;
    }

    private void redirectToListView() {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
