package com.example.demoapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.CallLogAdapter;
import com.example.demoapp.domains.CallLogEntry;

import java.util.ArrayList;
import java.util.List;

public class CallLogsFragment extends Fragment {
    private RecyclerView recyclerView;
    private CallLogAdapter adapter;
    private List<CallLogEntry> callLogList;

    public CallLogsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_logs, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        callLogList = new ArrayList<>();
        accessCallLogs();
        setupRecyclerView();
        return view;
    }

    private void accessCallLogs() {
        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

                // Get the call type and map it to a readable format
                int typeInt = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String type = getCallTypeString(typeInt);

                // Get call duration and format it
                String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                String formattedDuration = formatDuration(duration);

                // Add entry to the list
                callLogList.add(new CallLogEntry(number, type, formattedDuration));
            }
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Failed to load call logs", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CallLogAdapter(callLogList);
        recyclerView.setAdapter(adapter);
    }

    // Method to convert call type integer to readable string
    private String getCallTypeString(int type) {
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            case CallLog.Calls.REJECTED_TYPE:
                return "Rejected";
            default:
                return "Unknown";
        }
    }

    // Method to format duration from seconds to minutes:seconds
    private String formatDuration(String durationInSeconds) {
        try {
            int seconds = Integer.parseInt(durationInSeconds);
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            return minutes + "m " + remainingSeconds + "s";
        } catch (NumberFormatException e) {
            return "0s";
        }
    }


}
