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

public class StudentStudyMaterial extends AppCompatActivity {
    RecyclerView studentstudyrecyclerView;
    private List<StudentStudyMaterialList> studentStudyMaterialLists;
    private StudentStudyMaterialAdapter studentStudyMaterialAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_study_material);
        studentstudyrecyclerView=findViewById(R.id.studentStudyMaterialRecylerView);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        studentStudyMaterialLists=new ArrayList<>();
        studentStudyMaterialAdapter=new StudentStudyMaterialAdapter(studentStudyMaterialLists);
        studentstudyrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentstudyrecyclerView.setAdapter(studentStudyMaterialAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            //Fetch data from Cloud Fire Storage
            fire.collection("study_material").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String material_id=doc.getDocument().getId();//get id for a particular notice
                            StudentStudyMaterialList blogPost = doc.getDocument().toObject(StudentStudyMaterialList.class).withId(material_id);;
                            studentStudyMaterialLists.add(blogPost);
                            studentStudyMaterialAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}