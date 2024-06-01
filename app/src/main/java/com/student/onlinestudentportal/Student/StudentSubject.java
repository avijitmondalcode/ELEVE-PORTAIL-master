package com.student.onlinestudentportal.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminNoticeList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentSubject extends AppCompatActivity {

    TextView tvSubject1,tvSubject2,tvSubject3,tvSubject4,tvSubject5;

    private List<StudentSubjectList> studentSubjectLists;
    private StudentSubjectAdapter studentSubjectAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course,department,semester,subject1,subject2,subject3,subject4,subject5,roll,regno;
    TextView tvFacultyExamSubjectName,tvFacultyExamSubjectCourse,tvFacultyExamSubjectDepartment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        studentSubjectLists=new ArrayList<>();
        studentSubjectAdapter=new StudentSubjectAdapter(studentSubjectLists);

        tvSubject1=findViewById(R.id.tvSubject1);
        tvSubject2=findViewById(R.id.tvSubject2);
        tvSubject3=findViewById(R.id.tvSubject3);
        tvSubject4=findViewById(R.id.tvSubject4);
        tvSubject5=findViewById(R.id.tvSubject5);



        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        semester=getIntent().getStringExtra("Semester");
        subject1=getIntent().getStringExtra("Subject1");
        subject2=getIntent().getStringExtra("Subject2");
        subject3=getIntent().getStringExtra("Subject3");
        subject4=getIntent().getStringExtra("Subject4");
        subject5=getIntent().getStringExtra("Subject5");
        roll=getIntent().getStringExtra("Roll");
        regno=getIntent().getStringExtra("Reg_No");


        tvSubject1.setText(subject1);
        tvSubject2.setText(subject2);
        tvSubject3.setText(subject3);
        tvSubject4.setText(subject4);
        tvSubject5.setText(subject5);

        tvSubject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), StudentQuestionSet.class);
                j.putExtra("Subject_Name", tvSubject1.getText().toString());
                j.putExtra("Course", course);
                j.putExtra("Department", department);
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);
                j.putExtra("Reg_No",regno);
                startActivity(j);
            }
        });
        tvSubject2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), StudentQuestionSet.class);
                j.putExtra("Subject_Name", tvSubject2.getText().toString());
                j.putExtra("Course", course);
                j.putExtra("Department", department);
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);
                startActivity(j);
            }
        });
        tvSubject3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), StudentQuestionSet.class);
                j.putExtra("Subject_Name", tvSubject3.getText().toString());
                j.putExtra("Course", course);
                j.putExtra("Department", department);
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);
                startActivity(j);
            }
        });
        tvSubject4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), StudentQuestionSet.class);
                j.putExtra("Subject_Name", tvSubject4.getText().toString());
                j.putExtra("Course", course);
                j.putExtra("Department", department);
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);
                startActivity(j);
            }
        });
        tvSubject5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), StudentQuestionSet.class);
                j.putExtra("Subject_Name", tvSubject5.getText().toString());
                j.putExtra("Course", course);
                j.putExtra("Department", department);
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);
                startActivity(j);
            }
        });


    }


}