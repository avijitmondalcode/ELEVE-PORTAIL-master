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

import java.util.List;

public class FacultyTakeExamAdapter extends RecyclerView.Adapter<FacultyTakeExamAdapter.ViewHolder> {
    public List<FacultyTakeExamList> facultyTakeExamLists;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public FacultyTakeExamAdapter(List<FacultyTakeExamList> facultyTakeExamLists) {
        this.facultyTakeExamLists = facultyTakeExamLists;
    }

    @NonNull
    @Override
    public FacultyTakeExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_faculty_take_exam_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyTakeExamAdapter.ViewHolder holder, int position) {

        String subject_id = facultyTakeExamLists.get(position).SubjectId;//Fetch subject id from NoticeId class

        String Name = facultyTakeExamLists.get(position).getSubject_Name();
        holder.setName(Name);
        String Course = facultyTakeExamLists.get(position).getCourse();
        holder.setCourse(Course);
        String Department = facultyTakeExamLists.get(position).getDepartment();
        holder.setDepartment(Department);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, FacultyAddQuestionSet.class);
                j.putExtra("Subject_Name", facultyTakeExamLists.get(position).getSubject_Name());
                j.putExtra("Course", facultyTakeExamLists.get(position).getCourse());
                j.putExtra("Department", facultyTakeExamLists.get(position).getDepartment());
                j.putExtra("SubjectId", facultyTakeExamLists.get(position).SubjectId);
                j.putExtra("Semester",facultyTakeExamLists.get(position).getSemester());
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
        /*holder.ivAdminSubjectDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Admin_Subject").document(subject_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position != RecyclerView.NO_POSITION) {
                            adminViewSubjectLists.remove(position);
                        }
                        context.startActivity(new Intent(context, AdminSubject.class));
                    }
                });
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return facultyTakeExamLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFacultyExamSubjectName, tvFacultyExamSubjectCourse, tvFacultyExamSubjectDepartment;
        private View mView;
       /* Button btnAdminEditSubject;
        ImageView ivAdminSubjectDelete;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
           /* btnAdminEditSubject = mView.findViewById(R.id.btnEditSubject);
            ivAdminSubjectDelete = mView.findViewById(R.id.ivAdminViewSubjectDelete);*/
        }

        public void setName(String text) {
            tvFacultyExamSubjectName = mView.findViewById(R.id.tvFacultyExamSubjectName);
            tvFacultyExamSubjectName.setText(text);
        }

        /*public void setLink(String text1){
            tvLink = mView.findViewById(R.id.tvPdfLink);
            tvLink.setText(text1);
        }*/
        public void setCourse(String text2) {
            tvFacultyExamSubjectCourse = mView.findViewById(R.id.tvFacultyExamSubjectCourse);
            tvFacultyExamSubjectCourse.setText(text2);
        }

        public void setDepartment(String text3) {
            tvFacultyExamSubjectDepartment = mView.findViewById(R.id.tvFacultyExamSubjectDepartment);
            tvFacultyExamSubjectDepartment.setText(text3);
        }


    }

}
