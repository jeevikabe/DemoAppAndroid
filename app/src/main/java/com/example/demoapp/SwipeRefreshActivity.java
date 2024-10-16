package com.example.demoapp;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.demoapp.adapter.SwipeRefreshAdapter;
import com.example.demoapp.domains.SwipeRefresh;
import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshAdapter adapter;
    private List<SwipeRefresh> animalList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list with some animals
        animalList = new ArrayList<>();
        populateAnimalList();

        adapter = new SwipeRefreshAdapter(animalList);
        recyclerView.setAdapter(adapter);

       // ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
       // itemTouchHelper.attachToRecyclerView(recyclerView);
        Context context = SwipeRefreshActivity.this;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(context, adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshData();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void populateAnimalList() {
        animalList.add(new SwipeRefresh("Cat", R.drawable.img_3));
        animalList.add(new SwipeRefresh("Dog", R.drawable.img_2));
        animalList.add(new SwipeRefresh("Elephant", R.drawable.img_1));;
        animalList.add(new SwipeRefresh("Lion", R.drawable.img_4));
        animalList.add(new SwipeRefresh("Monkey", R.drawable.img_6));
        animalList.add(new SwipeRefresh("Parrot", R.drawable.img_7));
        animalList.add(new SwipeRefresh("Peacock", R.drawable.img_8));
        animalList.add(new SwipeRefresh("Pigeon", R.drawable.img_9));
        animalList.add(new SwipeRefresh("Tiger", R.drawable.img_5));
        // Add more animals or common pets in India like "Elephant" or "Parrot"
    }

    private void refreshData() {
        adapter.notifyDataSetChanged();
    }
}
