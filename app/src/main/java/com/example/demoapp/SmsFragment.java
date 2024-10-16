package com.example.demoapp;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.SmsAdapter;
import com.example.demoapp.domains.Sms;

import java.util.ArrayList;
import java.util.List;

public class SmsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SmsAdapter adapter;
    private List<Sms> smsList;

    public SmsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        smsList = new ArrayList<>();
        accessSms();
        setupRecyclerView();
        return view;
    }

    private void accessSms() {
        Cursor cursor = getActivity().getContentResolver().query(Telephony.Sms.CONTENT_URI,
                null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                smsList.add(new Sms(address, body));
            }
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Failed to load SMS", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SmsAdapter(smsList);
        recyclerView.setAdapter(adapter);
    }
}
