package com.student.onlinestudentportal;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class MaterialId {
    @Exclude
    public String MaterialId;

    public <T extends MaterialId> T withId(@NonNull final String id){
        this.MaterialId = id;
        return (T) this;
    }
}
