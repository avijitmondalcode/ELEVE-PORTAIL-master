package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Management extends AppCompatActivity implements View.OnClickListener {
    CardView cardBCA,cardBBA,cardBBAHM,cardBCS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        cardBCA=findViewById(R.id.cardBCA);
        cardBBA=findViewById(R.id.cardBBA);
        cardBBAHM=findViewById(R.id.cardBBAHM);
        cardBCS=findViewById(R.id.cardBCS);
        cardBCA.setOnClickListener(this);
        cardBBA.setOnClickListener(this);
        cardBBAHM.setOnClickListener(this);
        cardBCS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardBCA:
                i = new Intent(this, BCA.class);
                startActivity(i);
                break;
            case R.id.cardBBA:
                i = new Intent(this, BBA.class);
                startActivity(i);
                break;
            case R.id.cardBBAHM:
                i = new Intent(this, BBAHM.class);
                startActivity(i);
                break;
            case R.id.cardBCS:
                i = new Intent(this, BCS.class);
                startActivity(i);
                break;
        }
    }
}