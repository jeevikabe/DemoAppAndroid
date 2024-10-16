//package com.example.demoapp;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.model.GlideUrl;
//
//import networkapi.APIServer;
//import networkapi.Links;
//
//public class ApiImageActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_api_image);
//
//        ImageView imageView = findViewById(R.id.imageView);
//
//        // Make sure to initialize the Links class somewhere before using the URLs
//        Links.setLinks(this);
//
//        //String imageUrl = Links.SHOW_IMAGE;
//
//        // Create an instance of ApiServer
//        APIServer apiServer = new APIServer(this);
//
//        // Use the loadImageFromServer method from ApiServer
//       // GlideUrl glideUrl = apiServer.loadImageFromServer(imageUrl);
//
//        // Load the image using Glide
//        Glide.with(this)
//               // .load(glideUrl)
//               // .into(imageView);
//    }
//}
