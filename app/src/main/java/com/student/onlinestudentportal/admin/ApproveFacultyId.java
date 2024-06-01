package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;

public class ApproveFacultyId {
    public String ApproveFacultyId;

    public <T extends ApproveFacultyId> T withId(@NonNull final String id){
        this.ApproveFacultyId = id;
        return (T) this;
    }
}
