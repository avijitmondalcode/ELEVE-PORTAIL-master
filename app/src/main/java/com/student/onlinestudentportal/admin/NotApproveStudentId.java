package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;

public class NotApproveStudentId {
    public String NotApproveStudentId;

    public <T extends NotApproveStudentId> T withId(@NonNull final String id){
        this.NotApproveStudentId = id;
        return (T) this;
    }
}

