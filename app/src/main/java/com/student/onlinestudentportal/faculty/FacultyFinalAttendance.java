package com.student.onlinestudentportal.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.ApproveStudent;
import com.student.onlinestudentportal.admin.NotApproveStudent;

public class FacultyFinalAttendance extends AppCompatActivity implements View.OnClickListener {

    CardView cardFacultyTakeAttendance,cardFacultyViewAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_final_attendance);
        cardFacultyTakeAttendance=findViewById(R.id.cardFacultyTakeAttendance);
        cardFacultyViewAttendance=findViewById(R.id.cardFacultyViewAttendance);
        cardFacultyTakeAttendance.setOnClickListener(this);
        cardFacultyViewAttendance.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cardFacultyTakeAttendance:
                i= new Intent(this, FacultyAttendance.class);
                startActivity(i);
                break;
            case R.id.cardFacultyViewAttendance:
                i= new Intent(this, FacultyCheckAttendance.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}