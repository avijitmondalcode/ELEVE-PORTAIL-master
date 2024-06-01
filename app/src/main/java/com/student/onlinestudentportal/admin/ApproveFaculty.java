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

public class ApproveFaculty extends AppCompatActivity {
    RecyclerView recyclerApproveFaculty;
    private List<ApproveFacultyList> approveFacultyLists;
    private ApproveFacultyAdapter approveFacultyAdapter;
    FirebaseAuth auth;
    FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_faculty);
        recyclerApproveFaculty=findViewById(R.id.recyclerApproveFaculty);
        auth= FirebaseAuth.getInstance();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        approveFacultyLists=new ArrayList<>();
        approveFacultyAdapter=new ApproveFacultyAdapter(approveFacultyLists);
        recyclerApproveFaculty.setLayoutManager(new LinearLayoutManager(this));
        recyclerApproveFaculty.setAdapter(approveFacultyAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Approve_Faculty").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String faculty_id=doc.getDocument().getId();//get id for a particular notice
                            ApproveFacultyList blogPost = doc.getDocument().toObject(ApproveFacultyList.class).withId(faculty_id);
                            approveFacultyLists.add(blogPost);
                            approveFacultyAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}