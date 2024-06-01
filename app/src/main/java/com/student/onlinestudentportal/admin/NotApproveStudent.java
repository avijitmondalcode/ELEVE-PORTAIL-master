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

public class NotApproveStudent extends AppCompatActivity {
    RecyclerView notapprovestudentrecyclerView;
    private List<NotApproveStudentList> notApproveStudentLists;
    private NotApproveStudentAdapter notApproveStudentAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_approve_student);
        notapprovestudentrecyclerView=findViewById(R.id.notApproveStudentRecylerView);
        auth= FirebaseAuth.getInstance();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        notApproveStudentLists=new ArrayList<>();
        notApproveStudentAdapter=new NotApproveStudentAdapter(notApproveStudentLists);
        notapprovestudentrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notapprovestudentrecyclerView.setAdapter(notApproveStudentAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Not_Approve_Student").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String student_id=doc.getDocument().getId();//get id for a particular not approve student
                            NotApproveStudentList blogPost = doc.getDocument().toObject(NotApproveStudentList.class).withId(student_id);
                            notApproveStudentLists.add(blogPost);
                            notApproveStudentAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}