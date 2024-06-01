package com.student.onlinestudentportal.admin;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.student.onlinestudentportal.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApproveStudentAdapter extends RecyclerView.Adapter<ApproveStudentAdapter.ViewHolder> {
    public List<ApproveStudentList> approveStudentLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    public ApproveStudentAdapter(List<ApproveStudentList> approveStudentLists) {
        this.approveStudentLists = approveStudentLists;
    }

    @NonNull
    @Override
    public ApproveStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approve_student_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveStudentAdapter.ViewHolder holder, int position) {

        //String material_id=notApproveStudentLists.get(position).MaterialId;//Fetch notice id from MaterialId class
        String student_id=approveStudentLists.get(position).ApproveStudentId;//Fetch notice id from MaterialId class

        String std_name = approveStudentLists.get(position).getName();
        holder.setName(std_name);

        String Email = approveStudentLists.get(position).getEmail();
        holder.setEmail(Email);
        String Password = approveStudentLists.get(position).getPassword();
        String Phone=approveStudentLists.get(position).getPhone();
        String Address=approveStudentLists.get(position).getAddress();
        String Course=approveStudentLists.get(position).getCourse();
        String Department=approveStudentLists.get(position).getDepartment();
        String Profile_Image=approveStudentLists.get(position).getProfile_Image();
        String Registration_No=approveStudentLists.get(position).getRegistration_No();
        String Roll_No=approveStudentLists.get(position).getRoll_No();
        String Gender=approveStudentLists.get(position).getGender();
        String Date_Of_Birth=approveStudentLists.get(position).getDate_Of_Birth();
        String Semester=approveStudentLists.get(position).getSemester();
        String Subject=approveStudentLists.get(position).getSubject();
/*
        String Password = approveStudentLists.get(position).getPassword();
*/

        //button for Approve Attendance
    /*    holder.btnNAStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseFirestore.collection("Not_Approve_Student").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                                        if (queryDocumentSnapshots != null) {
                                            Map<String,Object> hashmap= new HashMap<>();
                                            hashmap.put("Email",Email);
                                            hashmap.put("Name", fac_name);
                                            hashmap.put("Password",Password);

                                            firebaseFirestore.collection("Approve_Student")
                                                    .add(hashmap)
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(Task<DocumentReference> task) {
                                                            firebaseFirestore.collection("Not_Approve_Student")
                                                                    .document(Student_id).delete()
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {

                                                                            notApproveStudentLists.remove(position);
                                                                            notifyDataSetChanged();
                                                                            notifyItemRemoved(position);
                                                                            Toast.makeText(context, "Data Deleted Successfully!!", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    });
                                                        }
                                                    });

                                        }
                                    }
                                }
                            });


                            Toast.makeText(context, "Student has been approved", Toast.LENGTH_SHORT).show();

                            //Send Email Task
                            String subject = "Student Verification";
                            String message = fac_name + " ,Your Student Id has been approved!! ";
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{notApproveStudentLists.get(position).getEmail()});
                            email.putExtra(Intent.EXTRA_SUBJECT, subject);
                            email.putExtra(Intent.EXTRA_TEXT, message);
                            email.setType("message/rfc822");
                            context.startActivity(Intent.createChooser(email, "Choose an Email client :"));


                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });
        holder.ivNAStudentDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Not_Approve_Student").document(Student_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position != RecyclerView.NO_POSITION) {
                            notApproveStudentLists.remove(position);
                        }
                        context.startActivity(new Intent(context, AdminDashboard.class));
                    }
                });
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return approveStudentLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvApproveStudentName, tvApproveStudentEmail;
        private View mView;
        /*Button btnNAStudent;
        ImageView ivNAStudentDelete;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            /*btnNAStudent = mView.findViewById(R.id.btnNAStudent);
            ivNAStudentDelete = mView.findViewById(R.id.ivNAStudentDelete);*/
        }

        public void setName(String text) {
            tvApproveStudentName = mView.findViewById(R.id.tvApproveStudentName);
            tvApproveStudentName.setText(text);
        }

        public void setEmail(String text1) {
            tvApproveStudentEmail = mView.findViewById(R.id.tvApproveStudentEmail);
            tvApproveStudentEmail.setText(text1);
        }

    }

}
