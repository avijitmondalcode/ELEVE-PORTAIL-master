package com.student.onlinestudentportal.faculty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.admin.AdminNoticeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyNoticeAdapter extends RecyclerView.Adapter<FacultyNoticeAdapter.ViewHolder> {
    public List<AdminNoticeList> pdf_list;
    Context context;
    FirebaseFirestore firebaseFirestore;
    //String UserId;
    FirebaseAuth auth;
    public FacultyNoticeAdapter(List<AdminNoticeList> pdf_list) {
        this.pdf_list = pdf_list;
    }

    @NonNull
    @Override
    public FacultyNoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_notice_item,parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyNoticeAdapter.ViewHolder holder, int position) {

        String notice_id=pdf_list.get(position).NoticeId;//Fetch notice id from NoticeId class

        String desc_data = pdf_list.get(position).getName();
        holder.setName(desc_data);

        String Link = pdf_list.get(position).getNotice_pdf();
        String Course = pdf_list.get(position).getCourse();
        holder.setCourse(Course);
        String Department = pdf_list.get(position).getDepartment();
        holder.setDepartment(Department);
        String Description = pdf_list.get(position).getDescription();
        holder.setDescription(Description);

        //button for view notice
        holder.btnFacultyViewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_list.get(position).getNotice_pdf()));
                context.startActivity(i);
            }
        });
        holder.btnFacultyApproveNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Link != null){
                    Map<String,String> hashmap= new HashMap<>();
                    hashmap.put("Name",pdf_list.get(position).getName());
                    hashmap.put("pdf_Path",pdf_list.get(position).getNotice_pdf());
                    hashmap.put("Course",pdf_list.get(position).getCourse());
                    hashmap.put("Department",pdf_list.get(position).getDepartment());
                   // hashmap.put("User_Id",UserId);

                    firebaseFirestore.collection("notice_std").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(context, "Done..", Toast.LENGTH_SHORT).show();
                                //tvFileCh.setText("Uploded");
                            }else {
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //tvFileCh.setText(task.getException().getMessage());
                            }
                        }
                    });
                }else {
                    Toast.makeText(context, "Something Wrong...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.ivFacultyNoticeDelete.setOnClickListener(new View.OnClickListener() {

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
    public int getItemCount() {
        return pdf_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvLink,tvCourse,tvDepartment,tvDescription;
        private View mView;
        Button btnFacultyViewNotice,btnFacultyApproveNotice;
        ImageView ivFacultyNoticeDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnFacultyViewNotice=mView.findViewById(R.id.btnFacultyViewNotice);
            btnFacultyApproveNotice=mView.findViewById(R.id.btnFacultyApproveNotice);
            ivFacultyNoticeDelete=mView.findViewById(R.id.ivFacultyNoticeDelete);

        }
        public void setName(String text){
            tvName = mView.findViewById(R.id.tvFacultyPdfName);
            tvName.setText(text);
        }

        public void setCourse(String text2){
            tvCourse = mView.findViewById(R.id.tvFacultyCourse);
            tvCourse.setText(text2);
        }
        public void setDepartment(String text3){
            tvDepartment = mView.findViewById(R.id.tvFacultyDepartment);
            tvDepartment.setText(text3);
        }
        public void setDescription(String text4){
            tvDescription = mView.findViewById(R.id.tvFacultyDescription);
            tvDescription.setText(text4);
        }


    }

}
