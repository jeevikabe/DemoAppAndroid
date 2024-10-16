package com.example.demoapp.domains;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class StudentDataResponse {

    private String className;
    private String name;
    private String rollNo;

    public StudentDataResponse(String className, String name, String rollNo) {
        this.className = className;
        this.name = name;
        this.rollNo = rollNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}
