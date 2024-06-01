package com.student.onlinestudentportal.Student;

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
import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.InAppPdfViewer;
import com.student.onlinestudentportal.R;

import java.util.List;

public class StudentQuestionSetAdapter extends RecyclerView.Adapter<StudentQuestionSetAdapter.ViewHolder> {
    public List<StudentQuestionSetList> studentQuestionSetLists;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public StudentQuestionSetAdapter(List<StudentQuestionSetList> studentQuestionSetLists) {
        this.studentQuestionSetLists = studentQuestionSetLists;
    }

    @NonNull
    @Override
    public StudentQuestionSetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_question_set_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentQuestionSetAdapter.ViewHolder holder, int position) {

        //String question_set_id = studentQuestionSetLists.get(position).QuestionSetId;//Fetch subject id from NoticeId class
        String Subject_Name = studentQuestionSetLists.get(position).getSubject_Name();
        String Course = studentQuestionSetLists.get(position).getCourse();
        String Department = studentQuestionSetLists.get(position).getDepartment();
        String ExamHeading=studentQuestionSetLists.get(position).getExam_Heading();
        holder.setExam_Heading(ExamHeading);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, StudentStartExam.class);
                j.putExtra("Subject_Name", studentQuestionSetLists.get(position).getSubject_Name());
                j.putExtra("Course", studentQuestionSetLists.get(position).getCourse());
                j.putExtra("Department",studentQuestionSetLists.get(position).getDepartment());
                j.putExtra("Semester",studentQuestionSetLists.get(position).getSemester());
                //j.putExtra("QuestionSetId", studentQuestionSetLists.get(position).QuestionSetId);
                j.putExtra("Exam_Heading",studentQuestionSetLists.get(position).getExam_Heading());
                j.putExtra("Roll", GlobalData.roll);
                j.putExtra("Reg_No",GlobalData.regno);
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
       /* holder.ivExamDelete.setOnClickListener(new View.OnClickListener() {

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
        });*/


    }

    @Override
    public int getItemCount() {
        return studentQuestionSetLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExamHeading;
        private View mView;
        // Button btnAdminEditSubject;
        //ImageView ivExamDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
           // ivExamDelete = mView.findViewById(R.id.ivExamDelete);
        }


        public void setExam_Heading(String text3) {
            tvExamHeading = mView.findViewById(R.id.tvExamHeading);
            tvExamHeading.setText(text3);
        }



    }

}
