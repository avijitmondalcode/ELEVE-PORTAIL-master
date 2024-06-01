package com.student.onlinestudentportal.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.R;

import java.util.HashMap;
import java.util.Map;

public class AdminEditCourse extends AppCompatActivity {

    EditText etAdminEditCourseName;
    Button btnAdminCourseUpdate;
    String course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_course);
        etAdminEditCourseName=findViewById(R.id.etAdminEditCourseName);

        btnAdminCourseUpdate=findViewById(R.id.btnAdminCourseUpdate);

        course=getIntent().getStringExtra("Course");
        String course_id=getIntent().getStringExtra("CourseId");

        etAdminEditCourseName.setText(course);
        btnAdminCourseUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Admin_Course").document(course_id);
                Map<String,Object> hashMap=new HashMap<>();
                Map<String,Object> hashmap= new HashMap<>();
                hashmap.put("Course",etAdminEditCourseName.getText().toString());

                hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                documentReference.update(hashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused)
                    {

                        Snackbar.make(findViewById(android.R.id.content),"Course Updated Successfully!!",Snackbar.LENGTH_LONG)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AdminEditCourse.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}