package com.example.demoapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.ContactAdapter;
import com.example.demoapp.domains.Contact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContactFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        contactList = new ArrayList<>();
        accessContacts();
        setupRecyclerView();
        return view;
    }

    private void accessContacts() {
        // Use a HashSet to track unique contact IDs
        HashSet<String> contactIds = new HashSet<>();
        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Add contact only if it's not already in the HashSet
                if (!contactIds.contains(id)) {
                    contactList.add(new Contact(id, name, phoneNumber));
                    contactIds.add(id);  // Add the contact ID to the HashSet
                }
            }
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Failed to load contacts", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(adapter);
    }
}
