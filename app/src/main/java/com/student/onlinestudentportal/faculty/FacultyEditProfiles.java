package com.student.onlinestudentportal.faculty;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyEditProfiles extends AppCompatActivity {

    CircleImageView facultyprofilepic;
    EditText facultyName,facultyMobile,facultyAddress,facultyDOB,facultyEmail;
    Spinner facultyGender;
    private Uri facultyImage=null;
    Button btnfacultySubmit;
    int year,month,day;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String gender;
    String[] facultyGend={"Select Gender","Male","Female","Other"};


    String nameFaculty,dateBirthFaculty,addressFaculty,emailFaculty,FacultyPh,image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_edit_profiles);
        facultyprofilepic=findViewById(R.id.imgFacProf);
        facultyEmail=findViewById(R.id.editFacultyEmail);
        facultyDOB=findViewById(R.id.editFacultyDOB);
        facultyName=findViewById(R.id.editFacultyName);
        facultyMobile=findViewById(R.id.editFacultyMobile);
        btnfacultySubmit=findViewById(R.id.submitFacButton);
        facultyAddress=findViewById(R.id.editFacultyAddress);
        facultyGender=findViewById(R.id.editfacultygender);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,facultyGend);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultyGender.setAdapter(aa);
        facultyGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = facultyGend[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        facultyDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(FacultyEditProfiles.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                facultyDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        fire.collection("Approve_Faculty").document(UserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        //Toast.makeText(getApplicationContext(),"data exist",Toast.LENGTH_LONG).show();
                        nameFaculty = task.getResult().getString("Name");
                        image= task.getResult().getString("Profile_Image");
                        emailFaculty= task.getResult().getString("Email");
                        FacultyPh=task.getResult().getString("Phone");
                        addressFaculty=task.getResult().getString("Address");
                        dateBirthFaculty=task.getResult().getString("Date_Of_Birth");
                        gender=task.getResult().getString("Gender");

                        facultyName.setText(nameFaculty);
                        facultyDOB.setText(dateBirthFaculty);
                        facultyAddress.setText(addressFaculty);
                        facultyEmail.setText(emailFaculty);
                        facultyMobile.setText(FacultyPh);
                        facultyDOB.setText(dateBirthFaculty);
                        facultyGender.setAdapter(aa);

                        RequestOptions placeholder = new RequestOptions();
                        placeholder.placeholder(R.drawable.faculty);
                        Glide.with(FacultyEditProfiles.this).setDefaultRequestOptions(placeholder).load(image).into(facultyprofilepic);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Doesn't Exist",Toast.LENGTH_LONG).show();

                }
            }
        });


        btnfacultySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    StorageReference imagepath= store.child("Profile_Image").child(UserId+".jpg");
                    imagepath.putFile(facultyImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadpath= uri;
                                    Map<String,String> hashmap= new HashMap<>();
                                    hashmap.put("Name",facultyName.getText().toString());
                                    hashmap.put("Phone",facultyMobile.getText().toString());
                                    hashmap.put("Address",facultyAddress.getText().toString());
                                    hashmap.put("Date_Of_Birth",facultyDOB.getText().toString());
                                    hashmap.put("Email",facultyEmail.getText().toString());
                                    hashmap.put("Gender",gender);
                                    hashmap.put("Profile_Image",downloadpath.toString());

                                    fire.collection("Approve_Faculty").document(UserId).set(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Snackbar.make(findViewById(android.R.id.content),"Details Updated Successfully!!",Snackbar.LENGTH_LONG)
                                                        .setAction("Ok", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                            }
                                                        }).show();                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    });
                }


        });

        facultyprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(FacultyEditProfiles.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(FacultyEditProfiles.this);
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
                facultyImage = result.getUri();
                facultyprofilepic.setImageURI(facultyImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}