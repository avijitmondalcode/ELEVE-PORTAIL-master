package com.student.onlinestudentportal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.InAppPdfViewer;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminViewSubjectList;

import java.util.List;

public class FacultyAddQuestionSetAdapter extends RecyclerView.Adapter<FacultyAddQuestionSetAdapter.ViewHolder> {
    public List<FacultyAddQuestionSetList> facultyAddQuestionSetLists;
    public List<AdminViewSubjectList> adminViewSubjectLists;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public FacultyAddQuestionSetAdapter(List<FacultyAddQuestionSetList> facultyAddQuestionSetLists) {
        this.facultyAddQuestionSetLists = facultyAddQuestionSetLists;
    }
/*
    public FacultyAddQuestionSetAdapter(List<AdminViewSubjectList> adminViewSubjectLists) {
        this.adminViewSubjectLists = adminViewSubjectLists;
    }
*/

    @NonNull
    @Override
    public FacultyAddQuestionSetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_faculty_add_question_set_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAddQuestionSetAdapter.ViewHolder holder, int position) {

        String question_set_id = facultyAddQuestionSetLists.get(position).QuestionSetId;//Fetch subject id from NoticeId class
        //String subject_id=facultyAddQuestionSetLists.get(position).SubjectId;
        String Subject_Name = facultyAddQuestionSetLists.get(position).getSubject_Name();
        String Course = facultyAddQuestionSetLists.get(position).getCourse();
        String Department = facultyAddQuestionSetLists.get(position).getDepartment();
        String Semester=facultyAddQuestionSetLists.get(position).getSemester();
        String ExamHeading=facultyAddQuestionSetLists.get(position).getExam_Heading();
        holder.setExam_Heading(ExamHeading);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, FacultyAddQuestion.class);
                j.putExtra("Subject_Name", facultyAddQuestionSetLists.get(position).getSubject_Name());
                j.putExtra("Course", facultyAddQuestionSetLists.get(position).getCourse());
                j.putExtra("Department",facultyAddQuestionSetLists.get(position).getDepartment());
                j.putExtra("Semester",facultyAddQuestionSetLists.get(position).getSemester());
                j.putExtra("QuestionSetId", facultyAddQuestionSetLists.get(position).QuestionSetId);
                j.putExtra("Exam_Heading",facultyAddQuestionSetLists.get(position).getExam_Heading());
                context.startActivity(j);

            }
        });
        //button for edit Subject
        /*holder.btnAdminEditSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, AdminEditSubject.class);
                j.putExtra("Name", facultyTakeExamLists.get(position).getSubject_Name());
                j.putExtra("Course", facultyTakeExamLists.get(position).getCourse());
                j.putExtra("Department", facultyTakeExamLists.get(position).getDepartment());
                j.putExtra("SubjectId", facultyTakeExamLists.get(position).getDepartment());

                context.startActivity(j);
            }
        });
*/
        holder.ivExamDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Exam").document(question_set_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position != RecyclerView.NO_POSITION) {
                            facultyAddQuestionSetLists.remove(position);
                        }
                        context.startActivity(new Intent(context, FacultyAddQuestionSet.class));
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return facultyAddQuestionSetLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExamHeading;
        private View mView;
       // Button btnAdminEditSubject;
        ImageView ivExamDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ivExamDelete = mView.findViewById(R.id.ivExamDelete);
        }


        public void setExam_Heading(String text3) {
            tvExamHeading = mView.findViewById(R.id.tvExamHeading);
            tvExamHeading.setText(text3);
        }



    }

}
