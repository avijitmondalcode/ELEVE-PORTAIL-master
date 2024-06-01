package com.student.onlinestudentportal.Student;

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
import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentQuestionSet extends AppCompatActivity {
    RecyclerView recyclerStudentQuestionSet;
    public List<StudentQuestionSetList> studentQuestionSetLists;
    private StudentQuestionSetAdapter studentQuestionSetAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    String sub_name,course,department,semester,roll,regno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question_set);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        recyclerStudentQuestionSet=findViewById(R.id.recyclerStudentQuestionSet);
        sub_name=getIntent().getStringExtra("Subject_Name");
        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        semester=getIntent().getStringExtra("Semester");
        GlobalData.roll=getIntent().getStringExtra("Roll");
        GlobalData.regno=getIntent().getStringExtra("Reg_No");

        studentQuestionSetLists=new ArrayList<>();
        studentQuestionSetAdapter=new StudentQuestionSetAdapter(studentQuestionSetLists);
        recyclerStudentQuestionSet.setLayoutManager(new LinearLayoutManager(this));
        recyclerStudentQuestionSet.setAdapter(studentQuestionSetAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Exam")
                    .whereEqualTo("Course",course)
                    .whereEqualTo("Department",department)
                    .whereEqualTo("Semester",semester)
                    .whereEqualTo("Subject_Name",sub_name)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String question_set_id=doc.getDocument().getId();//get id for a particular subject
                            StudentQuestionSetList blogPost = doc.getDocument().toObject(StudentQuestionSetList.class);
                            studentQuestionSetLists.add(blogPost);
                            studentQuestionSetAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }
}