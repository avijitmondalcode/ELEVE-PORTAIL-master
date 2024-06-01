package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminViewProfileDetails extends AppCompatActivity {

    TextView tvAdminViewName,tvAdminViewPhone,tvAdminViewEmail,tvAdminViewDOB,tvAdminViewGender,tvAdminViewAddress;
    CircleImageView adminViewProfilePic;
    FirebaseAuth auth;
    StorageReference store;
    FirebaseFirestore fire;
    String UserId;
    Uri uri;
    String image,name,phone,email,address,dob,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_profile_details);

        tvAdminViewName=findViewById(R.id.tvAdminViewName);
        tvAdminViewAddress=findViewById(R.id.tvAdminViewAddress);
        tvAdminViewEmail=findViewById(R.id.tvAdminViewEmail);
        tvAdminViewPhone=findViewById(R.id.tvAdminViewPhone);
        tvAdminViewGender=findViewById(R.id.tvAdminViewGender);
        tvAdminViewDOB=findViewById(R.id.tvAdminViewDOB);
        adminViewProfilePic=findViewById(R.id.imgAdminViewProfilePic);

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
                        address = task.getResult().getString("Address");
                        phone = task.getResult().getString("Phone");
                        dob = task.getResult().getString("Date_Of_Birth");
                        gender = task.getResult().getString("Gender");


                        tvAdminViewName.setText(name);
                        tvAdminViewAddress.setText(address);
                        tvAdminViewPhone.setText(phone);
                        tvAdminViewDOB.setText(dob);
                        tvAdminViewGender.setText(gender);

                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.admin);
                        Glide.with(AdminViewProfileDetails.this).setDefaultRequestOptions(placeholder).load(image).into(adminViewProfilePic);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Doesn't Exist",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}