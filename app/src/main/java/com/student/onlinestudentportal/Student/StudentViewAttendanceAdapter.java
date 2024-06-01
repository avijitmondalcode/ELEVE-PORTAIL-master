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

public class StudentViewAttendanceAdapter extends RecyclerView.Adapter<StudentViewAttendanceAdapter.ViewHolder> {
    public List<StudentViewAttendanceList> studentViewAttendanceLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    //String UserId;
    public StudentViewAttendanceAdapter(List<StudentViewAttendanceList> studentViewAttendanceLists) {
        this.studentViewAttendanceLists = studentViewAttendanceLists;
    }

    @NonNull
    @Override
    public StudentViewAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_item,parent,false);
        context = parent.getContext();

        auth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewAttendanceAdapter.ViewHolder holder, int position) {


        String Name =studentViewAttendanceLists.get(position).getName();
        String Roll =studentViewAttendanceLists.get(position).getRoll();
        String Course =studentViewAttendanceLists.get(position).getCourse();
        String Department=studentViewAttendanceLists.get(position).getDepartment();
        String Subject=studentViewAttendanceLists.get(position).getSubject();
        holder.setSubject(Subject);
        String Attendance=studentViewAttendanceLists.get(position).getAttendance();
        String TimeStamp=studentViewAttendanceLists.get(position).getTime_Stamp();
        holder.setDate(TimeStamp);
        String Semester=studentViewAttendanceLists.get(position).getSemester();





    }

    @Override
    public int getItemCount() {
        return studentViewAttendanceLists.size();
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
