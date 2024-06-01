package com.student.onlinestudentportal;

import androidx.annotation.NonNull;

public class CourseId {
    public String CourseId;

    public <T extends com.student.onlinestudentportal.CourseId> T withId(@NonNull final String id){
        this.CourseId = id;
        return (T) this;
    }
}