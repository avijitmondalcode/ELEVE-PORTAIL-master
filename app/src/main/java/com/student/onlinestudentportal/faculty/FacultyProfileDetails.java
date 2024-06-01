package com.student.onlinestudentportal.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;

public class FacultyProfileDetails extends AppCompatActivity implements View.OnClickListener {
    CardView cardFacultyEditProfile,cardFacultyViewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile_details);
        cardFacultyEditProfile = findViewById(R.id.cardFacultyEditProfile);
        cardFacultyViewProfile = findViewById(R.id.cardFacultyViewProfile);
        cardFacultyEditProfile.setOnClickListener(this);
        cardFacultyViewProfile.setOnClickListener(this);

    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardFacultyEditProfile:
                i = new Intent(this, FacultyEditProfiles.class);
                startActivity(i);
                break;
            case R.id.cardFacultyViewProfile:
                i = new Intent(this, FacultyViewProfileDetails.class);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
