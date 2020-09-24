package com.ishaanp.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DeleteRecord extends AppCompatActivity {

    private Button NoBTN;
    private Button YesBTN;
    private Context ctx;

    private String dataKey = "";
    private int position;

    private ArrayList<SaveListModel> savedRecords = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_record);

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
                deleteRecord();
            }
        });

        getSupportActionBar().hide();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int)(height * 0.3));
    }

    public void noPressed(){
        Intent intent = new Intent(this, SavedRecordView.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }

    public void deleteRecord(){
        if(savedRecords.size() > 0){
            savedRecords.remove(position);
            SaveList();
        }
        Intent intent = new Intent(this, SavedRecordView.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);

    }

    public void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
            position = bundle.getInt("Position");
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, SavedRecordView.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }

    private void loadList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(dataKey, null);
        Type type = new TypeToken<ArrayList<SaveListModel>>() {}.getType();
        savedRecords = gson.fromJson(json, type);
        if(savedRecords == null ||savedRecords.size() == 0){
            savedRecords = new ArrayList<>();
        }
    }
    private void SaveList(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedRecords);
        editor.putString(dataKey, json);
        editor.apply();
    }


}
