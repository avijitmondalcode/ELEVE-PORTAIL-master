package com.student.onlinestudentportal.Student;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentEditProfile extends AppCompatActivity {

    CircleImageView studentprofilepic;
    EditText studentName,studentMobile,studentAddress,studentDOB,studentEmail;
    Spinner studentGender;
    private Uri studentImage=null;
    Button btnstudentSubmit;
    int year,month,day;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String gender;
    String[] studentGend={"Male","Female","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);
        studentprofilepic=findViewById(R.id.imgStudProf);
        studentEmail=findViewById(R.id.editStudentEmail);
        studentDOB=findViewById(R.id.editStudentDOB);
        studentName=findViewById(R.id.editStudentName);
        studentMobile=findViewById(R.id.editStudentMobile);
        btnstudentSubmit=findViewById(R.id.submitStudButton);
        studentAddress=findViewById(R.id.editStudentAddress);
        studentGender=findViewById(R.id.editstudentgender);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentGend);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentGender.setAdapter(aa);
        studentGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = studentGend[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        studentDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentEditProfile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                studentDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnstudentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStudent = studentName.getText().toString();
                String dateBirthStudent = studentDOB.getText().toString();
                String addressStudent = studentAddress.getText().toString();
                String emailFaculty = studentEmail.getText().toString();
                String StudentPh = studentMobile.getText().toString();
                if (!TextUtils.isEmpty(nameStudent) && !TextUtils.isEmpty(dateBirthStudent) && !TextUtils.isEmpty(addressStudent) && !TextUtils.isEmpty(StudentPh)&& !TextUtils.isEmpty(emailFaculty) && studentImage!=null)
                {
                    StorageReference imagepath= store.child("Student_Profile_Image").child(UserId+".jpg");
                    imagepath.putFile(studentImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadpath= uri;
                                    Map<String,String> hashmap= new HashMap<>();
                                    hashmap.put("Name",studentName.getText().toString());
                                    hashmap.put("Phone",studentMobile.getText().toString());
                                    hashmap.put("Address",studentAddress.getText().toString());
                                    hashmap.put("Date_Of_Birth",studentDOB.getText().toString());
                                    hashmap.put("Email",studentEmail.getText().toString());
                                    hashmap.put("Gender",gender);
                                    hashmap.put("Profile_Image",downloadpath.toString());

                                    fire.collection("Approve_Student").document(UserId).set(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
//                                                Intent i= new Intent(FacultyEditProfiles.this, AdminDashboard.class);
//                                                startActivity(i);
                                                Toast.makeText(StudentEditProfile.this,"Details Updated Successfully",Toast.LENGTH_SHORT);
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT);
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

        studentprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(StudentEditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(StudentEditProfile.this);
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
                studentImage = result.getUri();
                studentprofilepic.setImageURI(studentImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}