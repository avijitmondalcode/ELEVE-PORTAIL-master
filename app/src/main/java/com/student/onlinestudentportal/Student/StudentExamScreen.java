package com.student.onlinestudentportal.Student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminNoticeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class StudentExamScreen extends AppCompatActivity {


    TextView tvQuestion, option_1, option_2, option_3, option_4, questionCounter, timer;
    Button btnPrevious, btnNext;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    String UserId;
    int quesCount1 = 0;

    Handler handler = new Handler();

    int totalQuestionCount =1;
    int correct=0;
    int wrong=0;

    int marks=0;

    public List<QuestionList> questionLists;

    String question, option1, option2, option3, option4, answer;

    String ans;

    String check;

    String course,department,subject_name,semester,exam_heading,roll,regno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_exam_screen);



        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        subject_name=getIntent().getStringExtra("Subject_Name");
        semester=getIntent().getStringExtra("Semester");
        exam_heading=getIntent().getStringExtra("Exam_Heading");
        roll=getIntent().getStringExtra("Roll");
        regno=getIntent().getStringExtra("Reg_No");

        Log.e("DATA",course+department+semester+subject_name+exam_heading);

        tvQuestion = findViewById(R.id.tvQuestion);
        option_1 = findViewById(R.id.option_1);
        option_2 = findViewById(R.id.option_2);
        option_3 = findViewById(R.id.option_3);
        option_4 = findViewById(R.id.option_4);
        btnNext = findViewById(R.id.nextBtn);
        questionCounter = findViewById(R.id.questionCounter);


        timer = findViewById(R.id.timer);

        handler.postDelayed(run,1000);

        questionLists=new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();

        getFireStoreData();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesCount1++;
                if (totalQuestionCount==questionLists.size())
                {
                    btnNext.setText("Submit");
                    btnNext.setBackgroundColor(Color.RED);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j=new Intent(getApplicationContext(),ThankYou.class);
                            j.putExtra("Subject_Name", subject_name);
                            j.putExtra("Course",course);
                            j.putExtra("Department", department);
                            j.putExtra("Exam_Heading", exam_heading);
                            j.putExtra("Semester",semester);
                            j.putExtra("Name",GlobalData.StudentName);
                            j.putExtra("Roll",roll);
                            j.putExtra("Reg_No",regno);
                            j.putExtra("Name",GlobalData.StudentName);
                            j.putExtra("Correct",correct);
                            j.putExtra("Wrong",wrong);
                            j.putExtra("Full_Marks",questionLists.size());
                            startActivity(j);
                            finish();
                            Toast.makeText(StudentExamScreen.this,GlobalData.StudentName+roll+regno+course+department+semester+subject_name+exam_heading+correct+wrong+questionLists.size(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    totalQuestionCount++;
                    questionCounter.setText(String.valueOf(totalQuestionCount)+"/"+questionLists.size());
                }
                for (int i = 0; i < questionLists.size(); i++) {
                    if (quesCount1 == i) {
                        timer.setText("30");
                        String ques = questionLists.get(i).getQuestion();
                        tvQuestion.setText(ques);
                        String op1=questionLists.get(i).getOption1();
                        option_1.setText(op1);
                        String op2=questionLists.get(i).getOption2();
                        option_2.setText(op2);
                        String op3=questionLists.get(i).getOption3();
                        option_3.setText(op3);
                        String op4=questionLists.get(i).getOption4();
                        option_4.setText(op4);
                        ans=questionLists.get(i).getAnswer();

                        Log.e("Data",ans);


                        option_1.setClickable(true);
                        option_2.setClickable(true);
                        option_3.setClickable(true);
                        option_4.setClickable(true);

                        option_1.setBackgroundColor(Color.WHITE);
                        option_1.setTextColor(Color.BLACK);
                        option_2.setBackgroundColor(Color.WHITE);
                        option_2.setTextColor(Color.BLACK);
                        option_3.setBackgroundColor(Color.WHITE);
                        option_3.setTextColor(Color.BLACK);
                        option_4.setBackgroundColor(Color.WHITE);
                        option_4.setTextColor(Color.BLACK);

                        option_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_2.setClickable(false);
                                option_3.setClickable(false);
                                option_4.setClickable(false);

                                if(option_1.getText().toString().equals(ans)){
                                    option_1.setBackgroundColor(Color.BLUE);
                                    option_1.setTextColor(Color.WHITE);
                                    correct++;
                                    GlobalData.totalMarks++;
                                }else {
                                    option_1.setBackgroundColor(Color.BLUE);
                                    option_1.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks++;
                                    wrong++;
                                }

                            }
                        });
                        option_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_1.setClickable(false);
                                option_3.setClickable(false);
                                option_4.setClickable(false);
                                if(option_2.getText().toString().equals(ans)){
                                    GlobalData.totalMarks++;
                                    option_2.setBackgroundColor(Color.BLUE);
                                    option_2.setTextColor(Color.WHITE);
                                    correct++;
                                }else {
                                    option_2.setBackgroundColor(Color.BLUE);
                                    option_2.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks--;
                                    wrong++;
                                }
                            }
                        });
                        option_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_2.setClickable(false);
                                option_1.setClickable(false);
                                option_4.setClickable(false);
                                if(option_3.getText().toString().equals(ans)){
                                    option_3.setBackgroundColor(Color.BLUE);
                                    option_3.setTextColor(Color.WHITE);                                    GlobalData.totalMarks++;
                                    correct++;
                                }else {
                                    option_3.setBackgroundColor(Color.BLUE);
                                    option_3.setTextColor(Color.WHITE);                                    GlobalData.totalMarks--;
                                    wrong++;
                                }
                            }
                        });
                        option_4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_2.setClickable(false);
                                option_3.setClickable(false);
                                option_1.setClickable(false);
                                if(option_4.getText().toString().equals(ans)){
                                    option_4.setBackgroundColor(Color.BLUE);
                                    option_4.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks++;
                                    correct++;
                                }else {
                                    option_4.setBackgroundColor(Color.BLUE);
                                    option_4.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks--;
                                    wrong++;
                                }
                            }
                        });


                    }
                }

            }
        });

        option_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option_2.setClickable(false);
                option_3.setClickable(false);
                option_4.setClickable(false);

                if(option_1.getText().toString().equals(answer)){
                    option_1.setBackgroundColor(Color.BLUE);
                    option_1.setTextColor(Color.WHITE);
                    GlobalData.totalMarks++;
                    correct++;
                }else {
                    option_1.setBackgroundColor(Color.BLUE);
                    option_1.setTextColor(Color.WHITE);
                    GlobalData.totalMarks++;
                    wrong++;
                }

            }
        });
        option_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option_1.setClickable(false);
                option_3.setClickable(false);
                option_4.setClickable(false);
                if(option_2.getText().toString().equals(answer)){
                    GlobalData.totalMarks++;
                    correct++;
                    option_2.setBackgroundColor(Color.BLUE);
                    option_2.setTextColor(Color.WHITE);
                }else {
                    option_2.setBackgroundColor(Color.BLUE);
                    option_2.setTextColor(Color.WHITE);
                    GlobalData.totalMarks--;
                    wrong++;
                }
            }
        });
        option_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option_2.setClickable(false);
                option_1.setClickable(false);
                option_4.setClickable(false);
                if(option_3.getText().toString().equals(answer)){
                    option_3.setBackgroundColor(Color.BLUE);
                    option_3.setTextColor(Color.WHITE);
                    GlobalData.totalMarks++;
                    correct++;
                }else {
                    option_3.setBackgroundColor(Color.BLUE);
                    option_3.setTextColor(Color.WHITE);
                    GlobalData.totalMarks--;
                    wrong++;
                }
            }
        });
        option_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option_2.setClickable(false);
                option_3.setClickable(false);
                option_1.setClickable(false);
                if(option_4.getText().toString().equals(answer)){
                    option_4.setBackgroundColor(Color.BLUE);
                    option_4.setTextColor(Color.WHITE);
                    GlobalData.totalMarks++;
                    correct++;
                }else {
                    option_4.setBackgroundColor(Color.BLUE);
                    option_4.setTextColor(Color.WHITE);
                    GlobalData.totalMarks--;
                    wrong++;
                }
            }
        });

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            timer.setText(""+ (Integer.parseInt(timer.getText().toString())-1));

            if(Integer.parseInt(timer.getText().toString()) == 0){
                quesCount1++;
                totalQuestionCount++;
                questionCounter.setText(String.valueOf(totalQuestionCount)+"/"+questionLists.size());

                timer.setText("30");

                handler.postDelayed(run,1000);

                for (int i = 0; i < questionLists.size(); i++) {
                    if (quesCount1 == i) {

                        String ques = questionLists.get(i).getQuestion();
                        tvQuestion.setText(ques);
                        String op1=questionLists.get(i).getOption1();
                        option_1.setText(op1);
                        String op2=questionLists.get(i).getOption2();
                        option_2.setText(op2);
                        String op3=questionLists.get(i).getOption3();
                        option_3.setText(op3);
                        String op4=questionLists.get(i).getOption4();
                        option_4.setText(op4);

                        ans=questionLists.get(i).getAnswer();

                        Log.e("Data",ans);


                        option_1.setClickable(true);
                        option_2.setClickable(true);
                        option_3.setClickable(true);
                        option_4.setClickable(true);

                        option_1.setBackgroundColor(Color.WHITE);
                        option_1.setTextColor(Color.BLACK);
                        option_2.setBackgroundColor(Color.WHITE);
                        option_2.setTextColor(Color.BLACK);
                        option_3.setBackgroundColor(Color.WHITE);
                        option_3.setTextColor(Color.BLACK);
                        option_4.setBackgroundColor(Color.WHITE);
                        option_4.setTextColor(Color.BLACK);

                        option_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_2.setClickable(false);
                                option_3.setClickable(false);
                                option_4.setClickable(false);

                                if(option_1.getText().toString().equals(ans)){
                                    option_1.setBackgroundColor(Color.BLUE);
                                    option_1.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks++;
                                }else {
                                    option_1.setBackgroundColor(Color.BLUE);
                                    option_1.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks++;
                                }

                            }
                        });
                        option_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_1.setClickable(false);
                                option_3.setClickable(false);
                                option_4.setClickable(false);
                                if(option_2.getText().toString().equals(ans)){
                                    GlobalData.totalMarks++;
                                    option_2.setBackgroundColor(Color.BLUE);
                                    option_2.setTextColor(Color.WHITE);
                                }else {
                                    option_2.setBackgroundColor(Color.BLUE);
                                    option_2.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks--;
                                }
                            }
                        });
                        option_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_2.setClickable(false);
                                option_1.setClickable(false);
                                option_4.setClickable(false);
                                if(option_3.getText().toString().equals(ans)){
                                    option_3.setBackgroundColor(Color.BLUE);
                                    option_3.setTextColor(Color.WHITE);

                                    GlobalData.totalMarks++;
                                }else {
                                    option_3.setBackgroundColor(Color.BLUE);
                                    option_3.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks--;
                                }
                            }
                        });
                        option_4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                option_2.setClickable(false);
                                option_3.setClickable(false);
                                option_1.setClickable(false);
                                if(option_4.getText().toString().equals(ans)){
                                    option_4.setBackgroundColor(Color.BLUE);
                                    option_4.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks++;
                                }else {
                                    option_4.setBackgroundColor(Color.BLUE);
                                    option_4.setTextColor(Color.WHITE);
                                    GlobalData.totalMarks--;
                                }
                            }
                        });


                    }
                }
            }else {
                handler.postDelayed(run,1000);
            }

        }
    };



    private void getFireStoreData() {

        if (auth.getCurrentUser() != null) {
            fire = FirebaseFirestore.getInstance();
            fire.collection("Question")
                    .whereEqualTo("Course",course).whereEqualTo("Department",department)
                    .whereEqualTo("Semester",semester).whereEqualTo("Exam_Heading",exam_heading)
                    .whereEqualTo("Subject_Name",subject_name)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc:value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            QuestionList blogPost = doc.getDocument().toObject(QuestionList.class);
                            questionLists.add(blogPost);
                            tvQuestion.setText(questionLists.get(0).getQuestion());
                            Log.e("Data1",tvQuestion.getText().toString());

                            questionCounter.setText(String.valueOf(totalQuestionCount)+"/"+questionLists.size());


                            for (int i = 0; i < questionLists.size(); i++)
                            {

                                if (quesCount1==i)
                                {
                                    question= questionLists.get(i).getQuestion();
                                    tvQuestion.setText(question);
                                    option1=questionLists.get(i).getOption1();
                                    option_1.setText(option1);
                                    option2=questionLists.get(i).getOption2();
                                    option_2.setText(option2);
                                    option3=questionLists.get(i).getOption3();
                                    option_3.setText(option3);
                                    option4=questionLists.get(i).getOption4();
                                    option_4.setText(option4);
                                    answer = questionLists.get(i).getAnswer();

                                }

                            }
                        }
                    }
                }
            });
        }

    }


}