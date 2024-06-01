package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManagementDesc extends AppCompatActivity {
    FloatingActionButton managementDept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_desc);
        managementDept=findViewById(R.id.managementDept);
        managementDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ManagementDesc.this,Management.class);
                startActivity(i);
            }
        });
    }
}