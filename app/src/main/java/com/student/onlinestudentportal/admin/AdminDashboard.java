package com.student.onlinestudentportal.admin;

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
import android.widget.ImageView;
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
import com.student.onlinestudentportal.FacultyDetails;
import com.student.onlinestudentportal.MainActivity;
import com.student.onlinestudentportal.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    CardView studentDetails, classSchedule, cardAdminNotice, examSchedule, facultyDetails,cardAdminSubject,cardAdminCourse;
    DrawerLayout drawerLayout;

    TextView adminHeaderName,adminHeaderPhone;
    CircleImageView adminHeaderProfImg;
    NavigationView navigationView;
    View headerView;

    Toolbar toolbar;
    Menu menu;

    FirebaseAuth auth;
    StorageReference store;
    FirebaseFirestore fire;
    String UserId;
    Uri uri;
    String image,name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        cardAdminCourse=findViewById(R.id.cardAdminCourse);
        cardAdminSubject=findViewById(R.id.cardAdminSubject);
        studentDetails = findViewById(R.id.cardStudentDetails);
        //classSchedule = findViewById(R.id.cardClassSchedule);
        cardAdminNotice = findViewById(R.id.cardAdminNotice);
       // examSchedule = findViewById(R.id.cardExamSchedule);
       // feesSection = findViewById(R.id.cardAdminSubject);
        facultyDetails = findViewById(R.id.cardFacultyDetails);
        drawerLayout = findViewById(R.id.admin_drawer_layout);

        navigationView = findViewById(R.id.nav_admin_view);
        headerView= navigationView.inflateHeaderView(R.layout.nav_header_admin_navigation);
        adminHeaderName= headerView.findViewById(R.id.adminHeaderName);
        adminHeaderPhone=headerView.findViewById(R.id.adminHeaderPhone);
        adminHeaderProfImg=headerView.findViewById(R.id.adminHeaderProfImg);


        toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        studentDetails.setOnClickListener(this);
        //classSchedule.setOnClickListener(this);
        //examSchedule.setOnClickListener(this);
        facultyDetails.setOnClickListener(this);
        cardAdminNotice.setOnClickListener(this);
        cardAdminCourse.setOnClickListener(this);
        cardAdminSubject.setOnClickListener(this);
        //feesSection.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_admin_home);

        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();


        fire.collection("admins").document(UserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        //Toast.makeText(getApplicationContext(),"data exist",Toast.LENGTH_LONG).show();
                        name = task.getResult().getString("Name");
                        image = task.getResult().getString("Profile_Image");
                        phone = task.getResult().getString("Phone");


                        adminHeaderName.setText(name);
                        adminHeaderPhone.setText(phone);


                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.admin);
                        Glide.with(AdminDashboard.this).setDefaultRequestOptions(placeholder).load(image).into(adminHeaderProfImg);
                    }
                }
                else
                    {
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
        switch (v.getId()) {
            case R.id.cardStudentDetails:
                i = new Intent(this, StudentDetails.class);
                startActivity(i);
                break;
//            case R.id.cardClassSchedule:
//                i= new Intent(this, ClassSchedule.class);
//                startActivity(i);
//                break;
            case R.id.cardAdminSubject:
                i = new Intent(this, AdminSubject.class);
                startActivity(i);
                break;
            case R.id.cardAdminCourse:
                i = new Intent(this, AdminCourse.class);
                startActivity(i);
                break;
//            case R.id.cardExamSchedule:
//                i= new Intent(this, ExamSchedule.class);
//                startActivity(i);
//                break;
            case R.id.cardAdminNotice:
                i = new Intent(this, AdminNotice.class);
                startActivity(i);
                break;
            case R.id.cardFacultyDetails:
                i = new Intent(this, FacultyDetails.class);
                startActivity(i);
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull   MenuItem item) {
        Intent i;
        switch ((item.getItemId())){
            case R.id.nav_admin_home:
                break;
            case R.id.nav_admin_profile:
                i=new Intent(getApplicationContext(),AdminProfileDetails.class);
                startActivity(i);
                break;
            case R.id.nav_admin_student_details:
                i=new Intent(getApplicationContext(), StudentDetails.class);
                startActivity(i);
                break;
            case R.id.nav_admin_faculty_details:
                i=new Intent(getApplicationContext(), FacultyDetails.class);
                startActivity(i);
                break;
            case R.id.nav_admin_subject:
                i=new Intent(getApplicationContext(), AdminSubject.class);
                startActivity(i);
                break;
            case R.id.nav_admin_notice:
                i=new Intent(getApplicationContext(), AdminNotice.class);
                startActivity(i);
                break;
            case R.id.nav_admin_logout:
                auth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
