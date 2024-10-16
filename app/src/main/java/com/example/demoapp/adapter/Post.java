package com.example.demoapp.adapter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private int id;
    private String title;
    private String body;

    // Constructors
    public Post(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }



    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

