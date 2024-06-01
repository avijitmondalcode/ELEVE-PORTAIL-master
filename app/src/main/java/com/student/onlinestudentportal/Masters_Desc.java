package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Masters_Desc extends AppCompatActivity {
    FloatingActionButton mastersDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masters_desc);
        mastersDept=findViewById(R.id.mastersDept);
        mastersDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Masters_Desc.this,Masters.class);
                startActivity(i);
            }
        });
    }
}