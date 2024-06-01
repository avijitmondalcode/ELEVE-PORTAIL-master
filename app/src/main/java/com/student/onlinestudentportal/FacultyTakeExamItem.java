package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FacultyTakeExamItem extends AppCompatActivity implements View.OnClickListener {

    CardView cardFacultyAddQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_take_exam_item);
        cardFacultyAddQuestion = findViewById(R.id.cardFacultyAddQuestion);
        cardFacultyAddQuestion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardFacultyAddQuestion:
                i = new Intent(this, FacultyAddQuestion.class);
                startActivity(i);
                break;
        }

    }
}