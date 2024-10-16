package com.example.demoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.domains.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userList;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(List<UserModel> userList, OnItemClickListener onItemClickListener) {
        this.userList = userList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if (position >= 0 && position < userList.size()) {
            UserModel user = userList.get(position);
            holder.title.setText(user.getTitle());
            holder.body.setText(user.getBody());
            holder.btnDelete.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(user, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < userList.size()) {
            userList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, userList.size()); // Notify that items have moved
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView title, body;
        Button btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            body = itemView.findViewById(R.id.bodyTextView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserModel user, int position);
    }
}
