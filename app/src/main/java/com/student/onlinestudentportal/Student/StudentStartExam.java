package com.student.onlinestudentportal.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.student.onlinestudentportal.R;

public class StudentStartExam extends AppCompatActivity {
    TextView tvStudentExamCourse,tvStudentExamDepartment,tvStudentExamSubject,tvStudentExamExamHeading;
    Button btnStartExam;
    String course,department,subject,exam_heading,semester,roll,regno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_start_exam);
        tvStudentExamCourse=findViewById(R.id.tvStudentExamCourse1);
        tvStudentExamDepartment=findViewById(R.id.tvStudentExamDepartment1);
        tvStudentExamSubject=findViewById(R.id.tvStudentExamSubject1);
        tvStudentExamExamHeading=findViewById(R.id.tvStudentExamExamHeading1);
        btnStartExam=findViewById(R.id.btnStartExam);
        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        subject=getIntent().getStringExtra("Subject_Name");
        exam_heading=getIntent().getStringExtra("Exam_Heading");
        semester=getIntent().getStringExtra("Semester");
        roll=getIntent().getStringExtra("Roll");
        regno=getIntent().getStringExtra("Reg_No");

        tvStudentExamCourse.setText(course);
        tvStudentExamDepartment.setText(department);
        tvStudentExamSubject.setText(subject);
        tvStudentExamExamHeading.setText(exam_heading);
        tvStudentExamExamHeading.setText(semester);


        btnStartExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), StudentExamScreen.class);
                j.putExtra("Subject_Name", subject);
                j.putExtra("Course",course);
                j.putExtra("Department", department);
                j.putExtra("Exam_Heading", exam_heading);
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);
                j.putExtra("Reg_No",regno);
                startActivity(j);
            }
        });





    }
}