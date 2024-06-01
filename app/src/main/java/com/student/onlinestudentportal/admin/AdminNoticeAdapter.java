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
import com.student.onlinestudentportal.InAppPdfViewer;
import com.student.onlinestudentportal.R;

import java.util.List;

public class AdminNoticeAdapter extends RecyclerView.Adapter<AdminNoticeAdapter.ViewHolder> {
    public List<AdminNoticeList> pdf_list;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public AdminNoticeAdapter(List<AdminNoticeList> pdf_list) {
        this.pdf_list = pdf_list;
    }

    @NonNull
    @Override
    public AdminNoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_notice_item,parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNoticeAdapter.ViewHolder holder, int position) {

        String notice_id=pdf_list.get(position).NoticeId;//Fetch notice id from NoticeId class

        String desc_data = pdf_list.get(position).getName();
        holder.setName(desc_data);

       String Link = pdf_list.get(position).getNotice_pdf();
       // holder.setLink(Link);
        String Course = pdf_list.get(position).getCourse();
        holder.setCourse(Course);
        String Department = pdf_list.get(position).getDepartment();
        holder.setDepartment(Department);
        String Description = pdf_list.get(position).getDescription();
        holder.setDescription(Description);

        //button for view notice
        holder.btViewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_list.get(position).getNotice_pdf()));//for downloading notice
                context.startActivity(i);*/

                Intent i=new Intent(context, InAppPdfViewer.class);
                i.putExtra("url",pdf_list.get(position).getNotice_pdf());
                context.startActivity(i);
            }
        });
        holder.ivNoticeDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    firebaseFirestore.collection("notice").document(notice_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (position!=RecyclerView.NO_POSITION)
                            {
                                pdf_list.remove(position);
                            }
                            context.startActivity(new Intent(context, AdminDashboard.class));
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
        Button btViewNotice;
        ImageView ivNoticeDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btViewNotice=mView.findViewById(R.id.btViewNotice);
            ivNoticeDelete=mView.findViewById(R.id.ivNoticeDelete);
        }
        public void setName(String text){
            tvName = mView.findViewById(R.id.tvPdfName);
            tvName.setText(text);
        }
        /*public void setLink(String text1){
            tvLink = mView.findViewById(R.id.tvPdfLink);
            tvLink.setText(text1);
        }*/
        public void setCourse(String text2){
            tvCourse = mView.findViewById(R.id.tvCourse);
            tvCourse.setText(text2);
        }
        public void setDepartment(String text3){
            tvDepartment = mView.findViewById(R.id.tvDepartment);
            tvDepartment.setText(text3);
        }
        public void setDescription(String text4){
            tvDescription = mView.findViewById(R.id.tvDescription);
            tvDescription.setText(text4);
        }

    }

}
