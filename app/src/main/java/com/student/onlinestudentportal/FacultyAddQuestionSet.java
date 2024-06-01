package com.student.onlinestudentportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyAddQuestionSet extends AppCompatActivity {
    EditText etExamHeading;
    Button btnAddExam;
    RecyclerView recyclerFacultyAddQuestionSet;
    public List<FacultyAddQuestionSetList> facultyAddQuestionSetLists;
    private FacultyAddQuestionSetAdapter facultyAddQuestionSetAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    String sub_name,course,department,semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_add_question_set);

        etExamHeading=findViewById(R.id.etExamHeading);
        btnAddExam=findViewById(R.id.btnAddExam);

        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        recyclerFacultyAddQuestionSet=findViewById(R.id.recyclerFacultyAddQuestionSet);
        sub_name=getIntent().getStringExtra("Subject_Name");
        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        semester=getIntent().getStringExtra("Semester");
        String subject_id=getIntent().getStringExtra("SubjectId");

        facultyAddQuestionSetLists=new ArrayList<>();
        facultyAddQuestionSetAdapter=new FacultyAddQuestionSetAdapter(facultyAddQuestionSetLists);
        recyclerFacultyAddQuestionSet.setLayoutManager(new LinearLayoutManager(this));
        recyclerFacultyAddQuestionSet.setAdapter(facultyAddQuestionSetAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Exam").whereEqualTo("Course",course)
                    .whereEqualTo("Department",department).whereEqualTo("Subject_Name",sub_name)
                    .whereEqualTo("Semester",semester)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){

                            String question_set_id=doc.getDocument().getId();//get id for a particular subject
                            FacultyAddQuestionSetList blogPost = doc.getDocument().toObject(FacultyAddQuestionSetList.class).withId(question_set_id);
                            facultyAddQuestionSetLists.add(blogPost);
                            facultyAddQuestionSetAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        btnAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> hashmap= new HashMap<>();
                hashmap.put("Subject_Name",sub_name);// hashmap.put("Table Column/field Name",field Value);
                hashmap.put("Course",course);// hashmap.put("Table Column/field Name",field Value);
                hashmap.put("Department",department);// hashmap.put("Table Column/field Name",field Value);
                hashmap.put("SubjectId",subject_id);
                hashmap.put("Semester",semester);
                hashmap.put("Exam_Heading",etExamHeading.getText().toString());
                hashmap.put("User Id",UserId);
                hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                fire.collection("Exam").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar.make(findViewById(android.R.id.content),"Exam Added Successfully!!",Snackbar.LENGTH_LONG)
                                    .setAction("Ok", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }).show();

                        }
                        else
                        {
                            etExamHeading.setText(task.getException().getMessage());
                        }
                    }
                });

            }
        });
    }
}