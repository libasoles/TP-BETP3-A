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

    private Vehicle vehicle;
    private VehicleRepository repository;

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
                this::populateView,
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void populateView(Vehicle vehicle) {
        this.vehicle = vehicle;

        setContentView(R.layout.activity_vehicle_detail);

        TextView brand = findViewById(R.id.brand);
        TextView model = findViewById(R.id.model);

        brand.setText(vehicle.getMarca());
        model.setText(vehicle.getModelo());
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
                .setTitle("Borrar?")
                .setMessage("Por favor confirma que quieres eliminar el modelo " + vehicle.getModelo())
                .setPositiveButton("Borrar", (dialog, whichButton) -> {
                    deleteVehicle();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
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
