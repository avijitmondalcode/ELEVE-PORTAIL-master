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

public class NotApproveFaculty extends AppCompatActivity {
    RecyclerView notapprovefacultyrecyclerView;
    private List<NotApproveFacultyList> notApproveFacultyLists;
    private NotApproveFacultyAdapter notApproveFacultyAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_approve_faculty);
        notapprovefacultyrecyclerView=findViewById(R.id.notApproveFacultyRecylerView);
        auth= FirebaseAuth.getInstance();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        notApproveFacultyLists=new ArrayList<>();
        notApproveFacultyAdapter=new NotApproveFacultyAdapter(notApproveFacultyLists);
        notapprovefacultyrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notapprovefacultyrecyclerView.setAdapter(notApproveFacultyAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Not_Approve_Faculty").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String faculty_id=doc.getDocument().getId();//get id for a particular notice
                            NotApproveFacultyList blogPost = doc.getDocument().toObject(NotApproveFacultyList.class).withId(faculty_id);
                            notApproveFacultyLists.add(blogPost);
                            notApproveFacultyAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}