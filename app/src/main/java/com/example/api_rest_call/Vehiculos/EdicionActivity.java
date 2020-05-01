package com.example.api_rest_call.Vehiculos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.R;
import com.example.api_rest_call.Services.HTTPServiceBuilder;
import com.example.api_rest_call.Services.VehiculoService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdicionActivity extends AppCompatActivity {

    Vehiculo vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edición de vehículo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_edicion);

        fetchVehiculo();
    }

    public void fetchVehiculo() {
        String id_vehiculo = getIntent().getStringExtra("id");
        VehiculoService vehiculoService = HTTPServiceBuilder.buildService(VehiculoService.class);
        Call<Vehiculo> http_call = vehiculoService.getVehiculo(id_vehiculo);

        http_call.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                vehiculo = response.body();

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
                Toast.makeText(EdicionActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, ListaVehiculosActivity.class);
        startActivity(intent);

        return true;
    }

    public boolean onCancelButtonClick(View view) {
        finish();
        return true;
    }

    public boolean onSaveButtonClick(View view) {
        VehiculoService vehiculoService = HTTPServiceBuilder.buildService(VehiculoService.class);

        String marca = ((TextInputEditText) findViewById(R.id.marca)).getText().toString();
        String modelo = ((TextInputEditText) findViewById(R.id.modelo)).getText().toString();

        Call<Void> http_call = vehiculoService.updateVehiculo(
                vehiculo.getId(),
                marca,
                modelo
        );

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                redirectToDetailsView();
            }

            private void redirectToDetailsView() {
                Log.i("YES!", "REDIRIGIENDO!");
                Intent intent = new Intent(EdicionActivity.this, DetalleVehiculoActivity.class);
                intent.putExtra("id", vehiculo.getId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EdicionActivity.this, "Hubo un error guardando los datos", Toast.LENGTH_LONG).show();
                Log.i("ERROR", t.getMessage());
            }
        });

        return true;
    }

}
