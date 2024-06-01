package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminNotice;
import com.student.onlinestudentportal.admin.AdminNoticeAdapter;
import com.student.onlinestudentportal.admin.AdminNoticeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddSubject extends AppCompatActivity {

    Spinner adminAddSubjectCourse,adminAddSubjectDepartment,spinAdminSubjectSem;
    Button btnAdminAddSubject;
    EditText etAdminSubject;
    private final int PICK_FILE= 1;
    Uri fileUri;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course,department,subjectName,semester;


    String[] studentCourse={"Select Course","BTech","Bsc","Management","Technology","Masters"};
    String[] studentManagement={"Select Department","BBA","BBA(HM)","BBA(HPM)"};
    String[] studentBTech={"Select Department","CSE","IT","ECE","EIE","EE","ME","CE"};
    String[] studentTechnology={"Select Department","BCA","BCS"};
    String[] studentBsc={"Select Department","Chemistry","Physics","Mathematics","BIOLOGY"};
    String[] studentMasters={"Select Department","MCA","MTech","MSc"};
    String[] studentSemester = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_subject);

        adminAddSubjectCourse=findViewById(R.id.spinAdminSubjectCourse);
        adminAddSubjectDepartment=findViewById(R.id.spinAdminSubjectDept);
        spinAdminSubjectSem=findViewById(R.id.spinAdminSubjectSem);
        btnAdminAddSubject=findViewById(R.id.btnAdminAddSubject);
        etAdminSubject=findViewById(R.id.etAdminSubjectName);

        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();

        //fetch data from notice table in firestore.

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentCourse);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminAddSubjectCourse.setAdapter(aa);
        ArrayAdapter ab1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentBTech);
        ab1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminAddSubjectDepartment.setAdapter(ab1);

        ArrayAdapter ab2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentBsc);
        ab2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminAddSubjectDepartment.setAdapter(ab2);

        ArrayAdapter ab3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentManagement);
        ab3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminAddSubjectDepartment.setAdapter(ab3);

        ArrayAdapter ab4 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentTechnology);
        ab4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminAddSubjectDepartment.setAdapter(ab4);

        ArrayAdapter ab5 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentMasters);
        ab5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminAddSubjectDepartment.setAdapter(ab5);

        //Adapter for Spinner Semester
        ArrayAdapter sem = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentSemester);
        sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAdminSubjectSem.setAdapter(sem);
        //Adapter for Spinner Semester


        adminAddSubjectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = adminAddSubjectCourse.getSelectedItem().toString();
                if(adminAddSubjectCourse.getSelectedItem().toString().equals("BTech")){
                    adminAddSubjectDepartment.setAdapter(ab1);
                }else if (adminAddSubjectCourse.getSelectedItem().toString().equals("Bsc")){
                    adminAddSubjectDepartment.setAdapter(ab2);
                }else if(adminAddSubjectCourse.getSelectedItem().toString().equals("Management")){
                    adminAddSubjectDepartment.setAdapter(ab3);
                }else if (adminAddSubjectCourse.getSelectedItem().toString().equals("Technology")){
                    adminAddSubjectDepartment.setAdapter(ab4);
                }else if(adminAddSubjectCourse.getSelectedItem().toString().equals("Masters")){
                    adminAddSubjectDepartment.setAdapter(ab5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adminAddSubjectDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=adminAddSubjectDepartment.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Select Semester AdapterView
        spinAdminSubjectSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                semester = spinAdminSubjectSem.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Semester AdapterView


        btnAdminAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> hashmap= new HashMap<>();
                hashmap.put("Subject_Name",etAdminSubject.getText().toString());
                hashmap.put("User Id",UserId);
                hashmap.put("Course",course);
                hashmap.put("Department",department);
                hashmap.put("Semester",semester);
                hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                fire.collection("Admin_Subject").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar.make(findViewById(android.R.id.content),"Subject Added Successfully!!",Snackbar.LENGTH_LONG)
                                    .setAction("Ok", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }).show();

                        }
                        else
                        {
                            etAdminSubject.setText(task.getException().getMessage());
                        }
                    }
                });
            }
        });


    }



}
