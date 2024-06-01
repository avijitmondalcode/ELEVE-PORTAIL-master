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

public class AdminViewCourseAdapter extends RecyclerView.Adapter<AdminViewCourseAdapter.ViewHolder>  {

    public List<AdminViewCourseList> adminViewCourseLists;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public AdminViewCourseAdapter(List<AdminViewCourseList> adminViewCourseLists) {
        this.adminViewCourseLists = adminViewCourseLists;
    }

    @NonNull
    @Override
    public AdminViewCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_course_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new AdminViewCourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewCourseAdapter.ViewHolder holder, int position) {

        String course_id = adminViewCourseLists.get(position).CourseId;//Fetch subject id from NoticeId class

        String Name = adminViewCourseLists.get(position).getCourse();
        holder.setName(Name);

        //button for edit Subject
        holder.btnAdminEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, AdminEditCourse.class);
                j.putExtra("Course", adminViewCourseLists.get(position).getCourse());
                j.putExtra("CourseId", adminViewCourseLists.get(position).CourseId);

                context.startActivity(j);
            }
        });

        holder.ivAdminCourseDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Admin_Course").document(course_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position != RecyclerView.NO_POSITION) {
                            adminViewCourseLists.remove(position);
                        }
                        context.startActivity(new Intent(context, AdminSubject.class));
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminViewCourseLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminViewCourseName;
        private View mView;
        Button btnAdminEditCourse;
        ImageView ivAdminCourseDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnAdminEditCourse = mView.findViewById(R.id.btnEditCourse);
            ivAdminCourseDelete = mView.findViewById(R.id.ivAdminViewCourseDelete);
        }

        public void setName(String text) {
            tvAdminViewCourseName = mView.findViewById(R.id.tvAdminViewCourseName);
            tvAdminViewCourseName.setText(text);
        }


    }

}

