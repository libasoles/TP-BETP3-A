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

import com.example.api_rest_call.Services.HTTPServiceBuilder;
import com.example.api_rest_call.Services.VehicleService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.api_rest_call.R;

public class VehicleListActivity extends AppCompatActivity {

    ListView listView;
    ListAdapter adaptador;
    ArrayList<Vehicle> vehicles = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_list);
        setTitle("Listado de vehículos");

        adaptador = new VehicleViewListAdapter(this, vehicles);
        listView = findViewById(R.id.vehicles_list);
        listView.setAdapter(adaptador);
        listView.setClickable(true);

        listView.setOnItemClickListener(onItemClick());

        this.fetchListadoVehiculos();
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

    public void fetchListadoVehiculos() {

        VehicleService vehicleService = HTTPServiceBuilder.buildService(VehicleService.class);
        Call<List<Vehicle>> http_call = vehicleService.getVehicles();

        http_call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.body() == null)
                    return;

                populateList(response.body());
            }

            private void populateList(List<Vehicle> vehicleList) {
                vehicles.clear();
                vehicles.addAll(vehicleList);

                ((BaseAdapter) adaptador).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast.makeText(VehicleListActivity.this, "Hubo un error leyendo los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
}
