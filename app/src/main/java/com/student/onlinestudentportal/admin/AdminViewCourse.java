package com.student.onlinestudentportal.admin;

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

import java.util.ArrayList;
import java.util.List;

public class AdminViewCourse extends AppCompatActivity {

    RecyclerView recyclerAdminViewCourse;
    private List<AdminViewCourseList> adminViewCourseLists;
    private AdminViewCourseAdapter adminViewCourseAdapter;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;

    TextView tvAdminViewSubjectName,tvAdminViewSubjectCourse,tvAdminViewSubjectDepartment;
    Button btnAdminViewSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_course);
        recyclerAdminViewCourse=findViewById(R.id.recyclerAdminViewCourse);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        adminViewCourseLists=new ArrayList<>();
        adminViewCourseAdapter=new AdminViewCourseAdapter(adminViewCourseLists);
        recyclerAdminViewCourse.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdminViewCourse.setAdapter(adminViewCourseAdapter);
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Admin_Course").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String course_id=doc.getDocument().getId();//get id for a particular subject
                            AdminViewCourseList blogPost = doc.getDocument().toObject(AdminViewCourseList.class).withId(course_id);
                            adminViewCourseLists.add(blogPost);
                            adminViewCourseAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}