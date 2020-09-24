package com.ishaanp.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddProfile extends AppCompatActivity {

    private ArrayList<Profile> profiles;
    private String PROFILES = "Profiles";

    private Context ctx = this;
    private int profile_icon_res_ID = 999;

    private EditText nameTXT;
    private EditText ageTXT;
    private EditText heightTXT;
    private EditText weightTXT;
    private TextView BMI;
    private Button createBTN;


    //icons
    private Button female1;
    private Button female2;
    private Button female3;
    private Button female4;
    private Button female5;
    private Button female6;
    private Button female7;
    private Button female8;
    private Button female9;
    private Button female10;
    private Button female11;
    private Button female12;
    private Button female13;
    private Button female14;
    private Button female15;
    private Button female16;
    private Button female17;
    private Button male1;
    private Button male2;
    private Button male3;
    private Button male4;
    private Button male5;
    private Button male6;
    private Button male7;
    private Button male8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile2);

        setTitle("Fill Out Your Profile");
        nameTXT = findViewById(R.id.Name);
        ageTXT = findViewById(R.id.Age);
        heightTXT = findViewById(R.id.height);
        weightTXT = findViewById(R.id.weight);
        BMI = findViewById(R.id.BMI);

        ageTXT.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        weightTXT.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        heightTXT.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        createBTN = findViewById(R.id.Create);

        female1 = findViewById(R.id.female1);
        female2 = findViewById(R.id.female2);
        female3 = findViewById(R.id.female3);
        female4 = findViewById(R.id.female4);
        female5 = findViewById(R.id.female5);
        female6 = findViewById(R.id.female6);
        female7 = findViewById(R.id.female7);
        female8 = findViewById(R.id.female8);
        female9 = findViewById(R.id.female9);
        female10 = findViewById(R.id.female10);
        female11 = findViewById(R.id.female11);
        female12 = findViewById(R.id.female12);
        female13 = findViewById(R.id.female13);
        female14 = findViewById(R.id.female14);
        female15 = findViewById(R.id.female15);
        female16 = findViewById(R.id.female16);
        female17 = findViewById(R.id.female17);
        male1 = findViewById(R.id.male1);
        male2 = findViewById(R.id.male2);
        male3 = findViewById(R.id.male3);
        male4 = findViewById(R.id.male4);
        male5 = findViewById(R.id.male5);
        male6 = findViewById(R.id.male6);
        male7 = findViewById(R.id.male7);
        male8 = findViewById(R.id.male8);



        setClicks();

        loadList();
        //add button click which will add the profile object into the array, and then call savelist
        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProfile();
            }
        });

        heightTXT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(heightTXT.getText().toString()) && !TextUtils.isEmpty(weightTXT.getText().toString()) && !heightTXT.getText().toString().equalsIgnoreCase("0")){
                    double weight = Double.parseDouble(weightTXT.getText().toString());
                    double height = Double.parseDouble(heightTXT.getText().toString());
                    double BMIval = weight/Math.pow(height, 2);
                    DecimalFormat dec = new DecimalFormat("#0.00");
                    BMI.setText( "BMI: "+String.valueOf(dec.format(BMIval)));
                }
            }
        });

        weightTXT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(heightTXT.getText().toString()) && !TextUtils.isEmpty(weightTXT.getText().toString()) && !heightTXT.getText().toString().equalsIgnoreCase("0")){
                    double weight = Double.parseDouble(weightTXT.getText().toString());
                    double height = Double.parseDouble(heightTXT.getText().toString());
                    double BMIval = weight/Math.pow(height, 2);

                    DecimalFormat dec = new DecimalFormat("#0.00");
                    BMI.setText("BMI: "+String.valueOf(dec.format(BMIval)));
                }
            }
        });
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

    private void addProfile(){
        String nametxt = nameTXT.getText().toString();
        if(TextUtils.isEmpty(nametxt)){
            Toast.makeText(this, "Please enter a name for the profile", Toast.LENGTH_LONG).show();
        }
        else if(ageTXT == null || ageTXT.equals("") || TextUtils.isEmpty(ageTXT.getText().toString())){
            Toast.makeText(this, "Please enter your age for the profile", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(weightTXT.getText().toString())){
            Toast.makeText(this, "Please enter your weight in kg for your profile", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(heightTXT.getText().toString())){
            Toast.makeText(this, "Please enter your height in m for your profile", Toast.LENGTH_LONG).show();
        }
        else if(profile_icon_res_ID == 999){
            Toast.makeText(this, "Please select an icon for your profile", Toast.LENGTH_LONG).show();
        }
        else{
            for(int i = 0; i < profiles.size(); i++){
                String currentName = profiles.get(i).getNAME();
                if(nametxt.matches(currentName)){
                    Toast.makeText(this, "Profile " + nameTXT.getText().toString() + " already exists", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            int age = (int) Double.parseDouble(ageTXT.getText().toString());
            double weight =  Double.parseDouble(weightTXT.getText().toString());
            double height =  Double.parseDouble(heightTXT.getText().toString());
            double BMIval =  weight/Math.pow(height, 2);
            Profile profile = new Profile();
            profile.setAge(age);
            profile.setNAME(nametxt);
            profile.setHeightInMeters(height);
            profile.setWeightInKg(weight);
            profile.setBMI(BMIval);
            profile.setIconID(profile_icon_res_ID);

            profiles.add(profile);
            saveList();
            Intent intent = new Intent(this, SelectProfile.class);
            startActivity(intent);
        }
    }

    void setClicks(){
        female1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female1.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female1;
            }
        });
        female2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female2.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female2;
            }
        });
        female3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female3.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female3;
            }
        });
        female4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female4.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female4;
            }
        });
        female5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female5.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female5;
            }
        });
        female6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female6.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female6;
            }
        });
        female7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female7.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female7;
            }
        });
        female8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female8.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female8;
            }
        });
        female9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female9.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female9;
            }
        });
        female10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female10.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female10;
            }
        });
        female11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female11.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female11;
            }
        });
        female12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female12.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female12;
            }
        });
        female13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female13.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female13;
            }
        });
        female14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female14.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female15;
            }
        });
        female15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female15.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female16;
            }
        });
        female16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female16.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female17;
            }
        });
        female17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                female17.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.female18;
            }
        });
        male1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male1.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male1;
            }
        });
        male2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male2.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male2;
            }
        });
        male3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male3.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male3;
            }
        });
        male4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male4.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male4;
            }
        });
        male5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male5.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male5;
            }
        });
        male6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male6.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male6;
            }
        });
        male7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male7.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male7;
            }
        });
        male8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckmarks();
                male8.setBackgroundResource(R.drawable.checkmark);
                profile_icon_res_ID = R.drawable.male8;
            }
        });
    }

    void removeCheckmarks(){
        female1.setBackgroundResource(R.drawable.female1);
        female2.setBackgroundResource(R.drawable.female2);
        female3.setBackgroundResource(R.drawable.female3);
        female4.setBackgroundResource(R.drawable.female4);
        female5.setBackgroundResource(R.drawable.female5);
        female6.setBackgroundResource(R.drawable.female6);
        female7.setBackgroundResource(R.drawable.female7);
        female8.setBackgroundResource(R.drawable.female8);
        female9.setBackgroundResource(R.drawable.female9);
        female10.setBackgroundResource(R.drawable.female10);
        female11.setBackgroundResource(R.drawable.female11);
        female12.setBackgroundResource(R.drawable.female12);
        female13.setBackgroundResource(R.drawable.female13);
        female14.setBackgroundResource(R.drawable.female15);
        female15.setBackgroundResource(R.drawable.female16);
        female16.setBackgroundResource(R.drawable.female17);
        female17.setBackgroundResource(R.drawable.female18);

        male1.setBackgroundResource(R.drawable.male1);
        male2.setBackgroundResource(R.drawable.male2);
        male3.setBackgroundResource(R.drawable.male3);
        male4.setBackgroundResource(R.drawable.male4);
        male5.setBackgroundResource(R.drawable.male5);
        male6.setBackgroundResource(R.drawable.male6);
        male7.setBackgroundResource(R.drawable.male7);
        male8.setBackgroundResource(R.drawable.male8);
    }

}
