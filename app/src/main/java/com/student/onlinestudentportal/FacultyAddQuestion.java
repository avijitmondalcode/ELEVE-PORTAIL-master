package com.student.onlinestudentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FacultyAddQuestion extends AppCompatActivity {

    EditText etQuestion,etOpt1,etOpt2,etOpt3,etOpt4,etAns;
    Button btnSubQuestion;
    TextView tvNoq;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    String UserId;
    int total=0;
    int quesCount1=0;
    int quesCount2=1;
    Spinner spinnerAnswer;
    String rans;
    String[] answer= {"1","2","3","4"};


    String sub_name,course,department,exam_heading,semester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_add_question);

        tvNoq=findViewById(R.id.tvNoq);
        etQuestion=findViewById(R.id.etQuestion);
        etOpt1=findViewById(R.id.etOpt1);
        etOpt2=findViewById(R.id.etOpt2);
        etOpt3=findViewById(R.id.etOpt3);
        etOpt4=findViewById(R.id.etOpt4);
       // etAns=findViewById(R.id.etAnswer);
        btnSubQuestion=findViewById(R.id.btnSubQues);
        spinnerAnswer = findViewById(R.id.spinnerAnswer);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, answer);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnswer.setAdapter(aa);
        spinnerAnswer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 rans= spinnerAnswer.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        auth= FirebaseAuth.getInstance();
        fire= FirebaseFirestore.getInstance();
        UserId= auth.getCurrentUser().getUid();


        sub_name=getIntent().getStringExtra("Subject_Name");
        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        semester=getIntent().getStringExtra("Semester");
        exam_heading=getIntent().getStringExtra("Exam_Heading");

        if (auth.getCurrentUser()!=null)
        {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Question").whereEqualTo("Course",course).whereEqualTo("Department",department)
                    .whereEqualTo("Subject_Name",sub_name).whereEqualTo("Exam_Heading",exam_heading).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                        {
                            String getTotal=queryDocumentSnapshot.getString("Total_Question");
                            total=total+Integer.parseInt(getTotal);
                            quesCount1=total;
                        }

                        tvNoq.setText(String.valueOf(total));
                    }

                }
            });
        }

        btnSubQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();//Field Value Validation
            }
        });



    }

    private void validation() {

        String Question=etQuestion.getText().toString();
        String Option1=etOpt1.getText().toString();
        String Option2=etOpt2.getText().toString();
        String Option3=etOpt3.getText().toString();
        String Option4=etOpt4.getText().toString();
       // String Answer=etAns.getText().toString();

        if ((!TextUtils.isEmpty(Question)) && (!TextUtils.isEmpty(Option1)) && (!TextUtils.isEmpty(Option2))
                && (!TextUtils.isEmpty(Option3)) && (!TextUtils.isEmpty(Option4)) && (!TextUtils.isEmpty(rans)) )
        {
            Map<String,Object> hashmap= new HashMap<>();
            hashmap.put("Subject_Name",sub_name);// hashmap.put("Table Column/field Name",field Value);
            hashmap.put("Course",course);// hashmap.put("Table Column/field Name",field Value);
            hashmap.put("Department",department);// hashmap.put("Table Column/field Name",field Value);
            hashmap.put("Semester",semester);
            hashmap.put("Exam_Heading",exam_heading);// hashmap.put("Table Column/field Name",field Value);
            hashmap.put("Total_Question",String.valueOf(quesCount2));
            hashmap.put("Question",etQuestion.getText().toString());// hashmap.put("Table Column/field Name",field Value);
            hashmap.put("Option1",etOpt1.getText().toString());
            hashmap.put("Option2",etOpt2.getText().toString());
            hashmap.put("Option3",etOpt3.getText().toString());
            hashmap.put("Option4",etOpt4.getText().toString());
            hashmap.put("Answer",rans);

            fire.collection("Question").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful())
                    {
                        quesCount1++;
                        tvNoq.setText(String.valueOf(quesCount1));
                        Toast.makeText(FacultyAddQuestion.this, "Question Added Successfullly.", Toast.LENGTH_SHORT).show();
                        etQuestion.setText(null);
                        etOpt1.setText(null);
                        etOpt2.setText(null);
                        etOpt3.setText(null);
                        etOpt4.setText(null);
                        //etAns.setText(null);
                    }
                    else
                    {
                        Toast.makeText(FacultyAddQuestion.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{
            etQuestion.requestFocus();
            etQuestion.setError("Question can't be empty!!");

            etOpt1.requestFocus();
            etOpt1.setError("Option1 cannot be empty!!");
            etOpt2.requestFocus();
            etOpt2.setError("Option1 cannot be empty!!");
            etOpt3.requestFocus();
            etOpt3.setError("Option1 cannot be empty!!");
            etOpt4.requestFocus();
            etOpt4.setError("Option1 cannot be empty!!");

           /* etAns.requestFocus();
            etAns.setError("Answer cannot be empty!!");*/

        }
    }
}