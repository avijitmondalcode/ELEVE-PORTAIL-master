package com.student.onlinestudentportal.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;
import com.student.onlinestudentportal.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyStudyMaterial extends AppCompatActivity {
    ImageView facultymaterial;
    Spinner facultyCourse,facultyDept;
    EditText facultymaterialDescription;
    TextView facultyFileName;
    RecyclerView facultyrecyclerView;
    private List<FacultyStudyMaterialList> facultyPdfLists;
    private FacultyStudyMaterialAdapter faculty_adapter_pdf;

    String ffileName;
    private final int PICK_FILE= 1;
    Uri ffileUri;
    Button uploadmaterial;
    private FirebaseAuth auth;
    private StorageReference store;
    private FirebaseFirestore fire;
    String UserId;
    String course,department;
    String[] studentCourse={"Select Course","BTech","Bsc","Management","Technology","Masters"};
    String[] studentManagement={"Select Department","BBA","BBA(HM)","BBA(HPM)"};
    String[] studentBTech={"Select Department","CSE","IT","ECE","EIE","EE","ME","CE"};
    String[] studentTechnology={"Select Department","BCA","BCS"};
    String[] studentBsc={"Select Department","Chemistry","Physics","Mathematics","BIOLOGY"};
    String[] studentMasters={"Select Department","MCA","MTech","MSc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_study_material);
        facultymaterial=findViewById(R.id.imgFacultyStudyMaterial);
        facultyCourse=findViewById(R.id.spinFacultyCourse);
        facultyDept=findViewById(R.id.spinFacultyDept);
        facultymaterialDescription=findViewById(R.id.etStudyMaterialDescription);
        uploadmaterial=findViewById(R.id.btnUploadStudyMaterial);
        facultyFileName=findViewById(R.id.tvFacultyFileName);
        facultyrecyclerView=findViewById(R.id.facultyrecylerpdf);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        facultyPdfLists=new ArrayList<>();
        faculty_adapter_pdf=new FacultyStudyMaterialAdapter(facultyPdfLists);
        facultyrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        facultyrecyclerView.setAdapter(faculty_adapter_pdf);

        //fetch data from notice table in firestore.
        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("study_material").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){

                            String material_id=doc.getDocument().getId();//get id for a particular material
                            FacultyStudyMaterialList blogPost = doc.getDocument().toObject(FacultyStudyMaterialList.class).withId(material_id);
                            facultyPdfLists.add(blogPost);
                            faculty_adapter_pdf.notifyDataSetChanged();


                        }
                    }
                }
            });
        }
        uploadmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description=facultymaterialDescription.getText().toString();
                if (facultymaterial!=null)
                {
                    StorageReference filePath=store.child("Material_pdf").child(ffileName+".pdf");
                    filePath.putFile(ffileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadPath=uri;
                                    Map<String,Object> hashmap= new HashMap<>();
                                    hashmap.put("Name",ffileName);
                                    hashmap.put("User Id",UserId);
                                    hashmap.put("Course",course);
                                    hashmap.put("Department",department);
                                    hashmap.put("Description",description);
                                    hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                                    hashmap.put("Material_pdf",downloadPath.toString());
                                    fire.collection("study_material").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful())
                                            {
                                                facultyFileName.setText("File Uploaded Successfully");

                                            }
                                            else
                                            {
                                                facultyFileName.setText(task.getException().getMessage());
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
        ArrayAdapter fm = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentCourse);
        fm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultyCourse.setAdapter(fm);
        ArrayAdapter fm1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentBTech);
        fm1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter fm2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentBsc);
        fm2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter fm3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentManagement);
        fm3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter fm4 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentTechnology);
        fm4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter fm5 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentMasters);
        fm5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        facultyCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = facultyCourse.getSelectedItem().toString();
                if(facultyCourse.getSelectedItem().toString().equals("BTech")){
                    facultyDept.setAdapter(fm1);
                }else if (facultyCourse.getSelectedItem().toString().equals("Bsc")){
                    facultyDept.setAdapter(fm2);
                }else if(facultyCourse.getSelectedItem().toString().equals("Management")){
                    facultyDept.setAdapter(fm3);
                }else if (facultyCourse.getSelectedItem().toString().equals("Technology")){
                    facultyDept.setAdapter(fm4);
                }else if(facultyCourse.getSelectedItem().toString().equals("Masters")){
                    facultyDept.setAdapter(fm5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        facultyDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=facultyDept.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        facultymaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });



    }
    private void openFile() {
        Intent file = new Intent(Intent.ACTION_GET_CONTENT);
        file.setType("application/pdf");
        startActivityForResult(Intent.createChooser(file,"select pdf file"),PICK_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_FILE)
        {
            ffileUri = data.getData();
            if (ffileUri.toString().startsWith("content://"))
            {
                Cursor cursor=null;
                cursor=FacultyStudyMaterial.this.getContentResolver().query(ffileUri,null,null,null,null);
                if (cursor!=null && cursor.moveToFirst())
                {
                    ffileName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            else if(ffileUri.toString().startsWith("file://"))
            {
                ffileName=new File(ffileUri.toString()).getName();
            }
            facultyFileName.setText(ffileName);

        }
    }

}
