package com.student.onlinestudentportal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminNoticeList;

import java.util.ArrayList;
import java.util.List;

public class FacultyTakeExam extends AppCompatActivity {
    RecyclerView recyclerFacultyTakeExam;
    private List<FacultyTakeExamList> facultyTakeExamLists;
    private FacultyTakeExamAdapter facultyTakeExamAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    TextView tvFacultyExamSubjectName,tvFacultyExamSubjectCourse,tvFacultyExamSubjectDepartment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_take_exam);
        recyclerFacultyTakeExam=findViewById(R.id.recyclerFacultyTakeExam);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        facultyTakeExamLists=new ArrayList<>();
        facultyTakeExamAdapter=new FacultyTakeExamAdapter(facultyTakeExamLists);
        recyclerFacultyTakeExam.setLayoutManager(new LinearLayoutManager(this));
        recyclerFacultyTakeExam.setAdapter(facultyTakeExamAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Admin_Subject").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String subject_id=doc.getDocument().getId();//get id for a particular subject
                            FacultyTakeExamList blogPost = doc.getDocument().toObject(FacultyTakeExamList.class).withId(subject_id);
                            facultyTakeExamLists.add(blogPost);
                            facultyTakeExamAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}