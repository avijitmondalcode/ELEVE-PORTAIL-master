package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.admin.ApproveFaculty;
import com.student.onlinestudentportal.admin.NotApproveFaculty;

public class FacultyExam extends AppCompatActivity implements View.OnClickListener{

    CardView cardFacultyTakeExam,cardFacultyResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_exam);
        cardFacultyTakeExam=findViewById(R.id.cardFacultyTakeExam);
        cardFacultyTakeExam.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardFacultyTakeExam:
                Intent i= new Intent(this, FacultyTakeExam.class);
                startActivity(i);
                break;
           /* case R.id.cardFacultyResult:
                Intent j= new Intent(this, FacultyResult.class);
                startActivity(j);
                break;*/



        }
    }
}