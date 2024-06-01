package com.student.onlinestudentportal;

import androidx.annotation.NonNull;

public class StudentAttendanceId {
    public String StudentAttendanceId;

    public <T extends StudentAttendanceId> T withId(@NonNull final String id){
        this.StudentAttendanceId = id;
        return (T) this;
    }
}
