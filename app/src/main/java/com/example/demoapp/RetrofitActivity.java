package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.UserAdapter;
import com.example.demoapp.domains.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<UserModel>> call = apiInterface.getPosts();

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body();
                    userAdapter = new UserAdapter(userList, RetrofitActivity.this);
                    recyclerView.setAdapter(userAdapter);
                } else {
                    Log.e("RetrofitActivity", "Response unsuccessful or empty body");
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.e("RetrofitActivity", "Fetch data failed", t);
            }
        });
    }

    @Override
    public void onItemClick(UserModel user, int position) {
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<Void> call = apiInterface.deletePost(user.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userAdapter.removeItem(position);
                    Toast.makeText(RetrofitActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RetrofitActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                    Log.e("RetrofitActivity", "Delete item failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                Log.e("RetrofitActivity", "Delete item failed", t);
            }
        });
    }
}
