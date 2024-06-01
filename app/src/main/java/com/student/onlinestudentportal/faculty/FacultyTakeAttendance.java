package com.student.onlinestudentportal.faculty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.faculty.FacultyAttendanceAdapter;
import com.student.onlinestudentportal.faculty.FacultyAttendanceList;

import java.util.ArrayList;
import java.util.List;

public class FacultyTakeAttendance extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<FacultyAttendanceList> facultyAttendanceLists;
    private FacultyAttendanceAdapter attendanceAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course, department, semester, subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_take_attendance);
        recyclerView = findViewById(R.id.facultyAttendanceRecylerView);
        course = getIntent().getStringExtra("Course");
        department = getIntent().getStringExtra("Department");
        semester = getIntent().getStringExtra("Semester");
        GlobalData.Subject_Name = getIntent().getStringExtra("Subject");
        auth = FirebaseAuth.getInstance();
        store = FirebaseStorage.getInstance().getReference();
        fire = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();
        facultyAttendanceLists = new ArrayList<>();
        attendanceAdapter = new FacultyAttendanceAdapter(facultyAttendanceLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(attendanceAdapter);
        if (auth.getCurrentUser() != null) {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Approve_Student").whereEqualTo("Course",course).whereEqualTo("Department",department)
                    .whereEqualTo("Semester",semester)/*.whereEqualTo("Subject1",subject)
                    .whereEqualTo("Subject2",subject).whereEqualTo("Subject3",subject)
                    .whereEqualTo("Subject4",subject).whereEqualTo("Subject5",subject)*/
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String student_attendace_id = doc.getDocument().getId();//get id for a particular notice
                                    FacultyAttendanceList blogPost = doc.getDocument().toObject(FacultyAttendanceList.class).withId(student_attendace_id);
                                    facultyAttendanceLists.add(blogPost);
                                    attendanceAdapter.notifyDataSetChanged();


                                }
                            }
                        }
                    });
        }

    }
}