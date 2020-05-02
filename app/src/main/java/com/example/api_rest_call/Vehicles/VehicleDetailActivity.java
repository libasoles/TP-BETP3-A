package com.example.api_rest_call.Vehicles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.Services.VehicleRepository.VehicleRepository;

import com.example.api_rest_call.R;

public class VehicleDetailActivity extends AppCompatActivity {

    String vehicle_id;
    VehicleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle de vehículo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.loading);

        vehicle_id = getIntent().getStringExtra("id");

        repository = new VehicleRepository();

        fetchVehicle();
    }

    private void fetchVehicle() {
        repository.getById(
                vehicle_id,
                (Vehicle vehicle) -> populateView(vehicle),
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void populateView(Vehicle vehicle) {
        setContentView(R.layout.activity_vehicle_detail);

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

    public void onEditButtonClick(View view) {
        Intent intent = new Intent(this, EditVehicleActivity.class);
        intent.putExtra("id", vehicle_id);
        startActivity(intent);
    }

    public void onDeleteButtonClick(View view) {
        repository.delete(
                vehicle_id,
                (Void v) -> redirectToVehiclesList(),
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void redirectToVehiclesList() {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);
    }

    private void displayError(String message) {
        Toast.makeText(VehicleDetailActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
