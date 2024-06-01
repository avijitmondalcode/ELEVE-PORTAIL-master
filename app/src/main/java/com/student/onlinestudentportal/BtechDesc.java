package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BtechDesc extends AppCompatActivity {
    FloatingActionButton btechDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btech_desc);
        btechDept=findViewById(R.id.btechDept);
        btechDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(BtechDesc.this,Btech.class);
                startActivity(i);
            }
        });
    }
}