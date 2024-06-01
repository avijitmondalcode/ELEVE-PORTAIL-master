package com.student.onlinestudentportal.faculty;

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
import android.util.Patterns;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.faculty.FacultySignUp;
import com.student.onlinestudentportal.faculty.FacultyDashboard;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultySignUp extends AppCompatActivity {

    private EditText etMail, etPass, etName, facultyPhone, facultyAddress, facultyDOB;
    CircleImageView facultyprofilepic;
    FloatingActionButton btnSignUp;
    Spinner facultyGender;
    private Uri facultyImage = null;
    int year, month, day;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    private StorageReference store;
    String UserId;
    String gender;
    String[] facultyGend = {"Male", "Female", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_sign_up);
        etName = findViewById(R.id.etFacultyName);
        etMail = findViewById(R.id.etFacultyEmail);
        facultyprofilepic = findViewById(R.id.imgFacultyProfile);
        etPass = findViewById(R.id.etFacultyPassword);
        btnSignUp = findViewById(R.id.btnFacultySignUp);
        facultyPhone = findViewById(R.id.etFacultyPhone);
        facultyAddress = findViewById(R.id.etFacultyAddress);
        facultyDOB = findViewById(R.id.etFacultyDOB);
        store= FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();
        facultyGender = findViewById(R.id.facultygender);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, facultyGend);
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(FacultySignUp.this,
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


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameFaculty = etName.getText().toString().trim();
                String emailFaculty = etMail.getText().toString().trim();
                String passwordFaculty = etPass.getText().toString().trim();
                String dateBirthFaculty = facultyDOB.getText().toString().trim();
                String addressFaculty = facultyAddress.getText().toString().trim();
                String FacultyPh = facultyPhone.getText().toString().trim();

                final Pattern PASSWORD_PATTERN =

                        Pattern.compile("^" +

                                "(?=.*[@#$%^&+=])" +     // at least 1 special character

                                "(?=\\S+$)" +            // no white spaces

                                ".{8,}" +                // at least 8 characters

                                "$");

                if (emailFaculty.isEmpty()) {
                    etMail.setError("Field can not be empty");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailFaculty).matches()) {
                    etMail.requestFocus();
                    etMail.setError("Please enter a valid email address");
                    return;
                }

                if (passwordFaculty.isEmpty()) {
                    etPass.setError("Field can not be empty");
                    return;
                }

                if (!PASSWORD_PATTERN.matcher(passwordFaculty).matches()) {
                    etPass.requestFocus();
                    etPass.setError("Password is too weak");
                    return;
                }
                if (!TextUtils.isEmpty(nameFaculty) && !TextUtils.isEmpty(passwordFaculty) && !TextUtils.isEmpty(dateBirthFaculty) && !TextUtils.isEmpty(emailFaculty) && !TextUtils.isEmpty(addressFaculty) && !TextUtils.isEmpty(FacultyPh) && facultyImage != null) {
                    StorageReference imagepath = store.child("FacultyProfileImage").child(UserId + ".jpg");
                    imagepath.putFile(facultyImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadpath = uri;
                                    Map<String, Object> hashmap = new HashMap<>();
                                    hashmap.put("Name", etName.getText().toString());
                                    hashmap.put("Phone", facultyPhone.getText().toString());
                                    hashmap.put("Address", facultyAddress.getText().toString());
                                    hashmap.put("Email", etMail.getText().toString());
                                    hashmap.put("Password", etPass.getText().toString());
                                    hashmap.put("Date_Of_Birth", facultyDOB.getText().toString());
                                    hashmap.put("Gender", gender);
                                    hashmap.put("Profile_Image", downloadpath.toString());
                                    fire.collection("Not_Approve_Faculty").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(FacultySignUp.this, "Wait for Verification Mail", Toast.LENGTH_SHORT).show();
                                            }
                                            else {

                                                Toast.makeText(FacultySignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }

                    });

                }
                else {
                    Toast.makeText(FacultySignUp.this, "Error 404", Toast.LENGTH_SHORT).show();
                }
            }
        });
        facultyprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FacultySignUp.this, new String[]
                                {
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(FacultySignUp.this);
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
