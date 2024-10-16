package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.demoapp.adapter.ItemAdapter;
import com.example.demoapp.domains.Item;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private Spinner spinner;
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_search);

        spinner = findViewById(R.id.spinner_filter);
        searchEditText = findViewById(R.id.edit_text_search);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        // Add sample items
        itemList.add(new Item("Bagalkot"));
        itemList.add(new Item("Ballari"));
        itemList.add(new Item("Belagavi"));
        itemList.add(new Item("Bengaluru Rural"));
        itemList.add(new Item("Bengaluru Urban"));
        itemList.add(new Item("Bidar"));
        itemList.add(new Item("Chamarajanagar"));
        itemList.add(new Item("Chikkaballapur"));
        itemList.add(new Item("Chikkamagaluru"));
        itemList.add(new Item("Chitradurga"));
        itemList.add(new Item("Dakshina Kannada"));
        itemList.add(new Item("Davanagere"));
        itemList.add(new Item("Dharwad"));
        itemList.add(new Item("Gadag"));
        itemList.add(new Item("Hassan"));
        itemList.add(new Item("Haveri"));
        itemList.add(new Item("Kalaburagi"));
        itemList.add(new Item("Kodagu"));
        itemList.add(new Item("Kolar"));
        itemList.add(new Item("Koppal"));
        itemList.add(new Item("Mandya"));
        itemList.add(new Item("Mysuru"));
        itemList.add(new Item("Raichur"));
        itemList.add(new Item("Ramanagara"));
        itemList.add(new Item("Shivamogga"));
        itemList.add(new Item("Tumakuru"));
        itemList.add(new Item("Udupi"));
        itemList.add(new Item("Uttara Kannada"));
        itemList.add(new Item("Vijayapura"));
        itemList.add(new Item("Yadgir"));


        // Set up the adapter and the click listener
        itemAdapter = new ItemAdapter(itemList, item -> {
            // Set the clicked item name into the search EditText
            searchEditText.setText(item.getName());
            searchEditText.setSelection(item.getName().length()); // Optional: Move cursor to the end
        });
        recyclerView.setAdapter(itemAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterItems(String query) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        itemAdapter.updateList(filteredList);
    }
}




