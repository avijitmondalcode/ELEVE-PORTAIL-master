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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.student.onlinestudentportal.R;
import com.student.onlinestudentportal.Student.QuestionList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

public class NotApproveStudentAdapter extends RecyclerView.Adapter<NotApproveStudentAdapter.ViewHolder> {
    public List<NotApproveStudentList> notApproveStudentLists;
    public List<SubjectList> subjectLists;

    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    String UserId;
    String Subject1,Subject2,Subject3,Subject4,Subject5;


    public NotApproveStudentAdapter(List<NotApproveStudentList> notApproveStudentLists) {
        this.notApproveStudentLists = notApproveStudentLists;
    }

    @NonNull
    @Override
    public NotApproveStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_approve_student_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotApproveStudentAdapter.ViewHolder holder, int position) {

        //String material_id=notApproveFacultyLists.get(position).MaterialId;//Fetch notice id from MaterialId class
        String student_id = notApproveStudentLists.get(position).NotApproveStudentId;//Fetch notice id from NoticeId class

        String Name = notApproveStudentLists.get(position).getName();
        holder.setName(Name);
        String Email = notApproveStudentLists.get(position).getEmail();
        holder.setEmail(Email);
        String Password = notApproveStudentLists.get(position).getPassword();
        String Phone=notApproveStudentLists.get(position).getPhone();
        String Address=notApproveStudentLists.get(position).getAddress();
        String Course=notApproveStudentLists.get(position).getCourse();
        String Department=notApproveStudentLists.get(position).getDepartment();
        String Profile_Image=notApproveStudentLists.get(position).getProfile_Image();
        String Registration_No=notApproveStudentLists.get(position).getRegistration_No();
        String Roll_No=notApproveStudentLists.get(position).getRoll_No();
        String Gender=notApproveStudentLists.get(position).getGender();
        String Date_Of_Birth=notApproveStudentLists.get(position).getDate_Of_Birth();
        String Semester=notApproveStudentLists.get(position).getSemester();
        //Fetch data from admin_subject table where equalto  (cour,dept,sem)---subject1,2,3,4,5
        subjectLists=new ArrayList<>();
        firebaseFirestore.collection("Admin_Subject").whereEqualTo("Course",Course).whereEqualTo("Department", Department)
                .whereEqualTo("Semester", Semester).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange doc:value.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        SubjectList blogPost = doc.getDocument().toObject(SubjectList.class);
                        subjectLists.add(blogPost);
                        for (int i = 0; i < subjectLists.size(); i++) {

                            Subject1=subjectLists.get(i).getSubject_Name();
                           // Subject1 = subjectLists.get(0).getSubject_Name();
                            //Subject2 = subjectLists.get(1).getSubject_Name();
                               /* Subject3=subjectLists.get(2).getSubject_Name();
                                Subject4=subjectLists.get(3).getSubject_Name();
                                Subject5=subjectLists.get(4).getSubject_Name();*/

                        }

                    }
                }
            }
        });


        //button for Approve Attendance
        holder.btnNAStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String sid=auth.getCurrentUser().getUid();
                            firebaseFirestore.collection("Not_Approve_Student").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                                        if (queryDocumentSnapshots != null) {
                                            Map<String,Object> hashmap= new HashMap<>();
                                            hashmap.put("Email",Email);
                                            hashmap.put("Name", Name);
                                            hashmap.put("Password",Password);
                                            hashmap.put("Address",Address);
                                            hashmap.put("Phone",Phone);
                                            hashmap.put("Date_Of_Birth",Date_Of_Birth);
                                            hashmap.put("Gender",Gender);
                                            hashmap.put("Profile_Image",Profile_Image);
                                            hashmap.put("Roll",Roll_No);
                                            hashmap.put("Reg_No",Registration_No);
                                            hashmap.put("Course",Course);
                                            hashmap.put("Department",Department);
                                            hashmap.put("Semester",Semester);
                                            hashmap.put("Subject1", subjectLists.get(0).Subject_Name);
                                            hashmap.put("Subject2", subjectLists.get(1).Subject_Name);
                                            hashmap.put("Subject3", subjectLists.get(2).Subject_Name);
                                            hashmap.put("Subject4", subjectLists.get(3).Subject_Name);
                                            hashmap.put("Subject5", subjectLists.get(4).Subject_Name);


                                            //set-->update
                                            firebaseFirestore.collection("Approve_Student").document(sid).set(hashmap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                            firebaseFirestore.collection("Not_Approve_Student")
                                                                    .document(student_id).delete()
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
                            String subject = "Student Verification[eleve portail]";
                            String message = Name + " ,Your Student Id has been approved!! "+"\n\nBy,"+"\n"+"eleve portail";
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

                firebaseFirestore.collection("Not_Approve_Student").document(student_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (position != RecyclerView.NO_POSITION) {
                            notApproveStudentLists.remove(position);
                        }
                        context.startActivity(new Intent(context, AdminDashboard.class));
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notApproveStudentLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNAStudentName, tvNAStudentEmail;
        private View mView;
        Button btnNAStudent;
        ImageView ivNAStudentDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnNAStudent = mView.findViewById(R.id.btnNAStudent);
            ivNAStudentDelete = mView.findViewById(R.id.ivNAStudentDelete);
        }

        public void setName(String text) {
            tvNAStudentName = mView.findViewById(R.id.tvNAStudentName);
            tvNAStudentName.setText(text);
        }

        public void setEmail(String text1) {
            tvNAStudentEmail = mView.findViewById(R.id.tvNAStudentEmail);
            tvNAStudentEmail.setText(text1);
        }

    }

}
