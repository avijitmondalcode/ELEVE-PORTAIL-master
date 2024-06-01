package com.student.onlinestudentportal.faculty;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

public class FacultyAttendanceAdapter extends RecyclerView.Adapter<FacultyAttendanceAdapter.ViewHolder> {
    public List<FacultyAttendanceList> facultyAttendanceLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    //String UserId;
    public FacultyAttendanceAdapter(List<FacultyAttendanceList> facultyAttendanceLists) {
        this.facultyAttendanceLists = facultyAttendanceLists;
    }

    @NonNull
    @Override
    public FacultyAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item,parent,false);
        context = parent.getContext();

        auth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAttendanceAdapter.ViewHolder holder, int position) {

        String studence_attendance_id= facultyAttendanceLists.get(position).StudentAttendanceId;//Fetch notice id from NoticeId class

        String Name = facultyAttendanceLists.get(position).getName();
        holder.setName(Name);
        String Roll = facultyAttendanceLists.get(position).getRoll();
        holder.setRoll(Roll);



       holder.btnAttStudentPresent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //btn color=green
               holder.btnAttStudentPresent.setBackgroundColor(Color.GREEN);
               holder.btnAttStudentAbsent.setEnabled(false);
               holder.btnAttStudentPresent.setEnabled(false);
               //attendance table-->Subject->
               //BTech--ECE--1st--Physics-->Avijit-->Roll
               DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
               Map<String,Object> hashmap= new HashMap<>();

               if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject1()))
               {
                   hashmap.put("Name",Name);
                   hashmap.put("Roll",Roll);
                   hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                   hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                   hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                   hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject1());
                   hashmap.put("Attendance","Present");
                   hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
               }
               else if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject2()))
               {
                   hashmap.put("Name",Name);
                   hashmap.put("Roll",Roll);
                   hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                   hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                   hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                   hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject2());
                   hashmap.put("Attendance","Present");
                   hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
               }else if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject3()))
               {
                   hashmap.put("Name",Name);
                   hashmap.put("Roll",Roll);
                   hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                   hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                   hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                   hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject3());
                   hashmap.put("Attendance","Present");
                   hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
               }else if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject4())) {
                   hashmap.put("Name", Name);
                   hashmap.put("Roll", Roll);
                   hashmap.put("Course", facultyAttendanceLists.get(position).getCourse());
                   hashmap.put("Department", facultyAttendanceLists.get(position).getDepartment());
                   hashmap.put("Semester", facultyAttendanceLists.get(position).getSemester());
                   hashmap.put("Subject", facultyAttendanceLists.get(position).getSubject4());
                   hashmap.put("Attendance", "Present");
                   hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
               }
               else
               {
                   hashmap.put("Name",Name);
                   hashmap.put("Roll",Roll);
                   hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                   hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                   hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                   hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject5());
                   hashmap.put("Attendance","Present");
                   hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
               }


               firebaseFirestore.collection("Attendance").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {
                       if (task.isSuccessful())
                       {
                           Toast.makeText(context,Name+" "+"Present", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });
        holder.btnAttStudentAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn color=green
                holder.btnAttStudentAbsent.setBackgroundColor(Color.RED);
                holder.btnAttStudentAbsent.setEnabled(false);
                holder.btnAttStudentPresent.setEnabled(false);
                //attendance table-->Subject->
                //BTech--ECE--1st--Physics-->Avijit-->Roll
                DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Map<String,Object> hashmap= new HashMap<>();

                if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject1()))
                {
                    hashmap.put("Name",Name);
                    hashmap.put("Roll",Roll);
                    hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                    hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                    hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                    hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject1());
                    hashmap.put("Attendance","Absent");
                    hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
                }
                else if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject2()))
                {
                    hashmap.put("Name",Name);
                    hashmap.put("Roll",Roll);
                    hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                    hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                    hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                    hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject2());
                    hashmap.put("Attendance","Absent");
                    hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
                }else if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject3()))
                {
                    hashmap.put("Name",Name);
                    hashmap.put("Roll",Roll);
                    hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                    hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                    hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                    hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject3());
                    hashmap.put("Attendance","Absent");
                    hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
                }else if (GlobalData.Subject_Name.equals(facultyAttendanceLists.get(position).getSubject4())) {
                    hashmap.put("Name", Name);
                    hashmap.put("Roll", Roll);
                    hashmap.put("Course", facultyAttendanceLists.get(position).getCourse());
                    hashmap.put("Department", facultyAttendanceLists.get(position).getDepartment());
                    hashmap.put("Semester", facultyAttendanceLists.get(position).getSemester());
                    hashmap.put("Subject", facultyAttendanceLists.get(position).getSubject4());
                    hashmap.put("Attendance", "Absent");
                    hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
                }
                else
                {
                    hashmap.put("Name",Name);
                    hashmap.put("Roll",Roll);
                    hashmap.put("Course",facultyAttendanceLists.get(position).getCourse());
                    hashmap.put("Department",facultyAttendanceLists.get(position).getDepartment());
                    hashmap.put("Semester",facultyAttendanceLists.get(position).getSemester());
                    hashmap.put("Subject",facultyAttendanceLists.get(position).getSubject5());
                    hashmap.put("Attendance","Absent");
                    hashmap.put("Time_Stamp", LocalDate.now().format(dateTimeFormatter).toString());
                }
                firebaseFirestore.collection("Attendance").add(hashmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(context,Name+" "+"Absent", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return facultyAttendanceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvRoll;
        private View mView;
        Button btnAttStudentPresent,btnAttStudentAbsent;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnAttStudentPresent=mView.findViewById(R.id.btnAttStudentPresent);
            btnAttStudentAbsent=mView.findViewById(R.id.btnAttStudentAbsent);


        }
        public void setName(String text){
            tvName = mView.findViewById(R.id.tvAttStudentName);
            tvName.setText(text);
        }
        public void setRoll(String text1){
            tvRoll = mView.findViewById(R.id.tvAttStudentRoll);
            tvRoll.setText(text1);
        }


    }

}
