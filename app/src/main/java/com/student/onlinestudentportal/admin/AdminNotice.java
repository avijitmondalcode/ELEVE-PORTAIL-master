package com.student.onlinestudentportal.admin;

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

public class AdminNotice extends AppCompatActivity {
    ImageView adminNotice;
    Spinner adminCourse,adminDept;
    EditText noticeDescription;
    TextView tvFileName;
    RecyclerView recyclerView;
    private List<AdminNoticeList> pdfLists;
    private AdminNoticeAdapter adadpter_pdf;

    String fileName;
    private final int PICK_FILE= 1;
    Uri fileUri;
    Button uploadNotice;
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
        setContentView(R.layout.activity_admin_notice);
        adminNotice=findViewById(R.id.imgAdminNotice);
        adminCourse=findViewById(R.id.spinAdminCourse);
        adminDept=findViewById(R.id.spinAdminDept);
        noticeDescription=findViewById(R.id.etNoticeDescription);
        uploadNotice=findViewById(R.id.btnUploadNotice);
        tvFileName=findViewById(R.id.tvFileName);
        recyclerView=findViewById(R.id.recylerpdf);
        auth= FirebaseAuth.getInstance();
        store= FirebaseStorage.getInstance().getReference();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();
        pdfLists=new ArrayList<>();
        adadpter_pdf=new AdminNoticeAdapter(pdfLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adadpter_pdf);

        //fetch data from notice table in firestore.
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
        uploadNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description=noticeDescription.getText().toString();
                if (adminNotice!=null)
                {
                    StorageReference filePath=store.child("Notice_pdf").child(fileName+".pdf");
                    filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                Uri downloadPath=uri;
                                    Map<String,Object> hashmap= new HashMap<>();
                                    hashmap.put("Name",fileName);
                                    hashmap.put("User Id",UserId);
                                    hashmap.put("Course",course);
                                    hashmap.put("Department",department);
                                    hashmap.put("Description",description);
                                    hashmap.put("Time_Stamp", FieldValue.serverTimestamp());
                                    hashmap.put("Notice_pdf",downloadPath.toString());
                                    fire.collection("notice").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                       if (task.isSuccessful())
                                       {
                                           tvFileName.setText("File Uploaded Successfully");

                                       }
                                       else
                                       {
                                           tvFileName.setText(task.getException().getMessage());
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
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentCourse);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminCourse.setAdapter(aa);
        ArrayAdapter ab1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentBTech);
        ab1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter ab2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentBsc);
        ab2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter ab3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentManagement);
        ab3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter ab4 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentTechnology);
        ab4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter ab5 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,studentMasters);
        ab5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adminCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = adminCourse.getSelectedItem().toString();
                if(adminCourse.getSelectedItem().toString().equals("BTech")){
                    adminDept.setAdapter(ab1);
                }else if (adminCourse.getSelectedItem().toString().equals("Bsc")){
                    adminDept.setAdapter(ab2);
                }else if(adminCourse.getSelectedItem().toString().equals("Management")){
                    adminDept.setAdapter(ab3);
                }else if (adminCourse.getSelectedItem().toString().equals("Technology")){
                    adminDept.setAdapter(ab4);
                }else if(adminCourse.getSelectedItem().toString().equals("Masters")){
                    adminDept.setAdapter(ab5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adminDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=adminDept.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adminNotice.setOnClickListener(new View.OnClickListener() {
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
        if (resultCode == RESULT_OK && requestCode == PICK_FILE){
            fileUri = data.getData();
            if (fileUri.toString().startsWith("content://"))
            {
                Cursor cursor=null;
                cursor=AdminNotice.this.getContentResolver().query(fileUri,null,null,null,null);
                if (cursor!=null && cursor.moveToFirst())
                {
                    fileName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            else if(fileUri.toString().startsWith("file://"))
            {
                fileName=new File(fileUri.toString()).getName();
            }
            tvFileName.setText(fileName);


        }
    }

}
