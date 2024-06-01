package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Btech extends AppCompatActivity implements View.OnClickListener {
    CardView cardCse,cardME,cardEE,cardECE,cardEIE,cardCE,cardIT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btech);
        cardCse=findViewById(R.id.cardCse);
        cardCE=findViewById(R.id.cardCE);
        cardEE=findViewById(R.id.cardEE);
        cardME=findViewById(R.id.cardME);
        cardECE=findViewById(R.id.cardECE);
        cardEIE=findViewById(R.id.cardEIE);
        cardIT=findViewById(R.id.cardIT);
        cardCse.setOnClickListener(this);
        cardEIE.setOnClickListener(this);
        cardECE.setOnClickListener(this);
        cardME.setOnClickListener(this);
        cardEE.setOnClickListener(this);
        cardCE.setOnClickListener(this);
        cardIT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cardCse:
                i= new Intent(Btech.this,CSE.class);
                startActivity(i);
                break;
            case R.id.cardME:
                i= new Intent(Btech.this,ME.class);
                startActivity(i);
                break;
            case R.id.cardIT:
                i= new Intent(Btech.this,IT.class);
                startActivity(i);
                break;
            case R.id.cardEE:
                i= new Intent(Btech.this,EE.class);
                startActivity(i);
                break;
            case R.id.cardECE:
                i= new Intent(Btech.this,ECE.class);
                startActivity(i);
                break;
            case R.id.cardEIE:
                i= new Intent(Btech.this,EIE.class);
                startActivity(i);
                break;
            case R.id.cardCE:
                i= new Intent(Btech.this,CE.class);
                startActivity(i);
                break;
        }
    }
}