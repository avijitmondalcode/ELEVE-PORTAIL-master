package com.student.onlinestudentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.spark.submitbutton.SubmitButton;


import org.jetbrains.annotations.NotNull;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    FloatingActionButton btnKnowMore;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Menu menu;
    SliderView sliderview;
    SubmitButton btnAboutUs;
    int[] images = {R.drawable.slider_1, R.drawable.slider_2, R.drawable.slider_3, R.drawable.slider_4,
            R.drawable.slider_5,R.drawable.slider_6,R.drawable.slider_7,R.drawable.slider_8,R.drawable.slider_9,
            R.drawable.slider_10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sliderview = findViewById(R.id.image_slider);
        btnAboutUs=findViewById(R.id.btnAboutUs);
        drawerLayout = findViewById(R.id.home_drawer_layout);
        navigationView = findViewById(R.id.nav_home_view);
        navigationView.bringToFront();

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderview.setSliderAdapter(sliderAdapter);
        sliderview.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderview.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderview.startAutoCycle();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_front_home);
        btnKnowMore=findViewById(R.id.btnKnow);
        btnKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AboutElevePortail.class);
                startActivity(i);
            }
        });
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3500);
                            Intent i = new Intent(Home.this, AboutUs.class);
                            startActivity(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch ((item.getItemId())) {
            case R.id.nav_front_home:
                break;
            case R.id.nav_front_login:
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
            case R.id.nav_front_aboutus:
                i = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(i);
                break;
            case R.id.nav_front_contactus:
                i = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(i);
                break;
            case R.id.nav_front_courses:
                i = new Intent(getApplicationContext(), Courses.class);
                startActivity(i);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cardBtech:
                i = new Intent(this, BtechDesc.class);
                startActivity(i);
                break;
            case R.id.cardBsc:
                i = new Intent(this, BscDesc.class);
                startActivity(i);
                break;
            case R.id.cardManagement:
                i = new Intent(this, ManagementDesc.class);
                startActivity(i);
                break;
            case R.id.cardMasters:
                i = new Intent(this, Masters_Desc.class);
                startActivity(i);
                break;

        }
    }
}

