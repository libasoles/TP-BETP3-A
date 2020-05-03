package com.example.api_rest_call.Vehicles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.api_rest_call.R;

import java.util.ArrayList;

class VehicleViewListAdapter extends ArrayAdapter<Vehicle> {
    public VehicleViewListAdapter(Context context, ArrayList<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vehicle vehicle = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_vehicle, parent, false);
        }

        TextView brand = convertView.findViewById(R.id.brand);
        TextView model = convertView.findViewById(R.id.model);

        brand.setText(vehicle.getMarca());
        model.setText(vehicle.getModelo());

        return convertView;
    }
}
