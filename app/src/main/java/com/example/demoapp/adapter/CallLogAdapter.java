package com.example.demoapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.domains.CallLogEntry;

import java.util.List;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder> {

    private List<CallLogEntry> callLogList;

    public CallLogAdapter(List<CallLogEntry> callLogList) {
        this.callLogList = callLogList;
    }

    @NonNull
    @Override
    public CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_log, parent, false);
        return new CallLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogViewHolder holder, int position) {
        CallLogEntry callLogEntry = callLogList.get(position);
        holder.number.setText(callLogEntry.getNumber());
        holder.duration.setText(callLogEntry.getDuration());
    }

    @Override
    public int getItemCount() {
        return callLogList.size();
    }

    public static class CallLogViewHolder extends RecyclerView.ViewHolder {
        TextView number, duration;

        public CallLogViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.callLogNumber);
            duration = itemView.findViewById(R.id.callLogDuration);
        }
    }
}

