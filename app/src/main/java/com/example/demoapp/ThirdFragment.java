package com.example.demoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdFragment extends Fragment implements View.OnClickListener {

    EditText name, cityname;
    Button button;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        // Initialize views
        name = view.findViewById(R.id.username);
        cityname = view.findViewById(R.id.city);
        button = view.findViewById(R.id.button1);
        // Set click listener on the button
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        String user = name.getText().toString();
        String city = cityname.getText().toString();

        if (user == null || user.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (city == null || city.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter city name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name != null && city != null){
            Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            return;
        }

        // Do something with the input data
        Toast.makeText(getActivity(), "Username: " + user + "\nCity: " + city, Toast.LENGTH_SHORT).show();
    }
}
