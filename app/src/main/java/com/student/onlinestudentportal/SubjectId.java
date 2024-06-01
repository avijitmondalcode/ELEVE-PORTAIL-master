package com.student.onlinestudentportal;

import androidx.annotation.NonNull;

public class SubjectId {
    public String SubjectId;

    public <T extends SubjectId> T withId(@NonNull final String id){
        this.SubjectId = id;
        return (T) this;
    }
}


