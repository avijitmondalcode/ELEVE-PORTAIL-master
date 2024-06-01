package com.student.onlinestudentportal.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.FacultyExam;
import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.MainActivity;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.StudentAttendance;
import com.student.onlinestudentportal.faculty.FacultyFinalAttendance;
import com.student.onlinestudentportal.faculty.FacultyNotice;
import com.student.onlinestudentportal.faculty.FacultyStudyMaterial;

import org.jetbrains.annotations.NotNull;

public class StudentDashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    CardView checkNotice, checkMarks, cardStudentExam, notes, classSchedule, examSchedule,cardStudentAttendance;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course,department,semester,roll,name,regno;

    String subject1,subject2,subject3,subject4,subject5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        drawerLayout = findViewById(R.id.student_drawer_layout);
        navigationView = findViewById(R.id.nav_student_view);
        toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        cardStudentAttendance=findViewById(R.id.cardStudentAttendance);
        checkNotice = findViewById(R.id.cardStudentNotice);
        cardStudentExam = findViewById(R.id.cardStudentExam);
        notes = findViewById(R.id.cardStudentStudyMaterial);
//        checkMarks.setOnClickListener(this);
        checkNotice.setOnClickListener(this);
        cardStudentExam.setOnClickListener(this);
        notes.setOnClickListener(this);
        cardStudentAttendance.setOnClickListener(this);
//        examSchedule.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //student data
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();




        fire.collection("Approve_Student").document(UserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    GlobalData.StudentName =task.getResult().getString("Name");
                    course=task.getResult().getString("Course");
                    department=task.getResult().getString("Department");
                    semester=task.getResult().getString("Semester");
                    roll=task.getResult().getString("Roll");
                    regno=task.getResult().getString("Reg_Name");

                    subject1=task.getResult().getString("Subject1");
                    subject2=task.getResult().getString("Subject2");
                    subject3=task.getResult().getString("Subject3");
                    subject4=task.getResult().getString("Subject4");
                    subject5=task.getResult().getString("Subject5");
                    Log.e("DATA",course+department+semester+roll+subject1+subject2+subject3+subject4+subject5);
                }
            }
        });








    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardStudentAttendance:
                i= new Intent(this,StudentSubjectAttendance.class);


                        i.putExtra("Course", course);
                        i.putExtra("Department",department);
                        i.putExtra("Semester",semester);
                        i.putExtra("Roll",roll);


                startActivity(i);

                break;
            case R.id.cardStudentNotice:
                i = new Intent(this, StudentNotice.class);
                startActivity(i);
                break;
            case R.id.cardStudentExam:
                i= new Intent(this,StudentSubject.class);


                i.putExtra("Course", course);
                i.putExtra("Department",department);
                i.putExtra("Semester",semester);
                i.putExtra("Roll",roll);
                i.putExtra("Subject1",subject1);
                i.putExtra("Subject2",subject2);
                i.putExtra("Subject3",subject2);
                i.putExtra("Subject4",subject3);
                i.putExtra("Subject5",subject5);
                i.putExtra("Reg_No",regno);


                startActivity(i);

                break;
            case R.id.cardStudentStudyMaterial:
                i = new Intent(this, StudentStudyMaterial.class);
                startActivity(i);
                break;
           /* case R.id.cardCheckClassSchedule:
                i= new Intent(this,StudentClass.class);
                startActivity(i);
                break;*/
            /*case R.id.cardCheckExamSchedule:
                i= new Intent(this,StudentExam.class);
                startActivity(i);
                break;*/


        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull   MenuItem item) {
        Intent i;
        switch ((item.getItemId())){
            case R.id.nav_student_home:
                break;
            case R.id.nav_student_profile:
                i=new Intent(getApplicationContext(), StudentProfileDetails.class);
                startActivity(i);
                break;
            case R.id.nav_student_attendance:
                i=new Intent(getApplicationContext(), StudentSubjectAttendance.class);
                startActivity(i);
                break;
            case R.id.nav_student_exam:
                i=new Intent(getApplicationContext(), StudentSubject.class);
                startActivity(i);
                break;
            case R.id.nav_student_study_material:
                i=new Intent(getApplicationContext(), StudentStudyMaterial.class);
                startActivity(i);
                break;
            case R.id.nav_student_notice:
                i=new Intent(getApplicationContext(), StudentNotice.class);
                startActivity(i);
                break;
            case  R.id.nav_student_logout:
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}