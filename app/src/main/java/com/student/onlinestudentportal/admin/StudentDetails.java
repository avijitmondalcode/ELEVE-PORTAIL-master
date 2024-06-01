package com.student.onlinestudentportal.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.ApproveStudent;
import com.student.onlinestudentportal.admin.NotApproveStudent;

public class StudentDetails extends AppCompatActivity implements View.OnClickListener {

    CardView cardApproveStudent,cardNotApproveStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        cardApproveStudent=findViewById(R.id.cardApproveStudent);
        cardNotApproveStudent=findViewById(R.id.cardNotApproveStudent);
        cardNotApproveStudent.setOnClickListener(this);
        cardApproveStudent.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cardApproveStudent:
                i= new Intent(this, ApproveStudent.class);
                startActivity(i);
                break;
            case R.id.cardNotApproveStudent:
                i= new Intent(this, NotApproveStudent.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}