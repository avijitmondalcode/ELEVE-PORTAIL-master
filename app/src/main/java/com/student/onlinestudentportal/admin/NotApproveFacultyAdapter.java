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

public class NotApproveFacultyAdapter extends RecyclerView.Adapter<NotApproveFacultyAdapter.ViewHolder> {
    public List<NotApproveFacultyList> notApproveFacultyLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    public NotApproveFacultyAdapter(List<NotApproveFacultyList> notApproveFacultyLists) {
        this.notApproveFacultyLists = notApproveFacultyLists;
    }

    @NonNull
    @Override
    public NotApproveFacultyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_approve_faculty_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotApproveFacultyAdapter.ViewHolder holder, int position) {

        //String material_id=notApproveFacultyLists.get(position).MaterialId;//Fetch notice id from MaterialId class
        String faculty_id = notApproveFacultyLists.get(position).NotApproveFacultyId;//Fetch notice id from NoticeId class

        String fac_name = notApproveFacultyLists.get(position).getName();
        holder.setName(fac_name);

        String Email = notApproveFacultyLists.get(position).getEmail();
        holder.setEmail(Email);

        String Password = notApproveFacultyLists.get(position).getPassword();

        String Phone=notApproveFacultyLists.get(position).getPhone();

        String Address=notApproveFacultyLists.get(position).getAddress();

        String Date_Of_Birth=notApproveFacultyLists.get(position).getDate_Of_Birth();

        String Gender=notApproveFacultyLists.get(position).getGender();

        String image=notApproveFacultyLists.get(position).getProfile_Image();

        //button for Approve Attendance
        holder.btnNAFaculty.setOnClickListener(new View.OnClickListener() {
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
                                            hashmap.put("Address",Address);
                                            hashmap.put("Phone",Phone);
                                            hashmap.put("Date_Of_Birth",Date_Of_Birth);
                                            hashmap.put("Gender",Gender);
                                            hashmap.put("Profile_Image",image);
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
                            String message = fac_name + " ,Your Faculty Id has been approved!! "+R.drawable.logo+"\nBy,\neleve portail";
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
        });
    }

    @Override
    public int getItemCount() {
        return notApproveFacultyLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNAFacultyName, tvNAFacultyEmail;
        private View mView;
        Button btnNAFaculty;
        ImageView ivNAFacultyDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnNAFaculty = mView.findViewById(R.id.btnNAFaculty);
            ivNAFacultyDelete = mView.findViewById(R.id.ivNAFacultyDelete);
        }

        public void setName(String text) {
            tvNAFacultyName = mView.findViewById(R.id.tvNAFacultyName);
            tvNAFacultyName.setText(text);
        }

        public void setEmail(String text1) {
            tvNAFacultyEmail = mView.findViewById(R.id.tvNAFacultyEmail);
            tvNAFacultyEmail.setText(text1);
        }

    }

}
