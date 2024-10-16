package com.example.demoapp;

import com.example.demoapp.domains.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("posts")
    Call<List<UserModel>> getPosts();

    @PATCH("posts/{id}")
    Call<UserModel> patchPost(@Path("id") int id, @Body UserModel user);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @POST("posts")
    Call<UserModel> createPost(@Body UserModel user);
}
