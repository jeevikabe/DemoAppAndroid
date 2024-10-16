package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.interfaces.APIResponseListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

import networkapi.APIServer;
import networkapi.Links;

public class TopicsActivity extends AppCompatActivity {

    private String refreshedFirebaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        refreshedFirebaseToken = task.getResult();
//                        handleFCMToken();
                        if(refreshedFirebaseToken != null){
                            sendFCMTokenToServer(refreshedFirebaseToken);
                        }
                    }
                });



        RadioGroup radioGroupLanguages = findViewById(R.id.radioGroupLanguage);
        RadioButton radioEnglish = findViewById(R.id.radioEnglish);
        RadioButton radioKannada = findViewById(R.id.radioKannada);

        // Load saved language preference
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedLanguage = sharedPreferences.getString("language", "English");
        updateButtonTexts(savedLanguage);
        if (savedLanguage.equals("Kannada")) {
            radioKannada.setChecked(true);
        } else {
            radioEnglish.setChecked(true);
        }

        // Set listener for language selection
        radioGroupLanguages.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioEnglish) {
                    saveLanguagePreference("English");
                    updateButtonTexts("English");
                } else if (checkedId == R.id.radioKannada) {
                    saveLanguagePreference("Kannada");
                    updateButtonTexts("Kannada");
                }
            }
        });











        Button btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, MainActivity.class);
                startActivity(in);
            }
        });

        Button btnmultimedia = findViewById(R.id.btn2);
        btnmultimedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, Multimedia.class);
                startActivity(in);
            }
        });

        Button btnmap = findViewById(R.id.btn3);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, MapActivity.class);
                startActivity(in);
            }
        });

        Button btnalert = findViewById(R.id.btn4);
        btnalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, AlertDialogActivity.class);
                startActivity(in);
            }
        });

        Button btnsearch = findViewById(R.id.btn5);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, RecyclerViewSearchActivity.class);
                startActivity(in);
            }
        });

        Button btnmenu = findViewById(R.id.btn6);
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, AndroidMenuActivity.class);
                startActivity(in);
            }
        });

        Button btntext = findViewById(R.id.btn7);
        btntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, TextSpeechActivity.class);
                startActivity(in);
            }
        });

        Button btnweb = findViewById(R.id.btn8);
        btnweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, WebViewActivity.class);
                startActivity(in);
            }
        });

        Button btnlogout = findViewById(R.id.btn9);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

        Button btnretro = findViewById(R.id.btn10);
        btnretro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, RetrofitGetPostActivity.class);
                startActivity(in);
            }
        });
        Button btnswipe = findViewById(R.id.btn11);
        btnswipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,SwipeRefreshActivity.class);
                startActivity(in);
            }
        });

        Button btnalarm = findViewById(R.id.btn12);
        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, AlaramActivity.class);
                startActivity(in);
            }
        });

        Button btnroom = findViewById(R.id.btn13);
        btnroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,RoomDatabaseActivity.class);
                startActivity(in);
            }
        });
        Button btnparser = findViewById(R.id.btnJSON);
        btnparser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,JSONParserActivity.class);
                startActivity(in);
            }
        });

        Button btnslider = findViewById(R.id.btnCarouselslider);
        btnslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,CarouselsliderActivity.class);
                startActivity(in);
            }
        });

        Button btnglide = findViewById(R.id.btnGlide);
        btnglide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,GlideActivity.class);
                startActivity(in);
            }
        });

        Button btnapi = findViewById(R.id.btnapi);
        btnapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,ApiActivity.class);
                startActivity(in);
            }
        });
        Button btnvideo = findViewById(R.id.btnvideo);
        btnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,VideoActivity.class);
                startActivity(in);
            }
        });

        Button btnpdf = findViewById(R.id.btnpdf);
        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,FileUploadActivity.class);
                startActivity(in);
            }
        });
        Button btnaud = findViewById(R.id.btnaudio);
        btnaud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,AudioUploadActivity.class);
                startActivity(in);
            }
        });
        Button btnscan = findViewById(R.id.btnscan);
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,ScanActivity.class);
                startActivity(in);
            }
        });
        Button btnzip = findViewById(R.id.btnzip);
        btnzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,ZipFileActivity.class);
                startActivity(in);
            }
        });
        Button btnsdb = findViewById(R.id.btndb);
        btnsdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,DbSqlActivity.class);
                startActivity(in);
            }
        });
        Button btnload = findViewById(R.id.btnload);
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,LoadActivity.class);
                startActivity(in);
            }
        });

        Button btnfire = findViewById(R.id.btnfire);
        btnfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, RealTimeFirebaseActivity.class);
                startActivity(in);
            }
        });

        Button btncrash = findViewById(R.id.btncrash);
        btncrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, CrashActivity.class);
                startActivity(in);
            }
        });
        Button btnmapapi = findViewById(R.id.btnmapapi);
        btnmapapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, ApiMapActivity.class);
                startActivity(in);
            }
        });
        Button btnlocal = findViewById(R.id.btnlocal);
        btnlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, LocalBroadcastActivity.class);
                startActivity(in);
            }
        });

        Button btncontent = findViewById(R.id.btncontent);
        btncontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, ContentProvidresActivity.class);
                startActivity(in);
            }
        });
        Button btnotp = findViewById(R.id.btnotp);
        btnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, AuthenticationOtpActivity.class);
                startActivity(in);
            }
        });

        Button btnbio = findViewById(R.id.btnbio);
        btnbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, BiosyncActivity.class);
                startActivity(in);
            }
        });

        Button btnnav = findViewById(R.id.btnnavgraph);
        btnnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, NavGraphActivity.class);
                startActivity(in);
            }
        });

        Button btnanimation = findViewById(R.id.btnanimation);
        btnanimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, AnimationActivity.class);
                startActivity(in);
            }
        });

        Button btnauto = findViewById(R.id.btnauto);
        btnauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this,AutoCompleteActivity.class);
                startActivity(in);
            }
        });

        Button btnmodern = findViewById(R.id.btnmodern);
        btnmodern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, ModernUiActivity.class);
                startActivity(in);
            }
        });


        Button btndraw = findViewById(R.id.btndraw);
        btndraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, DrawingMapActivity.class);
                startActivity(in);
            }
        });
        Button btncircle = findViewById(R.id.btncircle);
        btncircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, CircleActivity.class);
                startActivity(in);
            }
        });

        Button btnimpli = findViewById(R.id.btnimplicit);
        btnimpli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, CallActivity.class);
                startActivity(in);
            }
        });
        Button btnud = findViewById(R.id.btnud);
        btnud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, FileActivity.class);
                startActivity(in);
            }
        });


        Button btnfr = findViewById(R.id.btnfr);
        btnfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, FragmentSDActivity.class);
                startActivity(in);
            }
        });

        Button btnmen = findViewById(R.id.btnmenu);
        btnmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, MenuActivity.class);
                startActivity(in);
            }
        });

        Button btnjs = findViewById(R.id.btnjs);
        btnjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, JsonParserJsonActivity.class);
                startActivity(in);
            }
        });

        Button btnauth = findViewById(R.id.btnauth);
        btnauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, AuthenticateFirebaseActivity.class);
                startActivity(in);
            }
        });

        Button btnnot = findViewById(R.id.btnnot);
        btnnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, NotificationAllActivity.class);
                startActivity(in);
            }
        });

        Button btnsms = findViewById(R.id.btnsms);
        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, SendingSMSActivity.class);
                startActivity(in);
            }
        });


        Button btngeo = findViewById(R.id.btngeo);
        btngeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, GeococdingActivity.class);
                startActivity(in);
            }
        });


        Button btnpermission = findViewById(R.id.btnpermission);
        btnpermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, PermissionActivity.class);
                startActivity(in);
            }
        });


        Button btnsim = findViewById(R.id.btnsim);
        btnsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, SimCardActivity.class);
                startActivity(in);
            }
        });

        Button btnfea = findViewById(R.id.btnfea);
        btnfea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, MapFeatureActivity.class);
                startActivity(in);
            }
        });

        Button btnphone = findViewById(R.id.btnphone);
        btnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, FirePhoneActivity.class);
                startActivity(in);
            }
        });
        Button btndataf = findViewById(R.id.btndataf);
        btndataf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, FragmentDataActivity.class);
                startActivity(in);
            }
        });



        Button btnr = findViewById(R.id.btnroomas);
        btnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, UserActivity.class);
                startActivity(in);
            }
        });


//        Button btnorder = findViewById(R.id.btnorder);
//        btnorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(TopicsActivity.this, OrderedBoardCastActivity.class);
//                startActivity(in);
//            }
//        });

       /* Button btnshowimage = findViewById(R.id.btnimag);
        btnshowimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TopicsActivity.this, ApiImageActivity.class);
                startActivity(in);
            }
        });*/

    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void logout() {
        // Clear SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(TopicsActivity.this, Loginactivity.class);
        startActivity(intent);
        finish(); // Finish the TopicsActivity so the user cannot go back to it
    }

    private void updateButtonTexts(String language) {

        Locale locale = null;
        if (language.equals("Kannada")) {
            locale = new Locale("kn"); // Kannada language code
        } else if (language.equals("English")) {
            locale = new Locale("en"); // English language code
        }

        if (locale != null) {
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }


        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btn10 = findViewById(R.id.btn10);
        Button btn11 = findViewById(R.id.btn11);
        Button btn12 = findViewById(R.id.btn12);
        Button btn13 = findViewById(R.id.btn13);
        Button btnJSON = findViewById(R.id.btnJSON);
        Button btnCarouselslider = findViewById(R.id.btnCarouselslider);
        Button btnGlide = findViewById(R.id.btnGlide);
        Button btnApi = findViewById(R.id.btnapi);
        Button btnVideo = findViewById(R.id.btnvideo);
        Button btnPdf = findViewById(R.id.btnpdf);
        Button btnAudio = findViewById(R.id.btnaudio);
        Button btnScan = findViewById(R.id.btnscan);
        Button btnZip = findViewById(R.id.btnzip);
        Button btnDb = findViewById(R.id.btndb);
        Button btnLoad = findViewById(R.id.btnload);
        Button btnFire = findViewById(R.id.btnfire);
        Button btnCrash = findViewById(R.id.btncrash);
        Button btnbio = findViewById(R.id.btnbio);
        Button btnotp = findViewById(R.id.btnotp);
        Button btncontent = findViewById(R.id.btncontent);
        Button btnlocal = findViewById(R.id.btnlocal);
        Button btnmapapi = findViewById(R.id.btnmapapi);
        /*if (language.equals("English")) {
            btn1.setText("Fragments");
            btn2.setText("MultiMedia");
            btn3.setText("Map");
            btn4.setText("AlertDialog");
            btn5.setText("Recycler View with a Search Option");
            btn6.setText("Android Menu");
            btn7.setText("Text to Speech");
            btn8.setText("WebView");
            btn9.setText("Logout");
            btn10.setText("Retrofit");
            btn11.setText("Swipe to Delete and Refresh");
            btn12.setText("Alarm");
            btn13.setText("Room Database");
            btnJSON.setText("JSON Parser");
            btnCarouselslider.setText("Carousel Slider");
            btnGlide.setText("Image Show");
            btnApi.setText("Image Upload");
            btnVideo.setText("Video Upload");
            btnPdf.setText("File Upload");
            btnAudio.setText("Audio Upload");
            btnScan.setText("Scanner");
            btnZip.setText("Zip File upload");
            btnDb.setText("SQLITE Database");
            btnLoad.setText("Load On Scroll");
            btnFire.setText("Firebase");
            btnCrash.setText("Firebase Crashlytics");
        } else if (language.equals("Kannada")) {
            btn1.setText("ಫ್ರಾಗ್ಮೆಂಟ್‌ಗಳು");
            btn2.setText("ಮಲ್ಟಿಮೆಡಿಯಾ");
            btn3.setText("ನಕ್ಷೆ");
            btn4.setText("ಅಲರ್ಟ್ ಡೈಲಾಗ್");
            btn5.setText("ಹುಡುಕಲು ಆಯ್ಕೆ ಹೊಂದಿರುವ ರಿಸೈಕ್ಲರ್ ವ್ಯೂ");
            btn6.setText("ಆಂಡ್ರಾಯ್ಡ್ ಮೆನು");
            btn7.setText("ಪಠ್ಯ ನಿಗಮ");
            btn8.setText("ವೆಬ್ ವೀಕ್ಷಣೆ");
            btn9.setText("ಲಾಗ್ ಔಟ್");
            btn10.setText("ರೆಟ್ರೋಫಿಟ್");
            btn11.setText("ಅಳಿಸಲು ಮತ್ತು ನವೀಕರಣಕ್ಕಾಗಿ ಸ್ವೈಪ್");
            btn12.setText("ಆಲಾರ್ಮ್");
            btn13.setText("ರೂಮ್ ಡೇಟಾಬೇಸ್");
            btnJSON.setText("ಜೇನ್ಸ್ ಪಾರ್ಸರ್");
            btnCarouselslider.setText("ಕ್ಯಾರೋಸೆಲ್ ಸ್ಲೈಡರ್");
            btnGlide.setText("ಚಿತ್ರ ಪ್ರದರ್ಶನ");
            btnApi.setText("ಚಿತ್ರ ಅಪ್‌ಲೋಡ್");
            btnVideo.setText("ವೀಡಿಯೋ ಅಪ್‌ಲೋಡ್");
            btnPdf.setText("ಫೈಲ್ ಅಪ್‌ಲೋಡ್");
            btnAudio.setText("ಆಡಿಯೋ ಅಪ್‌ಲೋಡ್");
            btnScan.setText("ಸ್ಕ್ಯಾನರ್");
            btnZip.setText("ಜಿಪ್ ಫೈಲ್ ಅಪ್‌ಲೋಡ್");
            btnDb.setText("ಎಸ್‌ಕ್ಯೂಎಲ್‌ಐಟಿ ಡೇಟಾಬೇಸ್");
            btnLoad.setText("ಸ್ಕ್ರೋಲ್‌ನಲ್ಲಿ ಲೋಡ್ ಮಾಡಿ");
            btnFire.setText("ಫೈರ್‌ಬೇಸ್");
            btnCrash.setText("ಫೈರ್‌ಬೇಸ್ ಕ್ರ್ಯಾಶ್‌ಲಿಟಿಕ್‌ಗಳು");
        }*/

       /* if (language.equals("English")) {
            btn1.setText(getString(R.string.btn1_text));
            btn2.setText(getString(R.string.btn2_text));
            btn3.setText(getString(R.string.btn3_text));
            btn4.setText(getString(R.string.btn4_text));
            btn5.setText(getString(R.string.btn5_text));
            btn6.setText(getString(R.string.btn6_text));
            btn7.setText(getString(R.string.btn7_text));
            btn8.setText(getString(R.string.btn8_text));
            btn9.setText(getString(R.string.btn9_text));
            btn10.setText(getString(R.string.btn10_text));
            btn11.setText(getString(R.string.btn11_text));
            btn12.setText(getString(R.string.btn12_text));
            btn13.setText(getString(R.string.btn13_text));
            btnJSON.setText(getString(R.string.btnJSON_text));
            btnCarouselslider.setText(getString(R.string.btnCarouselslider_text));
            btnGlide.setText(getString(R.string.btnGlide_text));
            btnApi.setText(getString(R.string.btnApi_text));
            btnVideo.setText(getString(R.string.btnVideo_text));
            btnPdf.setText(getString(R.string.btnPdf_text));
            btnAudio.setText(getString(R.string.btnAudio_text));
            btnScan.setText(getString(R.string.btnScan_text));
            btnZip.setText(getString(R.string.btnZip_text));
            btnDb.setText(getString(R.string.btnDb_text));
            btnLoad.setText(getString(R.string.btnLoad_text));
            btnFire.setText(getString(R.string.btnFire_text));
            btnCrash.setText(getString(R.string.btnCrash_text));
        } else if (language.equals("Kannada")) {
            btn1.setText(getString(R.string.btn1_text));
            btn2.setText(getString(R.string.btn2_text));
            btn3.setText(getString(R.string.btn3_text));
            btn4.setText(getString(R.string.btn4_text));
            btn5.setText(getString(R.string.btn5_text));
            btn6.setText(getString(R.string.btn6_text));
            btn7.setText(getString(R.string.btn7_text));
            btn8.setText(getString(R.string.btn8_text));
            btn9.setText(getString(R.string.btn9_text));
            btn10.setText(getString(R.string.btn10_text));
            btn11.setText(getString(R.string.btn11_text));
            btn12.setText(getString(R.string.btn12_text));
            btn13.setText(getString(R.string.btn13_text));
            btnJSON.setText(getString(R.string.btnJSON_text));
            btnCarouselslider.setText(getString(R.string.btnCarouselslider_text));
            btnGlide.setText(getString(R.string.btnGlide_text));
            btnApi.setText(getString(R.string.btnApi_text));
            btnVideo.setText(getString(R.string.btnVideo_text));
            btnPdf.setText(getString(R.string.btnPdf_text));
            btnAudio.setText(getString(R.string.btnAudio_text));
            btnScan.setText(getString(R.string.btnScan_text));
            btnZip.setText(getString(R.string.btnZip_text));
            btnDb.setText(getString(R.string.btnDb_text));
            btnLoad.setText(getString(R.string.btnLoad_text));
            btnFire.setText(getString(R.string.btnFire_text));
            btnCrash.setText(getString(R.string.btnCrash_text));
        }*/

        btn1.setText(getString(R.string.btn1_text));
        btn2.setText(getString(R.string.btn2_text));
        btn3.setText(getString(R.string.btn3_text));
        btn4.setText(getString(R.string.btn4_text));
        btn5.setText(getString(R.string.btn5_text));
        btn6.setText(getString(R.string.btn6_text));
        btn7.setText(getString(R.string.btn7_text));
        btn8.setText(getString(R.string.btn8_text));
        btn9.setText(getString(R.string.btn9_text));
        btn10.setText(getString(R.string.btn10_text));
        btn11.setText(getString(R.string.btn11_text));
        btn12.setText(getString(R.string.btn12_text));
        btn13.setText(getString(R.string.btn13_text));
        btnJSON.setText(getString(R.string.btnJSON_text));
        btnCarouselslider.setText(getString(R.string.btnCarouselslider_text));
        btnGlide.setText(getString(R.string.btnGlide_text));
        btnApi.setText(getString(R.string.btnApi_text));
        btnVideo.setText(getString(R.string.btnVideo_text));
        btnPdf.setText(getString(R.string.btnPdf_text));
        btnAudio.setText(getString(R.string.btnAudio_text));
        btnScan.setText(getString(R.string.btnScan_text));
        btnZip.setText(getString(R.string.btnZip_text));
        btnDb.setText(getString(R.string.btnDb_text));
        btnLoad.setText(getString(R.string.btnLoad_text));
        btnFire.setText(getString(R.string.btnFire_text));
        btnCrash.setText(getString(R.string.btnCrash_text));
         btnbio.setText(getString(R.string.authentication_up));
         btnotp.setText(getString(R.string.authentication));
         btncontent.setText(getString(R.string.content_providers));
         btnlocal.setText(getString(R.string.local_broadcast));
         btnmapapi.setText(getString(R.string.location_tracking));
    }

    private void saveLanguagePreference(String language) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.apply();
    }

    public void sendFCMTokenToServer(String token) {
        new APIServer(this).getString(Links.UPDATE_NOTIFICATION_ID+"?token="+token+"&type="+"token", new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == 200) {
                    Gson gson = new GsonBuilder().create();
//                        User appUser1 = gson.fromJson(response, User.class);
//                        String userJson = gson.toJson(appUser1);
//                        localStorage.put(LocalStorage.USER, userJson);
//                        localStorage.put(LocalStorage.USER_TOKEN, appUser1.getNotificationId());
                        FirebaseMessaging.getInstance().subscribeToTopic("Public");
//                        FirebaseMessaging.getInstance().subscribeToTopic("GENERAL");
// FirebaseMessaging.getInstance().subscribeToTopic("jurisdiction_" + appUser1. ().getId()); //todo : need add this line
                }
            }
        });
    }
}
