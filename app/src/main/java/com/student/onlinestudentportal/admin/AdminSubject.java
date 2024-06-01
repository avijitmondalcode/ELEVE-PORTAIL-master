package com.student.onlinestudentportal.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.onlinestudentportal.R;

public class AdminSubject extends AppCompatActivity implements View.OnClickListener {
    CardView cardAdminAddSubject,cardAdminViewSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_subject);
        cardAdminAddSubject=findViewById(R.id.cardAdminAddSubject);
        cardAdminViewSubject=findViewById(R.id.cardAdminViewSubject);

        cardAdminAddSubject.setOnClickListener(this);
        cardAdminViewSubject.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cardAdminAddSubject:
                i= new Intent(this, AdminAddSubject.class);
                startActivity(i);
                break;
            case R.id.cardAdminViewSubject:
                i= new Intent(this, AdminViewSubject.class);
                startActivity(i);
                break;


        }
    }
}