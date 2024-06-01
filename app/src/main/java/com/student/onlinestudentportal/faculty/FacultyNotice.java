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
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminNoticeList;

import java.util.ArrayList;
import java.util.List;

public class FacultyNotice extends AppCompatActivity {
    RecyclerView facultynoticerecyclerView;
    private List<AdminNoticeList> pdfLists;
    private FacultyNoticeAdapter adadpter_pdf;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_notice);
        facultynoticerecyclerView=findViewById(R.id.facultyNoticeRecylerView);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        pdfLists=new ArrayList<>();
        adadpter_pdf=new FacultyNoticeAdapter(pdfLists);
        facultynoticerecyclerView.setLayoutManager(new LinearLayoutManager(this));
        facultynoticerecyclerView.setAdapter(adadpter_pdf);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("notice").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String notice_id=doc.getDocument().getId();//get id for a particular notice
                            AdminNoticeList blogPost = doc.getDocument().toObject(AdminNoticeList.class).withId(notice_id);
                            pdfLists.add(blogPost);
                            adadpter_pdf.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}