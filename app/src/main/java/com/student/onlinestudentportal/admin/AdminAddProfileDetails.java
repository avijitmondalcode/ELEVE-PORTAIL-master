package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.Student.StudentDashboard;
import com.student.onlinestudentportal.UserProfileDetails;
import com.student.onlinestudentportal.faculty.FacultySignUp;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAddProfileDetails extends AppCompatActivity {
    CircleImageView adminprofilepic;
    EditText adminName,adminMobile,adminAddress,adminDOB;
    Spinner adminGender;
    private Uri adminImage=null;
    Button btnadminSubmit;
    int year,month,day;
     FirebaseAuth auth;
     StorageReference store;
     FirebaseFirestore fire;
    String UserId;
    String gender;
    String[] adminGend={"Male","Female","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_profile_details);
        adminprofilepic=findViewById(R.id.cirAdminProf);
        adminDOB=findViewById(R.id.editTextAdminDOB);
        adminName=findViewById(R.id.editTextAdminName);
        adminMobile=findViewById(R.id.editTextAdminMobile);
        btnadminSubmit=findViewById(R.id.submitAdminButton);
        adminAddress=findViewById(R.id.editTextAdminAddress);
        adminGender=findViewById(R.id.admingender);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,adminGend);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminGender.setAdapter(aa);
        adminGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = adminGend[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adminDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddProfileDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                adminDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnadminSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameAdmin = adminName.getText().toString();
                String dateBirthAdmin = adminDOB.getText().toString();
                String addressAdmin = adminAddress.getText().toString();
                String AdminPh = adminMobile.getText().toString();
                if (!TextUtils.isEmpty(nameAdmin) && !TextUtils.isEmpty(dateBirthAdmin) && !TextUtils.isEmpty(addressAdmin) && !TextUtils.isEmpty(AdminPh) && adminImage!=null)
                {
                    StorageReference imagepath= store.child("ProfileImage").child(UserId+".jpg");
                    imagepath.putFile(adminImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadpath= uri;
                                    Map<String,String> hashmap= new HashMap<>();
                                    hashmap.put("Name",nameAdmin);
                                    hashmap.put("Phone",AdminPh);
                                    hashmap.put("Address",addressAdmin);
                                    hashmap.put("Date_Of_Birth",dateBirthAdmin);
                                    hashmap.put("Gender",gender);
                                    hashmap.put("Profile_Image",downloadpath.toString());

                                    fire.collection("admins").document(UserId).set(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AdminAddProfileDetails.this, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
                                                Intent i= new Intent(AdminAddProfileDetails.this, AdminProfileDetails.class);
                                                startActivity(i);
                                            }
                                            else {

                                                Toast.makeText(AdminAddProfileDetails.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    });
                }

            }
        });

        adminprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(AdminAddProfileDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(AdminAddProfileDetails.this);
                    }
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                adminImage = result.getUri();
                adminprofilepic.setImageURI(adminImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}