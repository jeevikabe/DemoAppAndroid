package com.example.demoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.domains.ResponseModel;

import java.util.List;

public class ResponseModelAdapter extends RecyclerView.Adapter<ResponseModelAdapter.ViewHolder> {
    private List<ResponseModel> mediaItems;

    public ResponseModelAdapter(List<ResponseModel> mediaItems) {
        this.mediaItems = mediaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseModel item = mediaItems.get(position);
        holder.textViewId.setText(String.valueOf(item.getId()));
        holder.textViewType.setText(item.getType());
        // Add more fields as needed
    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
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
