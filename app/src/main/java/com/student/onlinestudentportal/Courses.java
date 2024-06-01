package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Courses extends AppCompatActivity implements View.OnClickListener {
    CardView cardBtech, cardBsc, cardMasters, cardManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        cardBtech = findViewById(R.id.cardBtech);
        cardBsc = findViewById(R.id.cardBsc);
        cardMasters = findViewById(R.id.cardMasters);
        cardManagement = findViewById(R.id.cardManagement);
        cardBtech.setOnClickListener(this);
        cardBsc.setOnClickListener(this);
        cardManagement.setOnClickListener(this);
        cardMasters.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardBtech:
                i = new Intent(this, BtechDesc.class);
                startActivity(i);
                break;
            case R.id.cardBsc:
                i = new Intent(this, BscDesc.class);
                startActivity(i);
                break;
            case R.id.cardManagement:
                i = new Intent(this, ManagementDesc.class);
                startActivity(i);
                break;
            case R.id.cardMasters:
                i = new Intent(this, Masters_Desc.class);
                startActivity(i);
                break;

        }
    }
}