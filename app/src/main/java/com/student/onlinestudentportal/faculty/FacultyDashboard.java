package com.student.onlinestudentportal.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.FacultyExam;
import com.student.onlinestudentportal.MainActivity;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminDashboard;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyDashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    CardView cardFacultyAttendance,caMarks,facultyNotice,takeExam,studyMaterial,result;
    DrawerLayout drawerLayout;

    NavigationView navigationView;
    TextView facultyHeaderName,facultyHeaderEmail;
    CircleImageView facultyHeaderProfImg;
    View headerView;

    Toolbar toolbar;
    Menu menu;

    FirebaseAuth auth;
    StorageReference store;
    FirebaseFirestore fire;
    String UserId;
    Uri uri;
    String image,name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);
        cardFacultyAttendance=findViewById(R.id.cardFacultyAttendance);
        //caMarks=findViewById(R.id.cardCAMarks);
        facultyNotice=findViewById(R.id.cardFacultyNotice);
        takeExam=findViewById(R.id.cardFacultyExam);
        studyMaterial=findViewById(R.id.cardFacultyStudyMaterial);
        drawerLayout = findViewById(R.id.faculty_drawer_layout);

        navigationView = findViewById(R.id.nav_faculty_view);
        headerView= navigationView.inflateHeaderView(R.layout.nav_header_faculty_navigation);
        facultyHeaderName= headerView.findViewById(R.id.facultyHeaderName);
        facultyHeaderEmail=headerView.findViewById(R.id.facultyHeaderEmail);
        facultyHeaderProfImg=headerView.findViewById(R.id.facultyHeaderProfImg);



        navigationView.bringToFront();
        //result=findViewById(R.id.cardResult);
        //studentAttend.setOnClickListener(this);
        //caMarks.setOnClickListener(this);
        cardFacultyAttendance.setOnClickListener(this);
        facultyNotice.setOnClickListener(this);
        takeExam.setOnClickListener(this);
        studyMaterial.setOnClickListener(this);
       // result.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_faculty_home);

        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();


        fire.collection("Approve_Faculty").document(UserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        //Toast.makeText(getApplicationContext(),"data exist",Toast.LENGTH_LONG).show();
                        name = task.getResult().getString("Name");
                        image = task.getResult().getString("Profile_Image");
                        email = task.getResult().getString("Email");


                        facultyHeaderName.setText(name);
                        facultyHeaderEmail.setText(email);


                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.faculty);
                        Glide.with(FacultyDashboard.this).setDefaultRequestOptions(placeholder).load(image).into(facultyHeaderProfImg);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Doesn't Exist",Toast.LENGTH_LONG).show();

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
        switch (v.getId()){
            case R.id.cardFacultyAttendance:
                i= new Intent(this, FacultyFinalAttendance.class);
                startActivity(i);
                break;
            /*case R.id.cardCAMarks:
                i= new Intent(this, CAMarks.class);
                startActivity(i);
                break;*/
            case R.id.cardFacultyNotice:
                i= new Intent(this, FacultyNotice.class);
                startActivity(i);
                break;
            case R.id.cardFacultyExam:
                i= new Intent(this, FacultyExam.class);
                startActivity(i);
                break;
            case R.id.cardFacultyStudyMaterial:
                i= new Intent(this, FacultyStudyMaterial.class);
                startActivity(i);
                break;
            /*case R.id.cardResult:
                i= new Intent(this, Result.class);
                startActivity(i);
                break;
*/


        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull   MenuItem item) {
        Intent i;
        switch ((item.getItemId())){
            case R.id.nav_faculty_home:
                break;
            case R.id.nav_faculty_profile:
                i=new Intent(getApplicationContext(), FacultyProfileDetails.class);
                startActivity(i);
                break;
            case R.id.nav_faculty_attendance:
                i=new Intent(getApplicationContext(), FacultyFinalAttendance.class);
                startActivity(i);
                break;
            case R.id.nav_faculty_exam:
                i=new Intent(getApplicationContext(), FacultyExam.class);
                startActivity(i);
                break;
            case R.id.nav_faculty_study_material:
                i=new Intent(getApplicationContext(), FacultyStudyMaterial.class);
                startActivity(i);
                break;
            case R.id.nav_faculty_notice:
                i=new Intent(getApplicationContext(), FacultyNotice.class);
                startActivity(i);
                break;
            case  R.id.nav_faculty_logout:
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
