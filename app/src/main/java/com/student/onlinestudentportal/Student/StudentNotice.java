package com.student.onlinestudentportal.Student;

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

import java.util.ArrayList;
import java.util.List;

public class StudentNotice extends AppCompatActivity {
    RecyclerView studentnoticerecyclerView;
    private List<StudentNoticeList> pdfLists;
    private StudentNoticeAdapter adadpter_pdf;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notice);
        studentnoticerecyclerView=findViewById(R.id.studentNoticeRecylerView);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        pdfLists=new ArrayList<>();
        adadpter_pdf=new StudentNoticeAdapter(pdfLists);
        studentnoticerecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentnoticerecyclerView.setAdapter(adadpter_pdf);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            //Fetch data from Cloud Fire Storage
            fire.collection("notice_std").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            StudentNoticeList blogPost = doc.getDocument().toObject(StudentNoticeList.class);
                            pdfLists.add(blogPost);
                            adadpter_pdf.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}