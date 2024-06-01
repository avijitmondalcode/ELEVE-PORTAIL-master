package com.student.onlinestudentportal.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AdminEditSubject extends AppCompatActivity {

    EditText etAdminEditSubjectName;
    EditText etAdminEditSubjectCourse,etAdminEditSubjectDepartment;
    Button btnAdminSubjectUpdate;
    String course,department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_subject);
        etAdminEditSubjectName=findViewById(R.id.etAdminEditSubjectName);
        etAdminEditSubjectCourse=findViewById(R.id.tvAdminEditSubjectCourse);
        etAdminEditSubjectDepartment=findViewById(R.id.tvAdminEditSubjectDepartment);
        btnAdminSubjectUpdate=findViewById(R.id.btnAdminSubjectUpdate);

        String sub_name=getIntent().getStringExtra("Name");
        String sub_course=getIntent().getStringExtra("Course");
        String sub_department=getIntent().getStringExtra("Department");
        String subject_id=getIntent().getStringExtra("SubjectId");

        etAdminEditSubjectName.setText(sub_name);
        etAdminEditSubjectCourse.setText(sub_course);
        etAdminEditSubjectDepartment.setText(sub_department);

        btnAdminSubjectUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Admin_Subject").document(subject_id);
                Map<String,Object> hashMap=new HashMap<>();
                Map<String,Object> hashmap= new HashMap<>();
                hashmap.put("Subject_Name",etAdminEditSubjectName.getText().toString());
                hashmap.put("Course",etAdminEditSubjectCourse.getText().toString());
                hashmap.put("Department",etAdminEditSubjectDepartment.getText().toString());
                hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                documentReference.update(hashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused)
                    {

                        Snackbar.make(findViewById(android.R.id.content),"Subject Updated Successfully!!",Snackbar.LENGTH_LONG)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AdminEditSubject.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}