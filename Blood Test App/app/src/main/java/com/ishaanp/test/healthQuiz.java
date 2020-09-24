package com.ishaanp.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class healthQuiz extends AppCompatActivity {

    public TextView question;
    public TextView questionNo;

    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button option5;

    int questionCurrent = 1;
    private ArrayList<String> questions = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_quiz);

        initializeQuestions();

        option1 = findViewById(R.id.Option1);
        option2 = findViewById(R.id.Option2);
        option3 = findViewById(R.id.Option3);
        option4 = findViewById(R.id.Option4);
        option5 = findViewById(R.id.Option5);
        question = findViewById(R.id.question);
        questionNo = findViewById(R.id.questionNumber);

        questionNo.setText(String.valueOf(questionCurrent) + "/25:");
        question.setText(questions.get(questionCurrent-1));

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextQuestion();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextQuestion();
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextQuestion();
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextQuestion();
            }
        });
        option5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextQuestion();
            }
        });


    }

    void initializeQuestions(){
        questions.add("1");
        questions.add("2");
        questions.add("3");
        questions.add("4");
        questions.add("5");
        questions.add("6");
        questions.add("7");
        questions.add("8");
        questions.add("9");
        questions.add("10");
        questions.add("11");
        questions.add("12");
        questions.add("13");
        questions.add("14");
        questions.add("15");
        questions.add("16");
        questions.add("17");
        questions.add("18");
        questions.add("19");
        questions.add("20");
        questions.add("20");
        questions.add("21");
        questions.add("22");
        questions.add("23");
        questions.add("24");
        questions.add("25");
    }

    void NextQuestion(){
        if(questionCurrent <= 25 ){
            questionCurrent++;
            questionNo.setText(String.valueOf(questionCurrent) + "/25:");
            question.setText(questions.get(questionCurrent-1));
        }

    }
}
