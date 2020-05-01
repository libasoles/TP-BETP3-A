package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ListAdapter adaptador;
    ArrayList<Vehiculo> vehiculos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehiculos_activity);
        setTitle("Listado de Autos");

        adaptador = new AutoListAdapter(this, vehiculos);
        list = findViewById(android.R.id.list);
        list.setAdapter(adaptador);

        this.fetchListadoVehiculos();
    }

    public void fetchListadoVehiculos() {

        VehiculoService vehiculoService = HTTPServiceBuilder.buildService(VehiculoService.class);
        Call<List<Vehiculo>> http_call = vehiculoService.getAutos();

        http_call.enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {
                if (response.body() == null)
                    return;

                populateList(response.body());
            }

            private void populateList(List<Vehiculo> vehiculoList) {
                vehiculos.clear();
                vehiculos.addAll(vehiculoList);

                ((BaseAdapter) adaptador).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Vehiculo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG).show();
            }
        });
    }
}
