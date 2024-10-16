package com.example.demoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.domains.LoadOnScrollResult;

import java.util.List;

public class LoadOnScrollAdapter extends RecyclerView.Adapter<LoadOnScrollAdapter.ViewHolder> {
    private List<LoadOnScrollResult> loadOnScrollResultList;

    public LoadOnScrollAdapter(List<LoadOnScrollResult> loadOnScrollResultList) {
        this.loadOnScrollResultList = loadOnScrollResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_on_scroll_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoadOnScrollResult item = loadOnScrollResultList.get(position);
        holder.textViewId.setText(String.valueOf(item.getId()));
        holder.textViewType.setText(item.getType());
        // Add more fields as needed
    }

    @Override
    public int getItemCount() {
        return loadOnScrollResultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.text_view_id);
            textViewType = itemView.findViewById(R.id.text_view_type);
        }
    }
}
