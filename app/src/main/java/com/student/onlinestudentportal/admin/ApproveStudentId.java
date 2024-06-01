package com.student.onlinestudentportal.admin;

import androidx.annotation.NonNull;

public class ApproveStudentId {
    public String ApproveStudentId;

    public <T extends ApproveStudentId> T withId(@NonNull final String id){
        this.ApproveStudentId = id;
        return (T) this;
    }
}

