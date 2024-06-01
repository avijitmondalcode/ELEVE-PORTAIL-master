package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;

public class NotApproveFacultyId {
    public String NotApproveFacultyId;

    public <T extends NotApproveFacultyId> T withId(@NonNull final String id){
        this.NotApproveFacultyId = id;
        return (T) this;
    }
}
