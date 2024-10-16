package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.LoadOnScrollAdapter;
import com.example.demoapp.domains.LoadOnScrollResult;
import com.example.demoapp.domains.ResultResponse;
import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.utils.EndlessRecyclerViewScrollListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import networkapi.APIServer;
import networkapi.Links;

public class LoadActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LoadOnScrollAdapter adapter;
    private List<LoadOnScrollResult> loadOnScrollResultList;
    SearchView searchView;
    private int limit=20;
    private int start=0;
LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.searchView);
        loadOnScrollResultList = new ArrayList<>();
        adapter = new LoadOnScrollAdapter(loadOnScrollResultList);
        //adapter = new LoadOnScrollAdapter(this, loadOnScrollResultList);

        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Check if the length of the search term is 2 or more characters
                if (newText.length() >= 2) {
                    // Call fetchData with the search term
                    fetchData(newText);
                }
                return false;
            }
        });


        // Fetch data from server
        fetchData("");
    }

    /* private void fetchData() {
         APIServer apiServer = new APIServer(this);

         apiServer.getString(Links.LOAD, new APIResponseListener() {
             @Override
             public void onResponse(int statusCode, String response) {
                 if (statusCode == 200) {
                     Type type = new TypeToken<ArrayList<ResponseModel>>() {}.getType();
                     List<ResponseModel> udrs = new GsonBuilder().create().fromJson(response, type);

                     if (udrs != null) {
                         mediaItems.clear(); // Clear existing items
                         mediaItems.addAll(udrs); // Add new items
                         adapter.notifyDataSetChanged(); // Notify adapter of data change
                     }
                 } else {
                     Log.e("LoadActivity", "Error: " + statusCode);
                     Toast.makeText(LoadActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }*/
    private void fetchData(String searchTerm) {
        APIServer apiServer = new APIServer(this);

        apiServer.getString(Links.LOAD + "?start=" + start + "&limit=" + limit + "&searchTerm=" + searchTerm, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                Log.d("API Response", response); // Log response to check

                if (statusCode == 200) {
                    try {
                        // Parse the response into ResultResponse
                        ResultResponse resultResponse = new Gson().fromJson(response, ResultResponse.class);
                        List<LoadOnScrollResult> loadOnScrollResults = resultResponse.getResult();

                        if (loadOnScrollResults != null && !loadOnScrollResults.isEmpty()) {
                            adapter = new LoadOnScrollAdapter(loadOnScrollResults);
                            //adapter = new LoadOnScrollAdapter(LoadActivity.this, loadOnScrollResultList);

                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setItemViewCacheSize(loadOnScrollResults.size());
                            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                @Override
                                public void onLoadMore(int page, int totalItemsCount) {
                                    loadMore(page, searchTerm);
                                }
                            });
                        } else {
                            recyclerView.setVisibility(View.GONE);
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("LoadActivity", "Error parsing response: " + e.getMessage());
                        Toast.makeText(LoadActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("LoadActivity", "Error: " + statusCode);
                    Toast.makeText(LoadActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void loadMore(int page, String searchTerm) {
        start = start + limit;
        String apiUrl = Links.LOAD + "?start=" + start + "&limit=" + limit + "&searchTerm=" + searchTerm;
        new APIServer(this).getString(apiUrl, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == 200) {
                    try {
                        // Parse the response into ResultResponse
                        ResultResponse resultResponse = new Gson().fromJson(response, ResultResponse.class);
                        List<LoadOnScrollResult> newArrayList = resultResponse.getResult();

                        if (newArrayList != null && !newArrayList.isEmpty()) {
                            loadOnScrollResultList.addAll(newArrayList);
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    int curSize = adapter.getItemCount();
                                    adapter.notifyItemRangeInserted(curSize, newArrayList.size());
                                }
                            });
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("LoadActivity", "Error parsing response: " + e.getMessage());
                    }
                }
            }
        });
    }

}
