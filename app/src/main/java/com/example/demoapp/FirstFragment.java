package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.StudentDataAdapter;
import com.example.demoapp.domains.StudentDataResponse;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    StudentDataAdapter studentDataAdapter;
    List<StudentDataResponse> studentDataResponseArrayList;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Use the correct list returned from createDummyData()
        studentDataResponseArrayList = createDummyData();

        studentDataAdapter = new StudentDataAdapter(getActivity(), studentDataResponseArrayList);
        recyclerView.setAdapter(studentDataAdapter);

        return view;
    }

    private List<StudentDataResponse> createDummyData() {
        List<StudentDataResponse> studentDataResponseArrayList1 = new ArrayList<>();
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Aryan", "101"));
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Jeevika", "102"));
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Ram", "103"));
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Sam", "104"));
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Sita", "105"));
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Thanu", "106"));
        studentDataResponseArrayList1.add(new StudentDataResponse("10", "Varun", "107"));
        return studentDataResponseArrayList1;
    }

}