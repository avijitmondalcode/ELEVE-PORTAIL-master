package com.student.onlinestudentportal.admin;

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
import com.student.onlinestudentportal.R;

import java.util.ArrayList;
import java.util.List;

public class ApproveStudent extends AppCompatActivity {
    RecyclerView recyclerApproveStudent;
    private List<ApproveStudentList> approveStudentLists;
    private ApproveStudentAdapter approveStudentAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_student);
        recyclerApproveStudent=findViewById(R.id.recyclerApproveStudent);
        auth= FirebaseAuth.getInstance();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        approveStudentLists=new ArrayList<>();
        approveStudentAdapter=new ApproveStudentAdapter(approveStudentLists);
        recyclerApproveStudent.setLayoutManager(new LinearLayoutManager(this));
        recyclerApproveStudent.setAdapter(approveStudentAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Approve_Student").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String Student_id=doc.getDocument().getId();//get id for a particular notice
                            ApproveStudentList blogPost = doc.getDocument().toObject(ApproveStudentList.class).withId(Student_id);
                            approveStudentLists.add(blogPost);
                            approveStudentAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}