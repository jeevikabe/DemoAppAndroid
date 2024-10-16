package com.example.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;
import com.example.demoapp.domains.StudentDataResponse;

import java.util.List;

public class StudentDataAdapter extends RecyclerView.Adapter<StudentDataAdapter.ViewHolder> {
    private final List<StudentDataResponse> studentDataResponseList;
    private final Context context;


    public StudentDataAdapter(Context context, List<StudentDataResponse> studentDataResponseList) {
        this.context = context;
        this.studentDataResponseList = studentDataResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_data_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StudentDataResponse studentDataResponse = studentDataResponseList.get(position);

        holder.className.setText(studentDataResponse.getClassName());
        holder.rollNo.setText(studentDataResponse.getRollNo());
        holder.name.setText(studentDataResponse.getName());

    }

    @Override
    public int getItemCount() {
        return studentDataResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,className,rollNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTV);
            className = itemView.findViewById(R.id.classTV);
            rollNo = itemView.findViewById(R.id.rollNoTV);


        }
    }
}
