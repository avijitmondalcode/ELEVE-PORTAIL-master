package com.student.onlinestudentportal.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.student.onlinestudentportal.R;

import java.util.List;

public class StudentViewAttendanceAdapterAbsent extends RecyclerView.Adapter<StudentViewAttendanceAdapterAbsent.ViewHolder> {
    public List<StudentViewAttendanceListAbsent> studentViewAttendanceListAbsents;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    //String UserId;
    public StudentViewAttendanceAdapterAbsent(List<StudentViewAttendanceListAbsent> studentViewAttendanceListAbsents) {
        this.studentViewAttendanceListAbsents = studentViewAttendanceListAbsents;
    }

    @NonNull
    @Override
    public StudentViewAttendanceAdapterAbsent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_item,parent,false);
        context = parent.getContext();

        auth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewAttendanceAdapterAbsent.ViewHolder holder, int position) {


        String Name =studentViewAttendanceListAbsents.get(position).getName();
        String Roll =studentViewAttendanceListAbsents.get(position).getRoll();
        String Course =studentViewAttendanceListAbsents.get(position).getCourse();
        String Department=studentViewAttendanceListAbsents.get(position).getDepartment();
        String Subject=studentViewAttendanceListAbsents.get(position).getSubject();
        holder.setSubject(Subject);
        String Attendance=studentViewAttendanceListAbsents.get(position).getAttendance();
        String Date=studentViewAttendanceListAbsents.get(position).getTime_Stamp();
        holder.setDate(Date);
        String Semester=studentViewAttendanceListAbsents.get(position).getSemester();





    }

    @Override
    public int getItemCount() {
        return studentViewAttendanceListAbsents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentSubjectName,tvStudentDate;
        private View mView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }
        public void setSubject(String text){
            tvStudentSubjectName = mView.findViewById(R.id.tvStudentSubjectName);
            tvStudentSubjectName.setText(text);
        }
        public void setDate(String text1){
            tvStudentDate = mView.findViewById(R.id.tvStudentDate);
            tvStudentDate.setText(text1);
        }


    }

}
