package com.student.onlinestudentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText etMail,etPass;
    Button btnSign;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        etMail=findViewById(R.id.editTextEmail);
        etPass=findViewById(R.id.editTextPassword);
        btnSign=findViewById(R.id.cirSignUpButton);
        auth= FirebaseAuth.getInstance();
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

    }





    private void validate() {
        String email = etMail.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        final Pattern PASSWORD_PATTERN =

                Pattern.compile("^" +

                        "(?=.*[@#$%^&+=])" +     // at least 1 special character

                        "(?=\\S+$)" +            // no white spaces

                        ".{8,}" +                // at least 8 characters

                        "$");

        if (email.isEmpty()) {
            etMail.setError("Field can not be empty");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMail.requestFocus();
            etMail.setError("Please enter a valid email address");
            return;
        }

        if (password.isEmpty()) {
            etPass.setError("Field can not be empty");
            return;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()){
            etPass.requestFocus();
            etPass.setError("Password is too weak");
            return;
        }

        //Toast.makeText(getApplicationContext(),gender,Toast.LENGTH_LONG).show();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(),task.getResult().toString(),Toast.LENGTH_LONG).show();
                    Intent i= new Intent(Register.this,UserProfileDetails.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
//    public void confirmInput(View v) {
//
//        if (!validateEmail() | !validatePassword()) {
//
//            return;
//
//        }

}