package com.student.onlinestudentportal;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.Student.StudentDashboard;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileDetails extends AppCompatActivity {
   CircleImageView profilepic;
   EditText userName,userMobile,userAddress,userDOB,userRegistration;
   Spinner userGender;
   private Uri userImage=null;
   Button btnSubmit;
   int year,month,day;
   private FirebaseAuth auth;
   private StorageReference store;
   private FirebaseFirestore fire;
   String UserId;
   String gender;
   String[] userGend={"Male","Female","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_details);
        profilepic=findViewById(R.id.cirUserProf);
        userDOB=findViewById(R.id.editTextDOB);
        userRegistration=findViewById(R.id.userReg);
        userName=findViewById(R.id.editTextName);
        userMobile=findViewById(R.id.editTextMobile);
        btnSubmit=findViewById(R.id.submitButton);
        userAddress=findViewById(R.id.editTextAddress);
        userGender=findViewById(R.id.gender);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,userGend);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userGender.setAdapter(aa);

        userGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = userGend[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UserProfileDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                userDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = userName.getText().toString();
                String dateBirth = userDOB.getText().toString();
                String addressUser = userAddress.getText().toString();
                String userPh = userMobile.getText().toString();
                String userRegister = userRegistration.getText().toString();
                if (!TextUtils.isEmpty(nameUser) && !TextUtils.isEmpty(dateBirth) && !TextUtils.isEmpty(addressUser) && !TextUtils.isEmpty(userPh) && !TextUtils.isEmpty(userRegister)&& userImage!=null)
                {
                 StorageReference imagepath= store.child("ProfileImage").child(UserId+".jpg");
                 imagepath.putFile(userImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         imagepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 Uri downloadpath= uri;
                                 Map<String,String> hashmap= new HashMap<>();
                                 hashmap.put("Name",nameUser);
                                 hashmap.put("Phone",userPh);
                                 hashmap.put("Address",addressUser);
                                 hashmap.put("Date_Of_Birth",dateBirth);
                                 hashmap.put("Registration_No",userRegister);
                                 hashmap.put("Gender",gender);
                                 hashmap.put("Profile_Image",downloadpath.toString());

                                 fire.collection("users").document(UserId).set(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if(task.isSuccessful()){
                                             Intent i= new Intent(UserProfileDetails.this, StudentDashboard.class);
                                             startActivity(i);
                                         }
                                         else{
                                             btnSubmit.requestFocus();
                                             btnSubmit.setError("Fields cannot be Empty");
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

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(UserProfileDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(UserProfileDetails.this);
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                userImage = result.getUri();
                profilepic.setImageURI(userImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}