package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Bsc extends AppCompatActivity implements View.OnClickListener {
    CardView cardPhysics,cardMaths,cardBiology,cardChemistry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsc);
        cardBiology=findViewById(R.id.cardBiology);
        cardPhysics=findViewById(R.id.cardPhysics);
        cardChemistry=findViewById(R.id.cardChemistry);
        cardMaths=findViewById(R.id.cardMaths);
        cardBiology.setOnClickListener(this);
        cardPhysics.setOnClickListener(this);
        cardMaths.setOnClickListener(this);
        cardChemistry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardBiology:
                i = new Intent(this, Biology.class);
                startActivity(i);
                break;
            case R.id.cardPhysics:
                i = new Intent(this, Physics.class);
                startActivity(i);
                break;
            case R.id.cardMaths:
                i = new Intent(this, Maths.class);
                startActivity(i);
                break;
            case R.id.cardChemistry:
                i = new Intent(this, Chemistry.class);
                startActivity(i);
                break;
        }
    }
}