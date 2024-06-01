package com.student.onlinestudentportal.faculty;

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

public class FacultyViewAttendanceAdapterAbsent extends RecyclerView.Adapter<FacultyViewAttendanceAdapterAbsent.ViewHolder> {
    public List<FacultyAttendanceListAbsent> facultyAttendanceLists;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    //String UserId;
    public FacultyViewAttendanceAdapterAbsent(List<FacultyAttendanceListAbsent> facultyAttendanceLists) {
        this.facultyAttendanceLists = facultyAttendanceLists;
    }

    @NonNull
    @Override
    public FacultyViewAttendanceAdapterAbsent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_view_item,parent,false);
        context = parent.getContext();

        auth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //UserId=auth.getCurrentUser().getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewAttendanceAdapterAbsent.ViewHolder holder, int position) {

        //String studence_attendance_id= facultyAttendanceLists.get(position).StudentAttendanceId;//Fetch notice id from NoticeId class

        String Name = facultyAttendanceLists.get(position).getName();
        holder.setName(Name);
        String Roll = facultyAttendanceLists.get(position).getRoll();
        holder.setRoll(Roll);




    }

    @Override
    public int getItemCount() {
        return facultyAttendanceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvRoll;
        private View mView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }
        public void setName(String text){
            tvName = mView.findViewById(R.id.tvCheckStudentName);
            tvName.setText(text);
        }
        public void setRoll(String text1){
            tvRoll = mView.findViewById(R.id.tvCheckStudentRoll);
            tvRoll.setText(text1);
        }


    }

}
