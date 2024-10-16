package com.example.demoapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class StaticDynamicFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    public void show(FragmentManager supportFragmentManager, String dialog) {


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static_dynamic, container, false);

        Button sendDataButton = view.findViewById(R.id.send_data_button);
        sendDataButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFragmentInteraction("Data from Fragment");
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}