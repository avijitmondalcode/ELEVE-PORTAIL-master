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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentSignUp extends AppCompatActivity {

    private EditText etMail, etPass, etName, studentPhone, studentAddress, studentDOB,etReg,etRoll;
    //CircleImageView studentprofilepic;
    FloatingActionButton btnSignUp;
    Spinner studentGender,studentCourse,studentDepartment,studentSemester;
    private Uri studentImage = null;
    int year, month, day;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    private StorageReference store;
    String UserId;
    String gender;
    String course, department,semester;
    String[] studentCour = {"Select Course", "BTech", "Bsc", "Management", "Technology", "Masters"};
    String[] studentBTech = {"Select Department", "CSE", "IT", "ECE", "EIE", "EE", "ME", "CE"};
    String[] studentBsc = {"Select Department", "Chemistry", "Physics", "Mathematics", "BIOLOGY"};
    String[] studentManagement = {"Select Department", "BBA", "BBA(HM)"};
    String[] studentTechnology = {"Select Department", "BCA", "BCS"};
    String[] studentMasters = {"Select Department", "MCA", "MBA"};

    String[] studentSem = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};

    String[] studentGend = {"Select Gender","Male", "Female", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        etName = findViewById(R.id.etStudentName);
        etReg=findViewById(R.id.etStudentRegistration);
        etRoll=findViewById(R.id.etStudentRollNo);
        etMail = findViewById(R.id.etStudentEmail);
       // studentprofilepic = findViewById(R.id.imgStudentProfile);
        etPass = findViewById(R.id.etStudentPassword);
        btnSignUp = findViewById(R.id.btnStudentSignUp);
        studentPhone = findViewById(R.id.etStudentPhone);
        studentAddress = findViewById(R.id.etStudentAddress);
        studentDOB = findViewById(R.id.etStudentDOB);
        store= FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        //UserId = auth.getCurrentUser().getUid();

        studentGender = findViewById(R.id.studentgender);

        studentCourse = findViewById(R.id.studentCourse);
        studentDepartment = findViewById(R.id.studentDepartment);
        studentSemester=findViewById(R.id.studentSemester);

        //Initializing The adapters for Spinner
        ArrayAdapter cour = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentCour);
        cour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentCourse.setAdapter(cour);

        //Adapter for Spinner Departments
        ArrayAdapter dept1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBTech);
        dept1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentDepartment.setAdapter(dept1);

        ArrayAdapter dept2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBsc);
        dept2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentDepartment.setAdapter(dept2);

        ArrayAdapter dept3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentManagement);
        dept3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentDepartment.setAdapter(dept3);

        ArrayAdapter dept4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentTechnology);
        dept4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentDepartment.setAdapter(dept4);

        ArrayAdapter dept5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentMasters);
        dept5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentDepartment.setAdapter(dept5);
        //Adapter for Spinner Departments

        studentCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course =studentCourse.getSelectedItem().toString();
                if(studentCourse.getSelectedItem().toString().equals("BTech")){
                    studentDepartment.setAdapter(dept1);
                }else if (studentCourse.getSelectedItem().toString().equals("Bsc")){
                    studentDepartment.setAdapter(dept2);
                }else if(studentCourse.getSelectedItem().toString().equals("Management")){
                    studentDepartment.setAdapter(dept3);
                }else if (studentCourse.getSelectedItem().toString().equals("Technology")){
                    studentDepartment.setAdapter(dept4);
                }else if(studentCourse.getSelectedItem().toString().equals("Masters")){
                    studentDepartment.setAdapter(dept5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        studentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=studentDepartment.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentGend);
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


        ArrayAdapter ab = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentSem);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentSemester.setAdapter(ab);
        studentSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = studentSem[position];
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentSignUp.this,
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


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStudent = etName.getText().toString().trim();
                String emailStudent = etMail.getText().toString().trim();
                String passwordStudent = etPass.getText().toString().trim();
                String dateBirthStudent = studentDOB.getText().toString().trim();
                String addressStudent = studentAddress.getText().toString().trim();
                String StudentPh = studentPhone.getText().toString().trim();
                String StudentReg= etReg.getText().toString().trim();
                String StudentRoll= etRoll.getText().toString().trim();

                final Pattern PASSWORD_PATTERN =

                        Pattern.compile("^" +

                                "(?=.*[@#$%^&+=])" +     // at least 1 special character

                                "(?=\\S+$)" +            // no white spaces

                                ".{8,}" +                // at least 8 characters

                                "$");

                if (emailStudent.isEmpty()) {
                    etMail.setError("Field can not be empty");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailStudent).matches()) {
                    etMail.requestFocus();
                    etMail.setError("Please enter a valid email address");
                    return;
                }

                if (passwordStudent.isEmpty()) {
                    etPass.setError("Field can not be empty");
                    return;
                }

                if (!PASSWORD_PATTERN.matcher(passwordStudent).matches()) {
                    etPass.requestFocus();
                    etPass.setError("Password is too weak");
                    return;
                }
                if (!TextUtils.isEmpty(nameStudent) && !TextUtils.isEmpty(passwordStudent)
                        && !TextUtils.isEmpty(dateBirthStudent) && !TextUtils.isEmpty(emailStudent)
                        && !TextUtils.isEmpty(addressStudent) && !TextUtils.isEmpty(StudentPh)
                        && !TextUtils.isEmpty(StudentReg) && !TextUtils.isEmpty(StudentRoll)) {
                    Map<String, Object> hashmap = new HashMap<>();
                    hashmap.put("Name", etName.getText().toString());
                    hashmap.put("Registration_No",etReg.getText().toString());
                    hashmap.put("Roll_No",etRoll.getText().toString());
                    hashmap.put("Phone", studentPhone.getText().toString());
                    hashmap.put("Address", studentAddress.getText().toString());
                    hashmap.put("Email", etMail.getText().toString());
                    hashmap.put("Password", etPass.getText().toString());
                    hashmap.put("Date_Of_Birth", studentDOB.getText().toString());
                    hashmap.put("Gender", gender);
                    hashmap.put("Course",course);
                    hashmap.put("Department",department);
                    hashmap.put("Semester",semester);
                   // hashmap.put("Profile_Image", downloadpath.toString());
                    fire.collection("Not_Approve_Student").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(StudentSignUp.this, "Wait for Verification Mail", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                Toast.makeText(StudentSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(StudentSignUp.this, "Error 404", Toast.LENGTH_SHORT).show();
                }
            }
        });
/*
        studentprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(StudentSignUp.this, new String[]
                                {
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(StudentSignUp.this);
                    }
                }
            }
        });
*/
    }

/*
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
*/

}
