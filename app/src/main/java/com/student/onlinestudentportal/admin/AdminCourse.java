package com.student.onlinestudentportal.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;

public class AdminCourse extends AppCompatActivity implements View.OnClickListener {
    CardView cardAdminAddCourse,cardAdminViewCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);
        cardAdminAddCourse=findViewById(R.id.cardAdminAddCourse);
        cardAdminViewCourse=findViewById(R.id.cardAdminViewCourse);

        cardAdminAddCourse.setOnClickListener(this);
        cardAdminViewCourse.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cardAdminAddCourse:
                i= new Intent(this, AdminAddCourse.class);
                startActivity(i);
                break;
            case R.id.cardAdminViewCourse:
                i= new Intent(this, AdminViewCourse.class);
                startActivity(i);
                break;


        }
    }
}