//package com.example.demoapp;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.SearchView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.demoapp.adapter.PersonAdapter;
//import com.example.demoapp.domains.Person;
//import com.example.demoapp.interfaces.APIResponseListener;
//import com.google.common.reflect.TypeToken;
//import com.google.gson.GsonBuilder;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//import networkapi.APIServer;
//import networkapi.Links;
//
//public class GetListActivity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    PersonAdapter personAdapter;
//    SearchView searchView;  // Add SearchView
//ArrayList<Person> arrayList=new ArrayList<>();
//    private int limit=5;
//    private int start=0;
//    List<Person> personList = new ArrayList<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_get_list);
//        recyclerView = findViewById(R.id.people_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        searchView = findViewById(R.id.search_view);  // Initialize SearchView
//
//        personAdapter = new PersonAdapter(this, personList);
//        recyclerView.setAdapter(personAdapter);
//
//        // Set a listener on the SearchView
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Handle query submission if needed
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Check if the length of the search term is 2 or more characters
//                if (newText.length() >= 2) {
//                    // Call fetchData with the search term
//                    fetchPeopleList(newText);
//                }
//                return false;
//            }
//        });
//        fetchPeopleList("");
//
//    }
//
//    private void fetchPeopleList(String searchTerm) {
//        APIServer apiServer = new APIServer(this);
//        apiServer.getString(Links.GET_POI_LIST+ "?start=" + start + "&limit=" + limit+ "&searchTerm=" + searchTerm, new APIResponseListener() {
//
//                @Override
//            public void onResponse(int statusCode, String response) {
//                    Log.d("API Response", response);
//                if (statusCode == 200) {
//
//                    Type type = new TypeToken<ArrayList<Person>>() {
//                    }.getType();
//                    List<Person> udrs = new GsonBuilder().create()
//                            .fromJson(response, type);
//                    arrayList.addAll(udrs);
//
//                    personAdapter = new PersonAdapter(GetListActivity.this, arrayList);
//                    recyclerView.setAdapter(personAdapter);
//
//
//                } else {
//                    Log.e("GetListActivity", "Error: " + statusCode);
//                    Toast.makeText(GetListActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
////            @Override
////            public void onError(ANError error) {
////                Log.e("GetListActivity", "API error: " + error.getMessage());
////                Toast.makeText(GetListActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
////            }
//        });
//    }
//
////    private void loadMore(int page, String searchTerm) {
////        start = start + limit;
////        String apiUrl = Links.GET_POI_LIST + "?start=" + start + "&limit=" + limit + "&searchTerm=" + searchTerm;
////        new APIServer(this).getString(apiUrl, new APIResponseListener() {
////            @Override
////            public void onResponse(int statusCode, String response) {
////                if (statusCode == 200) {
////                    try {
////                        // Parse the response into ResultResponse
////                        ResultResponse resultResponse = new Gson().fromJson(response, ResultResponse.class);
////                        List<LoadOnScrollResult> newArrayList = resultResponse.getResult();
////
////                        if (newArrayList != null && !newArrayList.isEmpty()) {
////                            loadOnScrollResultList.addAll(newArrayList);
////                            recyclerView.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    int curSize = adapter.getItemCount();
////                                    adapter.notifyItemRangeInserted(curSize, newArrayList.size());
////                                }
////                            });
////                        }
////                    } catch (JsonSyntaxException e) {
////                        Log.e("LoadActivity", "Error parsing response: " + e.getMessage());
////                    }
////                }
////            }
////        });
////    }
//
//
//
//}
//
//

















package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.PersonAdapter;
import com.example.demoapp.domains.Person;
import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.utils.EndlessRecyclerViewScrollListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import networkapi.APIServer;
import networkapi.Links;

public class GetListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PersonAdapter personAdapter;
    SearchView searchView;  // Add SearchView
    ArrayList<Person> arrayList=new ArrayList<>();
    private int limit=5;
    private int start=0;

    LinearLayoutManager linearLayoutManager;
    List<Person> personList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list);
        recyclerView = findViewById(R.id.people_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search_view);  // Initialize SearchView

        personAdapter = new PersonAdapter(this, personList);
        recyclerView.setAdapter(personAdapter);

        // Initialize LayoutManager and set to RecyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Set a listener on the SearchView
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
                    fetchPeopleList(newText);
                }
                return false;
            }
        });
        fetchPeopleList("");

    }

    private void fetchPeopleList(String searchTerm) {
        APIServer apiServer = new APIServer(this);
        apiServer.getString(Links.GET_POI_LIST+ "?start=" + start + "&limit=" + limit+ "&searchTerm=" + searchTerm, new APIResponseListener() {

            @Override
            public void onResponse(int statusCode, String response) {
                Log.d("API Response", response);
                if (statusCode == 200) {

                    Type type = new TypeToken<ArrayList<Person>>() {
                    }.getType();
                    List<Person> udrs = new GsonBuilder().create()
                            .fromJson(response, type);
                    arrayList.addAll(udrs);
                    personAdapter.notifyDataSetChanged();

                    personAdapter = new PersonAdapter(GetListActivity.this, arrayList);
                    recyclerView.setAdapter(personAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(personAdapter);
                    recyclerView.setVisibility(View.VISIBLE);

                    recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            loadMore(page, searchTerm);
                        }
                    });

                }
                else {
                    Log.e("GetListActivity", "Error: " + statusCode);
                    Toast.makeText(GetListActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

//            @Override
//            public void onError(ANError error) {
//                Log.e("GetListActivity", "API error: " + error.getMessage());
//                Toast.makeText(GetListActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//            }
        });
    }

    private void loadMore(int page, String searchTerm) {
        start = start + limit;
        String apiUrl = Links.GET_POI_LIST + "?start=" + start + "&limit=" + limit + "&searchTerm=" + searchTerm;
        new APIServer(this).getString(apiUrl, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == 200) {
//                    try {
//                        // Parse the response into ResultResponse
//                        ResultResponseGet resultResponse = new Gson().fromJson(response, ResultResponseGet.class);
//                        List<Person> newArrayList = resultResponse.getResult();
//
//                        if (newArrayList != null && !newArrayList.isEmpty()) {
//                            personList.addAll(newArrayList);
//                            recyclerView.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    int curSize = personAdapter.getItemCount();
//                                    personAdapter.notifyItemRangeInserted(curSize, newArrayList.size());
//                                }
//                            });
//                        }
//                    } catch (JsonSyntaxException e) {
//                        Log.e("GetListActivity", "Error parsing response: " + e.getMessage());
//                    }
                    Type type = new TypeToken<ArrayList<Person>>() {
                    }.getType();
                    List<Person> udrs = new GsonBuilder().create()
                            .fromJson(response, type);
                    arrayList.addAll(udrs);
                    personAdapter.notifyDataSetChanged();

                    personAdapter = new PersonAdapter(GetListActivity.this, arrayList);
                    recyclerView.setAdapter(personAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(personAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}



