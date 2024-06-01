package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Masters extends AppCompatActivity implements View.OnClickListener {
    CardView cardMCA,cardMBA,cardMtech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masters);
        cardMCA=findViewById(R.id.cardMCA);
        cardMBA=findViewById(R.id.cardMBA);
        cardMtech=findViewById(R.id.cardMtech);
        cardMCA.setOnClickListener(this);
        cardMBA.setOnClickListener(this);
        cardMtech.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardMCA:
                i = new Intent(this, MCA.class);
                startActivity(i);
                break;
            case R.id.cardMBA:
                i = new Intent(this, MBA.class);
                startActivity(i);
                break;
            case R.id.cardMtech:
                i = new Intent(this, Mtech.class);
                startActivity(i);
                break;
        }
    }
}