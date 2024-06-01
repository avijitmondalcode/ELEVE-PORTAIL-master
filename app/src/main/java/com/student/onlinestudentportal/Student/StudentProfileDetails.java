package com.student.onlinestudentportal.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.faculty.FacultyEditProfiles;
import com.student.onlinestudentportal.faculty.FacultyViewProfileDetails;

public class StudentProfileDetails extends AppCompatActivity implements View.OnClickListener {
    CardView cardStudentViewProfile, cardStudentEditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_details);
        cardStudentEditProfile = findViewById(R.id.cardStudentEditProfile);
        cardStudentViewProfile = findViewById(R.id.cardStudentViewProfile);
        cardStudentEditProfile.setOnClickListener(this);
        cardStudentViewProfile.setOnClickListener(this);
    }
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardStudentEditProfile:
                i = new Intent(this, StudentEditProfile.class);
                startActivity(i);
                break;
            case R.id.cardStudentViewProfile:
                i = new Intent(this, StudentViewProfileDetails.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}