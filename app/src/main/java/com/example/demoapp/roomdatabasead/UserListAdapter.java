//package com.example.demoapp.roomdatabasead;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.ListAdapter;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.demoapp.R;
//
//public class UserListAdapter extends ListAdapter<User, UserListAdapter.UserViewHolder> {
//
//    protected UserListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
//        super(diffCallback);
//    }
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userss, parent, false);
//        return new UserViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        User current = getItem(position);
//        holder.bind(current.getName(), current.getAge());
//    }
//
//    class UserViewHolder extends RecyclerView.ViewHolder {
//        private final TextView userNameTextView;
//        private final TextView userAgeTextView;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            userNameTextView = itemView.findViewById(R.id.textViewUserName);
//            userAgeTextView = itemView.findViewById(R.id.textViewUserAge);
//        }
//
//        public void bind(String name, int age) {
//            userNameTextView.setText(name);
//            userAgeTextView.setText(String.valueOf(age));
//        }
//    }
//
//    static class UserDiff extends DiffUtil.ItemCallback<User> {
//        @Override
//        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
//            return oldItem.getId() == newItem.getId();
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
//            return oldItem.getName().equals(newItem.getName()) && oldItem.getAge() == newItem.getAge();
//        }
//    }
//}


package com.example.demoapp.roomdatabasead;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;

public class UserListAdapter extends ListAdapter<User, UserListAdapter.UserViewHolder> {

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userss, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User current = getItem(position);
        holder.bind(current.getName(), current.getAge());
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView userNameTextView;
        private final TextView userAgeTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.textViewUserName);
            userAgeTextView = itemView.findViewById(R.id.textViewUserAge);
        }

        public void bind(String name, int age) {
            userNameTextView.setText(name);
            userAgeTextView.setText(String.valueOf(age));
        }
    }

    // Make UserDiff public
    public static class UserDiff extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getAge() == newItem.getAge();
        }
    }
}
