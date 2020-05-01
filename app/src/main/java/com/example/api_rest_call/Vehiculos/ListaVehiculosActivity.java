package com.example.api_rest_call.Vehiculos;

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
import com.example.api_rest_call.Services.VehiculoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.api_rest_call.R;

public class ListaVehiculosActivity extends AppCompatActivity {

    ListView listView;
    ListAdapter adaptador;
    ArrayList<Vehiculo> vehiculos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        setTitle("Listado de Autos");

        adaptador = new AutoListAdapter(this, vehiculos);
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
                Vehiculo vehiculo = (Vehiculo) adapterView.getItemAtPosition(i);
                redirectToDetailsView(vehiculo);
            }

            private void redirectToDetailsView(Vehiculo vehiculo) {
                Intent intent = new Intent(ListaVehiculosActivity.this, DetalleVehiculoActivity.class);
                intent.putExtra("id", vehiculo.getId());
                startActivity(intent);
            }
        };
    }

    public void fetchListadoVehiculos() {

        VehiculoService vehiculoService = HTTPServiceBuilder.buildService(VehiculoService.class);
        Call<List<Vehiculo>> http_call = vehiculoService.getVehiculos();

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
                Toast.makeText(ListaVehiculosActivity.this, "Hubo un error leyendo los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
}
