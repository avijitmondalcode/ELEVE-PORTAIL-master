package com.student.onlinestudentportal.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.faculty.FacultyViewAttendanceAdapter;
import com.student.onlinestudentportal.faculty.FacultyViewAttendanceAdapterAbsent;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentViewAttendance extends AppCompatActivity {

    RecyclerView recyclerStudentPresent, recyclerStudentAbsent;
    TextView tvStudentAttendanceHeader,tvStudentAttendancePresent,tvStudentAttendanceAbsent;
    private List<StudentViewAttendanceList> studentViewAttendanceLists;
    private List<StudentViewAttendanceListAbsent> studentViewAttendanceListAbsents;
    private StudentViewAttendanceAdapter studentViewAttendanceAdapter;
    private StudentViewAttendanceAdapterAbsent studentViewAttendanceAdapterAbsent;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course, department, semester, subject, date,roll;
    int presentCount=0,absentCount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_attendance);

        tvStudentAttendanceHeader=findViewById(R.id.tvStudentAttendanceHeader);
        tvStudentAttendanceAbsent=findViewById(R.id.tvStudentAttendanceAbsent);
        tvStudentAttendancePresent=findViewById(R.id.tvStudentAttendancePresent);

        recyclerStudentPresent = findViewById(R.id.recyclerStudentPresent);
        recyclerStudentAbsent = findViewById(R.id.recyclerStudentAbsent);

        auth = FirebaseAuth.getInstance();
        store = FirebaseStorage.getInstance().getReference();
        fire = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();


        studentViewAttendanceLists = new ArrayList<>();
        studentViewAttendanceListAbsents= new ArrayList<>();

        studentViewAttendanceAdapter = new StudentViewAttendanceAdapter(studentViewAttendanceLists);
        recyclerStudentPresent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerStudentPresent.setAdapter(studentViewAttendanceAdapter);

        studentViewAttendanceAdapterAbsent = new StudentViewAttendanceAdapterAbsent(studentViewAttendanceListAbsents);
        recyclerStudentAbsent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerStudentAbsent.setAdapter(studentViewAttendanceAdapterAbsent);




        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        subject=getIntent().getStringExtra("Subject_Name");
        roll=getIntent().getStringExtra("Roll");
        semester=getIntent().getStringExtra("Semester");
        date = getIntent().getStringExtra("Date");
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String Sysdate=LocalDate.now().format(dateTimeFormatter).toString();


       /* facultyAttendanceLists = new ArrayList<>();
        facultyAttendanceListAbsentList= new ArrayList<>();

        facultyViewAttendanceAdapter1 = new FacultyViewAttendanceAdapter(facultyAttendanceLists);
        recyclerPresent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerPresent.setAdapter(facultyViewAttendanceAdapter1);

        facultyViewAttendanceAdapterAbsent = new FacultyViewAttendanceAdapterAbsent(facultyAttendanceListAbsentList);
        recyclerAbsent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerAbsent.setAdapter(facultyViewAttendanceAdapterAbsent);*/

        if (auth.getCurrentUser() != null) {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Attendance").
                    whereEqualTo("Course", course).whereEqualTo("Department", department).
                    whereEqualTo("Attendance", "Present").whereEqualTo("Semester",semester)
                    .whereEqualTo("Subject",subject)
                    .whereEqualTo("Roll",roll)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String student_attendace_id = doc.getDocument().getId();//get id for a particular notice
                                    StudentViewAttendanceList blogPost = doc.getDocument().toObject(StudentViewAttendanceList.class);
                                    studentViewAttendanceLists.add(blogPost);
                                    studentViewAttendanceAdapter.notifyDataSetChanged();


                                }
                            }
                        }
                    });

            fire.collection("Attendance")
                    .whereEqualTo("Course", course).whereEqualTo("Department", department)
                    .whereEqualTo("Semester", semester).whereEqualTo("Roll",roll)
                    .whereEqualTo("Attendance", "Absent")
                    .whereEqualTo("Subject",subject)

                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String student_attendace_id = doc.getDocument().getId();//get id for a particular notice
                                    StudentViewAttendanceListAbsent blogPost = doc.getDocument().toObject(StudentViewAttendanceListAbsent.class);
                                    studentViewAttendanceListAbsents.add(blogPost);
                                    studentViewAttendanceAdapter.notifyDataSetChanged();


                                }
                            }
                        }
                    });
            fire.collection("Attendance")
                    .whereEqualTo("Course", course).whereEqualTo("Department", department)
                    .whereEqualTo("Subject",subject).whereEqualTo("Roll",roll)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete( Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                        {
                            if (documentSnapshot.getString("Attendance").equals("Absent"))
                            {
                                absentCount++;
                                tvStudentAttendanceAbsent.setText(String.valueOf(absentCount));
                            }
                            if (documentSnapshot.getString("Attendance").equals("Present"))
                            {
                                presentCount++;
                                tvStudentAttendancePresent.setText(String.valueOf(presentCount));
                            }
                        }
                    }
                }
            });
        }
        tvStudentAttendanceHeader.setText("Course: "+course+ "\n" +"Department: "+department+"\n"+"Subject: "+subject+"\n"+"Semester: "+semester+"\n"+"Date: "+Sysdate);
    }
}