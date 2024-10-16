package com.example.demoapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FragmentSDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_sdactivity);
//// In your activity or fragment where you want to show the dialog
//        DiFragment dialog = new DiFragment();
//        dialog.show(getSupportFragmentManager(), "dialog");
//
//        // Static Fragment in XML
//        // To dynamically add the fragment, uncomment the following lines:
//         StaticDynamicFragment fragment = new StaticDynamicFragment();
//         FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//         fragmentTransaction.add(R.id.fragment_container, fragment);
//         fragmentTransaction.commit();
//
//        Button openDialogButton = findViewById(R.id.open_dialog_button);
//        openDialogButton.setOnClickListener(v -> {
////            StaticDynamicFragment dialog = new StaticDynamicFragment();
////            dialog.show(getSupportFragmentManager(), "dialog");
//        });
//    }
//
//    @Override
//    public void onFragmentInteraction(String data) {
//        // Handle the data received from the Fragment
//        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
//    }
//}
        Button openDialogButton = findViewById(R.id.open_dialog_button);
        openDialogButton.setOnClickListener(v -> {
            DiFragment dialog = new DiFragment();
            dialog.show(getSupportFragmentManager(), "dialog");
        });
    }
}