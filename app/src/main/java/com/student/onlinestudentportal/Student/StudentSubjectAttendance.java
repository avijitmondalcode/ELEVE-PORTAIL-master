package com.student.onlinestudentportal.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentSubjectAttendance extends AppCompatActivity {
    RecyclerView recyclerStudentAttendanceSubject;
    private List<StudentSubjectList> studentSubjectLists;
    private StudentSubjectAttendanceAdapter studentSubjectAttendanceAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course,department,semester;
    TextView tvFacultyExamSubjectName,tvFacultyExamSubjectCourse,tvFacultyExamSubjectDepartment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject_attendance);
        recyclerStudentAttendanceSubject=findViewById(R.id.recyclerStudentAttendanceSubject);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        studentSubjectLists=new ArrayList<>();
        studentSubjectAttendanceAdapter=new StudentSubjectAttendanceAdapter(studentSubjectLists);
        recyclerStudentAttendanceSubject.setLayoutManager(new LinearLayoutManager(this));
        recyclerStudentAttendanceSubject.setAdapter(studentSubjectAttendanceAdapter);

        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        semester=getIntent().getStringExtra("Semester");

        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Admin_Subject").whereEqualTo("Course",course).whereEqualTo("Department",department).whereEqualTo("Semester",semester)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for(DocumentChange doc:value.getDocumentChanges()){
                                if(doc.getType() == DocumentChange.Type.ADDED){
                                    StudentSubjectList blogPost = doc.getDocument().toObject(StudentSubjectList.class);
                                    studentSubjectLists.add(blogPost);
                                    studentSubjectAttendanceAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}