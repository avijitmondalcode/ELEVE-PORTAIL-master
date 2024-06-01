package com.student.onlinestudentportal.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;

public class AdminProfileDetails extends AppCompatActivity implements View.OnClickListener {

    CardView cardAdminViewProfile, cardAdminEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_details);
        cardAdminEditProfile = findViewById(R.id.cardAdminEditProfile);
        cardAdminViewProfile = findViewById(R.id.cardAdminViewProfile);
        cardAdminEditProfile.setOnClickListener(this);
        cardAdminViewProfile.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardAdminEditProfile:
                i = new Intent(this, AdminAddProfileDetails.class);
                startActivity(i);
                break;
            case R.id.cardAdminViewProfile:
                i = new Intent(this, AdminViewProfileDetails.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
