package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BscDesc extends AppCompatActivity {
    FloatingActionButton bscDept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsc_desc);
        bscDept=findViewById(R.id.bscDept);
        bscDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BscDesc.this,Bsc.class);
                startActivity(i);
            }
        });
    }
}