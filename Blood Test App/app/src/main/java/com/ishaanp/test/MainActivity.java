package com.ishaanp.test;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String dataKey = "";//this will be passed through each activity after this one so you can access data from correct profile
    private Button newRecordBTN;
    private Button savedRecordBTN;
    private Button guideBTN;
    private Button compareBTN;
    private Button testBTN;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();

        newRecordBTN = findViewById(R.id.newRecordBTN);
        savedRecordBTN = findViewById(R.id.savedRecordBTN);
        guideBTN = findViewById(R.id.Guide);
        compareBTN = findViewById(R.id.CompareBTN);
        testBTN = findViewById(R.id.quizBTN);


/*
        buttonEffect(newRecordBTN);
        buttonEffect(savedRecordBTN);
        buttonEffect(guideBTN);
        buttonEffect(compareBTN);

 */


        newRecordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewRecordActivity();
            }
        });
        savedRecordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSavedRecordActivity();
            }
        });
        guideBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGuide();
            }
        });
        compareBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCompareActivity();
            }
        });
        testBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTestActivity();
            }
        });

    }

    void openNewRecordActivity(){
        Intent intent = new Intent(this, CreateNewRecord.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }

    void openSavedRecordActivity(){
        Intent intent = new Intent(this, SavedRecordView.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }
    void openGuide(){
        Intent intent = new Intent(this, Guide.class);
        startActivity(intent);
    }

    void openCompareActivity(){
        Intent intent = new Intent(this, SelectWhatToCompare.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }
    void openTestActivity(){
        Intent intent = new Intent(this, healthQuiz.class);
        intent.putExtra("DataIDProfile", dataKey);
        startActivity(intent);
    }

    void getID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            dataKey = bundle.getString("DataIDProfile");
        }
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, SelectProfile.class);
        startActivity(intent);

    }


    public void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }


}
