package com.student.onlinestudentportal.admin;

import android.content.Context;
import android.content.Intent;
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
import com.student.onlinestudentportal.R;

import java.util.List;

public class AdminViewSubjectAdapter extends RecyclerView.Adapter<AdminViewSubjectAdapter.ViewHolder> {
    public List<AdminViewSubjectList> adminViewSubjectLists;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public AdminViewSubjectAdapter(List<AdminViewSubjectList> adminViewSubjectLists) {
        this.adminViewSubjectLists = adminViewSubjectLists;
    }

    @NonNull
    @Override
    public AdminViewSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_subject_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewSubjectAdapter.ViewHolder holder, int position) {

        String subject_id = adminViewSubjectLists.get(position).SubjectId;//Fetch subject id from NoticeId class

        String Name = adminViewSubjectLists.get(position).getSubject_Name();
        holder.setName(Name);
        String Course = adminViewSubjectLists.get(position).getCourse();
        holder.setCourse(Course);
        String Department = adminViewSubjectLists.get(position).getDepartment();
        holder.setDepartment(Department);
        String Semester=adminViewSubjectLists.get(position).getSemester();
        holder.setSemester(Semester);
        //button for edit Subject
        holder.btnAdminEditSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, AdminEditSubject.class);
                j.putExtra("Name", adminViewSubjectLists.get(position).getSubject_Name());
                j.putExtra("Course", adminViewSubjectLists.get(position).getCourse());
                j.putExtra("Department", adminViewSubjectLists.get(position).getDepartment());
                j.putExtra("Semester",adminViewSubjectLists.get(position).getSemester());
                j.putExtra("SubjectId", adminViewSubjectLists.get(position).SubjectId);
                context.startActivity(j);
            }
        });

        holder.ivAdminSubjectDelete.setOnClickListener(new View.OnClickListener() {

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
        });
    }

    @Override
    public int getItemCount() {
        return adminViewSubjectLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminViewSubjectName, tvAdminViewSubjectCourse, tvAdminViewSubjectDepartment,tvAdminViewSubjectSemester;
        private View mView;
        Button btnAdminEditSubject;
        ImageView ivAdminSubjectDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnAdminEditSubject = mView.findViewById(R.id.btnEditSubject);
            ivAdminSubjectDelete = mView.findViewById(R.id.ivAdminViewSubjectDelete);
        }

        public void setName(String text) {
            tvAdminViewSubjectName = mView.findViewById(R.id.tvAdminViewSubjectName);
            tvAdminViewSubjectName.setText(text);
        }

        /*public void setLink(String text1){
            tvLink = mView.findViewById(R.id.tvPdfLink);
            tvLink.setText(text1);
        }*/
        public void setCourse(String text2) {
            tvAdminViewSubjectCourse = mView.findViewById(R.id.tvAdminViewSubjectCourse);
            tvAdminViewSubjectCourse.setText(text2);
        }

        public void setDepartment(String text3) {
            tvAdminViewSubjectDepartment = mView.findViewById(R.id.tvAdminViewSubjectDepartment);
            tvAdminViewSubjectDepartment.setText(text3);
        }
        public void setSemester(String text4) {
            tvAdminViewSubjectSemester= mView.findViewById(R.id.tvAdminViewSubjectSemester);
            tvAdminViewSubjectSemester.setText(text4);
        }


    }

}
