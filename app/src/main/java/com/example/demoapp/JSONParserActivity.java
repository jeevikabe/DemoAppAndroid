package com.example.demoapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class JSONParserActivity extends AppCompatActivity implements View.OnClickListener{
    Button xml,json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparser);
        xml = (Button) findViewById(R.id.btnxml);
        json=(Button) findViewById(R.id.btnjson);
        xml.setOnClickListener(this);
        json.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(xml)){
            Intent intent = new Intent(this,JSONDIsplayActivity.class);
            intent.putExtra("mode",1);
            startActivity(intent);
        }
        else if(view.equals(json)){
            Intent intent = new Intent(this, JSONDIsplayActivity.class);
            intent.putExtra("mode",2);
            startActivity(intent);
        }
    }
}