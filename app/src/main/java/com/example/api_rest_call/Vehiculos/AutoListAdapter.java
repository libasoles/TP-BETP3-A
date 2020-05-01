package com.example.api_rest_call.Vehiculos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.api_rest_call.R;

import java.util.ArrayList;

public class AutoListAdapter extends ArrayAdapter<Vehiculo> {
    public AutoListAdapter(Context context, ArrayList<Vehiculo> vehiculos) {
        super(context, 0, vehiculos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vehiculo vehiculo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_vehiculo, parent, false);
        }

        TextView marca = convertView.findViewById(R.id.marca);
        TextView modelo = convertView.findViewById(R.id.modelo);

        marca.setText(vehiculo.getMarca());
        modelo.setText(vehiculo.getModelo());

        return convertView;
    }
}
