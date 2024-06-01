package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.admin.ApproveFaculty;
import com.student.onlinestudentportal.admin.NotApproveFaculty;

public class FacultyDetails extends AppCompatActivity implements View.OnClickListener{

    CardView cardApproveFaculty,cardNotApproveFaculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);
        cardApproveFaculty=findViewById(R.id.cardApproveFaculty);
        cardNotApproveFaculty=findViewById(R.id.cardNotApproveFaculty);
        cardNotApproveFaculty.setOnClickListener(this);
        cardApproveFaculty.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardApproveFaculty:
                Intent i= new Intent(this, ApproveFaculty.class);
                startActivity(i);
                break;
            case R.id.cardNotApproveFaculty:
                Intent j= new Intent(this, NotApproveFaculty.class);
                startActivity(j);
                break;



        }
    }
}