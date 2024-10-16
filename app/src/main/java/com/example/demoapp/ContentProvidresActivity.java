package com.example.demoapp;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//public class ContentProvidresActivity extends AppCompatActivity {
//    private static final int REQUEST_PERMISSIONS = 100;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_content_providres);
//
//        // Check and request permissions if needed
//        checkPermissions();
//
//        if (savedInstanceState == null) {
//            loadFragment(new ContactFragment());
//        }
//
//        // Set up buttons and their click listeners
//        Button btnContacts = findViewById(R.id.buttonContacts);
//        Button btnCallLogs = findViewById(R.id.buttonCallLogs);
//        Button btnSms = findViewById(R.id.buttonSms);
//
//        btnContacts.setOnClickListener(view -> loadFragment(new ContactFragment()));
//        btnCallLogs.setOnClickListener(view -> loadFragment(new CallLogsFragment()));
//        btnSms.setOnClickListener(view -> loadFragment(new SmsFragment()));
//    }
//
//    // Load the given fragment into the container
//    private void loadFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(null)
//                .commit();
//    }
//
//    // Check if the required permissions are granted, otherwise request them
//    private void checkPermissions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//
//            // Request necessary permissions
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS},
//                    REQUEST_PERMISSIONS);
//        }
//    }
//
//    // Handle the result of the permission request
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_PERMISSIONS) {
//            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                // Permissions not granted; you can show a message to the user
//                // For example, using Toast:
//                // Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//}




import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ContentProvidresActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_providres);

        // Check and request permissions if needed
        checkPermissions();

        // Setup ViewPager and TabLayout
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Set up buttons and their click listeners
        Button btnContacts = findViewById(R.id.buttonContacts);
        Button btnCallLogs = findViewById(R.id.buttonCallLogs);
        Button btnSms = findViewById(R.id.buttonSms);

        btnContacts.setOnClickListener(view -> loadFragment(new ContactFragment()));
        btnCallLogs.setOnClickListener(view -> loadFragment(new CallLogsFragment()));
        btnSms.setOnClickListener(view -> loadFragment(new SmsFragment()));
    }

    // Load the given fragment into the container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContactFragment(), "Contacts");
        adapter.addFragment(new CallLogsFragment(), "Call Logs");
        adapter.addFragment(new SmsFragment(), "SMS");
        viewPager.setAdapter(adapter);
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        MyPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    // Check if the required permissions are granted, otherwise request them
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            // Request necessary permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS},
                    REQUEST_PERMISSIONS);
        }
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permissions not granted; you can show a message to the user
                // For example, using Toast:
                // Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
