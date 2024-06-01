package com.student.onlinestudentportal.Student;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.InAppPdfViewer;
import com.student.onlinestudentportal.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StudentSubjectAttendanceAdapter extends RecyclerView.Adapter<StudentSubjectAttendanceAdapter.ViewHolder> {
    public List<StudentSubjectList> studentSubjectLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    String UserId;
    String roll,semester;

    public StudentSubjectAttendanceAdapter(List<StudentSubjectList> studentSubjectLists) {
        this.studentSubjectLists = studentSubjectLists;
    }

    @NonNull
    @Override
    public StudentSubjectAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_subject_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentSubjectAttendanceAdapter.ViewHolder holder, int position) {



        String Name = studentSubjectLists.get(position).getSubject_Name();
        holder.setName(Name);


        firebaseFirestore.collection("Approve_Student").document(UserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    roll= task.getResult().getString("Roll");
                    // course=task.getResult().getString("Course");
                    // department=task.getResult().getString("Department");
                    semester=task.getResult().getString("Semester");
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, StudentViewAttendance.class);
                j.putExtra("Subject_Name", studentSubjectLists.get(position).getSubject_Name());
                j.putExtra("Course", studentSubjectLists.get(position).getCourse());
                j.putExtra("Department", studentSubjectLists.get(position).getDepartment());
                j.putExtra("Semester",semester);
                j.putExtra("Roll",roll);

                context.startActivity(j);

            }
        });
        //button for edit Subject
        /*holder.btnAdminEditSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, AdminEditSubject.class);
                j.putExtra("Name", facultyTakeExamLists.get(position).getSubject_Name());
                j.putExtra("Course", facultyTakeExamLists.get(position).getCourse());
                j.putExtra("Department", facultyTakeExamLists.get(position).getDepartment());
                j.putExtra("SubjectId", facultyTakeExamLists.get(position).getDepartment());

                context.startActivity(j);
            }
        });
*/
        /*holder.ivAdminSubjectDelete.setOnClickListener(new View.OnClickListener() {

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
        });*/

    }

    @Override
    public int getItemCount() {
        return studentSubjectLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentSubjectName;
        private View mView;
       /* Button btnAdminEditSubject;
        ImageView ivAdminSubjectDelete;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
           /* btnAdminEditSubject = mView.findViewById(R.id.btnEditSubject);
            ivAdminSubjectDelete = mView.findViewById(R.id.ivAdminViewSubjectDelete);*/
        }

        public void setName(String text) {
            tvStudentSubjectName = mView.findViewById(R.id.tvStudentSubjectName);
            tvStudentSubjectName.setText(text);
        }



    }

}
