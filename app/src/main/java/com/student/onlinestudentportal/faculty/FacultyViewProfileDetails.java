package com.student.onlinestudentportal.faculty;

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
import com.student.onlinestudentportal.admin.ApproveFacultyAdapter;
import com.student.onlinestudentportal.admin.ApproveFacultyList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyViewProfileDetails extends AppCompatActivity {

    private List<ApproveFacultyList> approveFacultyLists;
    private ApproveFacultyAdapter approveFacultyAdapter;
    TextView tvFacultyViewName,tvFacultyViewPhone,tvFacultyViewEmail,tvFacultyViewDOB,tvFacultyViewGender,tvFacultyViewAddress;
    CircleImageView FacultyViewProfilePic;
    FirebaseAuth auth;
    StorageReference store;
    FirebaseFirestore fire;
    String UserId;
    Uri uri;
    String image,name,phone,email,address,dob,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_profile_details);

        tvFacultyViewName=findViewById(R.id.tvFacultyViewName);
        tvFacultyViewAddress=findViewById(R.id.tvFacultyViewAddress);
        tvFacultyViewEmail=findViewById(R.id.tvFacultyViewEmail);
        tvFacultyViewPhone=findViewById(R.id.tvFacultyViewPhone);
        tvFacultyViewGender=findViewById(R.id.tvFacultyViewGender);
        tvFacultyViewDOB=findViewById(R.id.tvFacultyViewDOB);
        FacultyViewProfilePic=findViewById(R.id.imgfacultyViewProfilePic);

        approveFacultyLists=new ArrayList<>();
        approveFacultyAdapter=new ApproveFacultyAdapter(approveFacultyLists);

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
                        address = task.getResult().getString("Address");
                        phone = task.getResult().getString("Phone");
                        dob = task.getResult().getString("Date_Of_Birth");
                        gender = task.getResult().getString("Gender");
                        email=task.getResult().getString("Email");

                        tvFacultyViewName.setText(name);
                        tvFacultyViewAddress.setText(address);
                        tvFacultyViewPhone.setText(phone);
                        tvFacultyViewDOB.setText(dob);
                        tvFacultyViewGender.setText(gender);
                        tvFacultyViewEmail.setText(email);


                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.faculty);
                        Glide.with(FacultyViewProfileDetails.this).setDefaultRequestOptions(placeholder).load(image).into(FacultyViewProfilePic);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Doesn't Exist",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}