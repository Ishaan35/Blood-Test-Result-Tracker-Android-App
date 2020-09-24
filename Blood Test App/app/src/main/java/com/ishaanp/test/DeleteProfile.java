package com.ishaanp.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DeleteProfile extends AppCompatActivity {

    private Button NoBTN;
    private Button YesBTN;
    private Context ctx;

    private String dataKey = "";
    private int position;

    private ArrayList<Profile> profiles;
    private String PROFILES = "Profiles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);

        NoBTN = findViewById(R.id.No);
        YesBTN = findViewById(R.id.Yes);
        ctx = this;
        getID();
        loadList();

        NoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noPressed();
            }
        });
        YesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile();
            }
        });

        getSupportActionBar().hide();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int)(height * 0.3));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, SelectProfile.class);
        startActivity(intent);
    }

    public void noPressed(){
        Intent intent = new Intent(this, SelectProfile.class);
        startActivity(intent);
    }


    public void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
            position = bundle.getInt("Position");
        }
    }

    private void deleteProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(dataKey);
        editor.apply();
        if(profiles.size() != 0)
            profiles.remove(position);
        saveList();



        Intent intent = new Intent(this, SelectProfile.class);
        startActivity(intent);
    }

    private void loadList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PROFILES, null);
        Type type = new TypeToken<ArrayList<Profile>>() {}.getType();
        profiles = gson.fromJson(json, type);
        if(profiles == null ||profiles.size() == 0){
            profiles = new ArrayList<>();
        }
    }

    private void saveList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profiles);
        editor.putString(PROFILES, json);
        editor.apply();
    }
}
