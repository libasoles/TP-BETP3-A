package com.example.api_rest_call.Vehicles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.api_rest_call.R;
import com.example.api_rest_call.Services.VehicleRepository.VehicleRepository;

public class VehicleListActivity extends AppCompatActivity {

    private ListView listView;
    private ListAdapter viewListAdapter;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private VehicleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setTitle("Listado de vehículos");

        setContentView(R.layout.loading);

        repository = new VehicleRepository();
        this.fetchVehiclesList();
    }

    private void fetchVehiclesList() {
        repository.getAll(
                this::populateList,
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void populateList(List<Vehicle> vehicleList) {
        if (listView == null) {
            setListView();
        }

        vehicles.clear();
        vehicles.addAll(vehicleList);

        ((BaseAdapter) viewListAdapter).notifyDataSetChanged();
    }

    private void setListView() {
        setContentView(R.layout.activity_vehicles_list);

        viewListAdapter = new VehicleViewListAdapter(this, vehicles);
        listView = findViewById(R.id.vehicles_list);
        listView.setAdapter(viewListAdapter);
        listView.setOnItemClickListener(onItemClick());
    }

    private void displayError(String message) {
        Toast.makeText(VehicleListActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return (adapterView, view, i, l) -> {
            Vehicle vehicle = (Vehicle) adapterView.getItemAtPosition(i);
            redirectToDetailsView(vehicle);
        };
    }

    private void redirectToDetailsView(Vehicle vehicle) {
        Intent intent = new Intent(VehicleListActivity.this, VehicleDetailActivity.class);
        intent.putExtra("id", vehicle.getId());
        startActivity(intent);
    }

    public void onCreateButtonClicked(View view) {
        redirectToCreateView();
    }

    private void redirectToCreateView() {
        Intent intent = new Intent(this, CreateVehicleActivity.class);
        startActivity(intent);
    }
}
