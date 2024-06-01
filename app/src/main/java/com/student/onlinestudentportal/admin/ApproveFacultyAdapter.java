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

public class ApproveFacultyAdapter extends RecyclerView.Adapter<ApproveFacultyAdapter.ViewHolder> {
    public List<ApproveFacultyList> approveFacultyLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    public ApproveFacultyAdapter(List<ApproveFacultyList> approveFacultyLists) {
        this.approveFacultyLists = approveFacultyLists;
    }

    @NonNull
    @Override
    public ApproveFacultyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approve_faculty_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveFacultyAdapter.ViewHolder holder, int position) {

        //String material_id=notApproveFacultyLists.get(position).MaterialId;//Fetch notice id from MaterialId class
        String faculty_id =approveFacultyLists.get(position).ApproveFacultyId;//Fetch notice id from NoticeId class

        String fac_name = approveFacultyLists.get(position).getName();
        holder.setName(fac_name);

        String Email = approveFacultyLists.get(position).getEmail();
        holder.setEmail(Email);
        String Address=approveFacultyLists.get(position).getAddress();
        String Phone=approveFacultyLists.get(position).getPhone();
        String Gender=approveFacultyLists.get(position).getGender();
        String image=approveFacultyLists.get(position).getProfile_Image();
        String DOB=approveFacultyLists.get(position).getDate_Of_Birth();
/*
        String Password = approveFacultyLists.get(position).getPassword();
*/

        //button for Approve Attendance
    /*    holder.btnNAFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseFirestore.collection("Not_Approve_Faculty").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                                        if (queryDocumentSnapshots != null) {
                                            Map<String,Object> hashmap= new HashMap<>();
                                            hashmap.put("Email",Email);
                                            hashmap.put("Name", fac_name);
                                            hashmap.put("Password",Password);

                                            firebaseFirestore.collection("Approve_Faculty")
                                                    .add(hashmap)
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(Task<DocumentReference> task) {
                                                            firebaseFirestore.collection("Not_Approve_Faculty")
                                                                    .document(faculty_id).delete()
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {

                                                                            notApproveFacultyLists.remove(position);
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


                            Toast.makeText(context, "Faculty has been approved", Toast.LENGTH_SHORT).show();

                            //Send Email Task
                            String subject = "Faculty Verification";
                            String message = fac_name + " ,Your Faculty Id has been approved!! ";
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{notApproveFacultyLists.get(position).getEmail()});
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
        holder.ivNAFacultyDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Not_Approve_Faculty").document(faculty_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position != RecyclerView.NO_POSITION) {
                            notApproveFacultyLists.remove(position);
                        }
                        context.startActivity(new Intent(context, AdminDashboard.class));
                    }
                });
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return approveFacultyLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvApproveFacultyName, tvApproveFacultyEmail;
        private View mView;
        /*Button btnNAFaculty;
        ImageView ivNAFacultyDelete;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            /*btnNAFaculty = mView.findViewById(R.id.btnNAFaculty);
            ivNAFacultyDelete = mView.findViewById(R.id.ivNAFacultyDelete);*/
        }

        public void setName(String text) {
            tvApproveFacultyName = mView.findViewById(R.id.tvApproveFacultyName);
            tvApproveFacultyName.setText(text);
        }

        public void setEmail(String text1) {
            tvApproveFacultyEmail = mView.findViewById(R.id.tvApproveFacultyEmail);
            tvApproveFacultyEmail.setText(text1);
        }

    }

}
