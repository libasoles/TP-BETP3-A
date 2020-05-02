package com.example.api_rest_call.Vehicles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.api_rest_call.R;

import java.util.ArrayList;

public class VehicleViewListAdapter extends ArrayAdapter<Vehicle> {
    public VehicleViewListAdapter(Context context, ArrayList<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vehicle vehicle = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_vehicle, parent, false);
        }

        TextView marca = convertView.findViewById(R.id.marca);
        TextView modelo = convertView.findViewById(R.id.modelo);

        marca.setText(vehicle.getMarca());
        modelo.setText(vehicle.getModelo());

        return convertView;
    }
}
