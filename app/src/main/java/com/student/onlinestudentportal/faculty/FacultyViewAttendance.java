package com.student.onlinestudentportal.faculty;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;

import java.util.ArrayList;
import java.util.List;

public class FacultyViewAttendance extends AppCompatActivity {

    RecyclerView recyclerPresent, recyclerAbsent;
    TextView tvHeader,tvAttendancePresent,tvAttendanceAbsent;
    private List<FacultyAttendanceList> facultyAttendanceLists;
    private List<FacultyAttendanceListAbsent> facultyAttendanceListAbsentList;
    private FacultyViewAttendanceAdapter facultyViewAttendanceAdapter1;
    private FacultyViewAttendanceAdapterAbsent facultyViewAttendanceAdapterAbsent;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course, department, semester, subject, date;
    int presentCount=0,absentCount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_attendance);

        tvHeader=findViewById(R.id.tvAttendanceHeader);
        tvAttendanceAbsent=findViewById(R.id.tvAttendanceAbsent);
        tvAttendancePresent=findViewById(R.id.tvAttendancePresent);

        recyclerPresent = findViewById(R.id.recyclerPresent);
        recyclerAbsent = findViewById(R.id.recyclerAbsent);
        course = getIntent().getStringExtra("Course");
        department = getIntent().getStringExtra("Department");
        semester = getIntent().getStringExtra("Semester");
        subject = getIntent().getStringExtra("Subject");
        date = getIntent().getStringExtra("Date");
        auth = FirebaseAuth.getInstance();
        store = FirebaseStorage.getInstance().getReference();
        fire = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();
        facultyAttendanceLists = new ArrayList<>();
        facultyAttendanceListAbsentList= new ArrayList<>();

        facultyViewAttendanceAdapter1 = new FacultyViewAttendanceAdapter(facultyAttendanceLists);
        recyclerPresent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerPresent.setAdapter(facultyViewAttendanceAdapter1);

        facultyViewAttendanceAdapterAbsent = new FacultyViewAttendanceAdapterAbsent(facultyAttendanceListAbsentList);
        recyclerAbsent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerAbsent.setAdapter(facultyViewAttendanceAdapterAbsent);

        if (auth.getCurrentUser() != null) {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Attendance").
                    whereEqualTo("Course", course).whereEqualTo("Department", department).
                    whereEqualTo("Semester", semester).whereEqualTo("Subject",subject).whereEqualTo("Attendance", "Present")
                    .whereEqualTo("Time_Stamp",date)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String student_attendace_id = doc.getDocument().getId();//get id for a particular notice
                                    FacultyAttendanceList blogPost = doc.getDocument().toObject(FacultyAttendanceList.class).withId(student_attendace_id);
                                    facultyAttendanceLists.add(blogPost);
                                    facultyViewAttendanceAdapter1.notifyDataSetChanged();


                                }
                            }
                        }
                    });

            fire.collection("Attendance")
                    .whereEqualTo("Course", course).whereEqualTo("Department", department)
                    .whereEqualTo("Subject",subject)
                    .whereEqualTo("Semester", semester)
                    .whereEqualTo("Attendance", "Absent")
                    .whereEqualTo("Time_Stamp",date)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    //String student_attendace_id = doc.getDocument().getId();//get id for a particular notice
                                    FacultyAttendanceListAbsent blogPost = doc.getDocument().toObject(FacultyAttendanceListAbsent.class);
                                    facultyAttendanceListAbsentList.add(blogPost);
                                    facultyViewAttendanceAdapterAbsent.notifyDataSetChanged();


                                }
                            }
                        }
                    });
            fire.collection("Attendance")
                    .whereEqualTo("Course", course).whereEqualTo("Department", department)
                    .whereEqualTo("Semester", semester).whereEqualTo("Subject",subject)
                    .whereEqualTo("Time_Stamp",date)
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
                                tvAttendanceAbsent.setText(String.valueOf(absentCount));
                            }
                            if (documentSnapshot.getString("Attendance").equals("Present"))
                            {
                                presentCount++;
                                tvAttendancePresent.setText(String.valueOf(presentCount));
                            }
                        }
                    }
                }
            });
        }
        tvHeader.setText("Course: "+course+ "\n\n" +"Department: "+department+"\n\n"+"Subject: "+subject+"\n\n"+"Semester: "+semester+"\n\n"+"Date: "+date+"\n");
    }
}