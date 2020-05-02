package com.example.api_rest_call.Vehicles;

import androidx.appcompat.app.AlertDialog;
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

    Vehicle vehicle;
    VehicleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle de vehículo");

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
        intent.putExtra("id", vehicle.getId());
        startActivity(intent);
    }

    public void onDeleteButtonClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Please confirm you want to delete " + vehicle.getModelo())
                .setPositiveButton("Delete", (dialog, whichButton) -> {
                    deleteVehicle();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteVehicle() {
        repository.delete(
                vehicle.getId(),
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
