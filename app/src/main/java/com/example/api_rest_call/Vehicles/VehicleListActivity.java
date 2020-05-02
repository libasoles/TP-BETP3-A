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

    ListView listView;
    ListAdapter viewListAdapter;
    ArrayList<Vehicle> vehicles = new ArrayList();
    VehicleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_list);
        setTitle("Listado de vehículos");

        repository = new VehicleRepository();

        setView();

        this.fetchVehiclesList();
    }

    private void setView() {
        viewListAdapter = new VehicleViewListAdapter(this, vehicles);
        listView = findViewById(R.id.vehicles_list);
        listView.setAdapter(viewListAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(onItemClick());
    }

    private void fetchVehiclesList() {
        repository.getAll(
                (List<Vehicle> vehicles) -> populateList(vehicles),
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void populateList(List<Vehicle> vehicleList) {
        vehicles.clear();
        vehicles.addAll(vehicleList);

        ((BaseAdapter) viewListAdapter).notifyDataSetChanged();
    }

    private void displayError(String message) {
        Toast.makeText(VehicleListActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vehicle vehicle = (Vehicle) adapterView.getItemAtPosition(i);
                redirectToDetailsView(vehicle);
            }

            private void redirectToDetailsView(Vehicle vehicle) {
                Intent intent = new Intent(VehicleListActivity.this, VehicleDetailActivity.class);
                intent.putExtra("id", vehicle.getId());
                startActivity(intent);
            }
        };
    }

    public void onCreateButtonClicked(View view) {
        redirectToCreateView();
    }

    private void redirectToCreateView() {
        Intent intent = new Intent(this, CreateVehicleActivity.class);
        startActivity(intent);
    }
}
