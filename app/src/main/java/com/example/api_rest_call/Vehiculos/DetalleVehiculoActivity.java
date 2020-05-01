package com.example.api_rest_call.Vehiculos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.R;
import com.example.api_rest_call.Services.HTTPServiceBuilder;
import com.example.api_rest_call.Services.VehiculoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleVehiculoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle de vehículo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.detalle_vehiculo_activity);

        fetchVehiculo();
    }

    public void fetchVehiculo() {
        String id_vehiculo = getIntent().getStringExtra("id");
        VehiculoService vehiculoService = HTTPServiceBuilder.buildService(VehiculoService.class);
        Call<Vehiculo> http_call = vehiculoService.getVehiculo(id_vehiculo);

        http_call.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                Vehiculo vehiculo = response.body();

                populateView(vehiculo);
            }

            private void populateView(Vehiculo vehiculo) {
                TextView marca = findViewById(R.id.marca);
                TextView modelo = findViewById(R.id.modelo);

                marca.setText(vehiculo.getMarca());
                modelo.setText(vehiculo.getModelo());
            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                Toast.makeText(DetalleVehiculoActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, ListaVehiculosActivity.class);
        startActivity(intent);

        return true;
    }
}
