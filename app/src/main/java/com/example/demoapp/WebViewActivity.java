package com.example.demoapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webview);

        // Enable JavaScript (optional)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Make sure links and redirects open within the WebView, not a browser
        webView.setWebViewClient(new WebViewClient());

        // Load a webpage
        webView.loadUrl("https://www.google.com");

        // If you want to load HTML content directly
      //  webView.loadData("<html><body>Hello, WebView!</body></html>", "text/html", "UTF-8");
    }

    // Handle back button press within WebView
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();  // If there's a previous page in the WebView history, go back
        } else {
            super.onBackPressed();  // Otherwise, exit the activity
        }
    }
}
