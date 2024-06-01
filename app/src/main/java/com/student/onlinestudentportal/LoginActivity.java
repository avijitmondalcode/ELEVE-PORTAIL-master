package com.student.onlinestudentportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.student.onlinestudentportal.Student.StudentDashboard;
import com.student.onlinestudentportal.admin.AdminDashboard;
import com.student.onlinestudentportal.faculty.FacultyDashboard;


public class   LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText etId, etPass;
    Button logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        auth = FirebaseAuth.getInstance();

        etId = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        logIn = findViewById(R.id.cirLoginButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });
    }

    private void login() {
        String email = etId.getText().toString();
        String pass = etPass.getText().toString();
        if (!email.equals("") && !pass.equals("")) {
            //Toast.makeText(Login.this,"Wrong Details..",Toast.LENGTH_LONG).show();
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(LoginActivity.this,AdminDashboard.class);
                        startActivity(i);
                        Toast.makeText(LoginActivity.this,"Successful",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Invalid details...",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(LoginActivity.this,"Fields Cannot be Empty",Toast.LENGTH_LONG).show();
        }

    }
}