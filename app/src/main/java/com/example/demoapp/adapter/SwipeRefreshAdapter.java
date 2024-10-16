package com.example.demoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.R;
import com.example.demoapp.domains.SwipeRefresh;
import java.util.List;

public class SwipeRefreshAdapter extends RecyclerView.Adapter<SwipeRefreshAdapter.ViewHolder> {

    private final List<SwipeRefresh> animalList;

    public SwipeRefreshAdapter(List<SwipeRefresh> animalList) {
        this.animalList = animalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SwipeRefresh animal = animalList.get(position);
        holder.animalName.setText(animal.getName());
        holder.animalImage.setImageResource(animal.getImageResId());
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void removeItem(int position) {
        animalList.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView animalImage;
        TextView animalName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animalImage);
            animalName = itemView.findViewById(R.id.animalName);
        }
    }
}
