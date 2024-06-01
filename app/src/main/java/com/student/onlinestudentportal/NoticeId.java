package com.student.onlinestudentportal;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class NoticeId {
    @Exclude
    public String NoticeId;

    public <T extends NoticeId> T withId(@NonNull final String id){
        this.NoticeId = id;
        return (T) this;
    }
}
