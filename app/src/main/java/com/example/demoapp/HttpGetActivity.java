package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.UserAdapter;
import com.example.demoapp.domains.UserModel;
import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.network.APIServer;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpGetActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserModel> userList;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Initialize the user list
        userList = new ArrayList<>();

        // Call the GET method to fetch data
        getDataFromServerUsingHttp();
    }

    private void getDataFromServerUsingHttp() {
        String url = "https://jsonplaceholder.typicode.com/posts"; // The URL for the GET request

        new APIServer(this).getString(url, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == 200) {
                    try {
                        if (!response.isEmpty())
                        {
                            userList.clear(); // Clear the existing list before adding new data
                            Type type = new TypeToken<ArrayList<UserModel>>()
                            {
                            }.getType();
                            userList.addAll(new GsonBuilder().create().fromJson(response, type));

                            if (!userList.isEmpty())
                            {
                                userAdapter = new UserAdapter(userList, HttpGetActivity.this);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setItemViewCacheSize(userList.size());
                                recyclerView.setAdapter(userAdapter);
                            }
                            else
                            {
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                        // Set up the adapter with the fetched data

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("HttpGetActivity", "JSON parsing error: " + e.getMessage());
                    }

                } else {
                    Log.e("HttpGetActivity", "Failed to fetch data, Status Code: " + statusCode);
                }
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
                    Toast.makeText(HttpGetActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HttpGetActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                    Log.e("HttpGetActivity", "Delete item failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(HttpGetActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                Log.e("HttpGetActivity", "Delete item failed", t);
            }
        });
    }
}
