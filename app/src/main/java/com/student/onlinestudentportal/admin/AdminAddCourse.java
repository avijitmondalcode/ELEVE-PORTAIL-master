package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;

import java.util.HashMap;
import java.util.Map;

public class AdminAddCourse extends AppCompatActivity {

    Button btnAdminAddCourse;
    EditText etAdminCourse;
    private final int PICK_FILE= 1;
    Uri fileUri;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_course);

        btnAdminAddCourse=findViewById(R.id.btnAdminAddCourse);
        etAdminCourse=findViewById(R.id.etAdminCourseName);

        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();

        //fetch data from notice table in firestore.

        btnAdminAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> hashmap= new HashMap<>();
                hashmap.put("Course",etAdminCourse.getText().toString());
                hashmap.put("User Id",UserId);
                hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                fire.collection("Admin_Course").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar.make(findViewById(android.R.id.content),"Course Added Successfully!!",Snackbar.LENGTH_LONG)
                                    .setAction("Ok", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }).show();

                        }
                        else
                        {
                            etAdminCourse.setText(task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }

}
