package com.student.onlinestudentportal.Student;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.InAppPdfViewer;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.faculty.FacultyDashboard;

import java.util.List;

public class StudentNoticeAdapter extends RecyclerView.Adapter<StudentNoticeAdapter.ViewHolder> {
    public List<StudentNoticeList> pdf_list;
    Context context;
    FirebaseFirestore firebaseFirestore;
    //String UserId;
    FirebaseAuth auth;
    public StudentNoticeAdapter(List<StudentNoticeList> pdf_list) {
        this.pdf_list = pdf_list;
    }

    @NonNull
    @Override
    public StudentNoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_notice_item,parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentNoticeAdapter.ViewHolder holder, int position)
    {
        String notice_id=pdf_list.get(position).NoticeId;//Fetch notice id from NoticeId class

        String desc_data = pdf_list.get(position).getName();
        holder.setName(desc_data);


        String Course = pdf_list.get(position).getCourse();
        holder.setCourse(Course);
        String Department = pdf_list.get(position).getDepartment();
        holder.setDepartment(Department);
        //button for view notice
        holder.btnStudentViewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, InAppPdfViewer.class);
                i.putExtra("url",pdf_list.get(position).getPdf_Path());
                context.startActivity(i);
            }
        });

        holder.ivStudentNoticeDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("notice_std").document(notice_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            pdf_list.remove(position);
                        }
                        context.startActivity(new Intent(context, FacultyDashboard.class));
                    }
                });
            }
        });
    }



    @Override
    public int getItemCount()
    {
        return pdf_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvLink,tvCourse,tvDepartment;
        private View mView;
        Button btnStudentViewNotice;
        ImageView ivStudentNoticeDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnStudentViewNotice=mView.findViewById(R.id.btnStudentViewNotice);
            ivStudentNoticeDelete=mView.findViewById(R.id.ivStudentNoticeDelete);
        }
        public void setName(String text){
            tvName = mView.findViewById(R.id.tvStudentPdfName);
            tvName.setText(text);
        }

        public void setCourse(String text2){
            tvCourse = mView.findViewById(R.id.tvStudentCourse);
            tvCourse.setText(text2);
        }
        public void setDepartment(String text3){
            tvDepartment = mView.findViewById(R.id.tvStudentDepartment);
            tvDepartment.setText(text3);
        }

    }

}
