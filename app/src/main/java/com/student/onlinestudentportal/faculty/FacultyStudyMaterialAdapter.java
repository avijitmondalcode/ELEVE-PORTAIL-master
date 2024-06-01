package com.student.onlinestudentportal.faculty;

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
import com.student.onlinestudentportal.admin.AdminDashboard;

import java.util.List;

public class FacultyStudyMaterialAdapter extends RecyclerView.Adapter<FacultyStudyMaterialAdapter.ViewHolder> {
    public List<FacultyStudyMaterialList> facultyPdfLists;
    Context context;
    FirebaseFirestore firebaseFirestore;


    public FacultyStudyMaterialAdapter(List<FacultyStudyMaterialList> facultyPdfLists) {
        this.facultyPdfLists = facultyPdfLists;
    }

    @NonNull
    @Override
    public FacultyStudyMaterialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_study_material_item,parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyStudyMaterialAdapter.ViewHolder holder, int position) {

        String material_id=facultyPdfLists.get(position).MaterialId;//Fetch notice id from MaterialId class

        String desc_data = facultyPdfLists.get(position).getName();
        holder.setName(desc_data);

        String Link = facultyPdfLists.get(position).getMaterial_pdf();
       //holder.setLink(Link);
        String Course = facultyPdfLists.get(position).getCourse();
        holder.setCourse(Course);
        String Department = facultyPdfLists.get(position).getDepartment();
        holder.setDepartment(Department);
        String Description =facultyPdfLists.get(position).getDescription();
        holder.setDescription(Description);

        //button for view notice
        holder.btViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_list.get(position).getNotice_pdf()));//for downloading notice
                context.startActivity(i);*/

                Intent i=new Intent(context, InAppPdfViewer.class);
                i.putExtra("url",facultyPdfLists.get(position).getMaterial_pdf());
                context.startActivity(i);
            }
        });
        holder.ivMaterialDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("study_material").document(material_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            facultyPdfLists.remove(position);
                        }
                        context.startActivity(new Intent(context, AdminDashboard.class));
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return facultyPdfLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvLink,tvCourse,tvDepartment,tvDescription;
        private View mView;
        Button btViewMaterial;
        ImageView ivMaterialDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btViewMaterial=mView.findViewById(R.id.btViewMaterial);
            ivMaterialDelete=mView.findViewById(R.id.ivMaterialDelete);
        }
        public void setName(String text){
            tvName = mView.findViewById(R.id.tvFacultyMaterialName);
            tvName.setText(text);
        }
       /* public void setLink(String text1){
            tvLink = mView.findViewById(R.id.tvFacultyMaterialLink);
            tvLink.setText(text1);
        }*/
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
