package com.example.demoapp;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TextSpeechActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener{
    EditText txt_speech;
    Button btnspeech;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_speech);
        txt_speech=(EditText)findViewById(R.id.txt_speech);
        btnspeech=(Button)findViewById(R.id.btn_speech);
        btnspeech.setOnClickListener(this);
        tts=new TextToSpeech(getBaseContext(),this);
        tts.setLanguage(Locale.ENGLISH);
    }

    @Override
    public void onClick(View view) {
        String text=txt_speech.getText().toString();
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void onInit(int i) {
        if (i!=TextToSpeech.ERROR){
            //Toast.makeText(getBaseContext(),"Success", Toast.LENGTH_LONG).show();
        }
    }
}
