package com.student.onlinestudentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.student.onlinestudentportal.Student.StudentSignIn;
import com.student.onlinestudentportal.faculty.FacultySignIn;

public class MainActivity extends AppCompatActivity {
    Button buttonAdmin, buttonStudent, buttonFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        buttonAdmin= findViewById(R.id.btnAdmin);
        buttonFaculty=findViewById(R.id.btnFaculty);
        buttonStudent=findViewById(R.id.btnStudent);
        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        buttonFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this, FacultySignIn.class);
                startActivity(i);
            }
        });
        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this, StudentSignIn.class);
                startActivity(i);
            }
        });
    }
}
