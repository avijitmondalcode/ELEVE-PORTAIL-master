package com.student.onlinestudentportal.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.student.onlinestudentportal.LoginActivity;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.StudentSignUp;
import com.student.onlinestudentportal.admin.AdminDashboard;
import com.student.onlinestudentportal.faculty.FacultySignUp;

public class StudentSignIn extends AppCompatActivity {
    private FirebaseAuth auth;
    TextView signUp;
    EditText etId, etPass;
    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);
        auth = FirebaseAuth.getInstance();
        signUp=findViewById(R.id.tvStudentSignUp);
        etId = findViewById(R.id.editTextStudentEmail);
        etPass = findViewById(R.id.editTextStudentPassword);
        logIn = findViewById(R.id.cirStudentLoginButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });
    }
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvStudentSignUp:
                startActivity(new Intent(this, StudentSignUp.class));
                break;
        }
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
                        Intent i = new Intent(StudentSignIn.this, StudentDashboard.class);
                        startActivity(i);
                        Toast.makeText(StudentSignIn.this,"Successful",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(StudentSignIn.this,"Invalid details...",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(StudentSignIn.this,"Fields Cannot be Empty",Toast.LENGTH_LONG).show();
        }

    }
}
