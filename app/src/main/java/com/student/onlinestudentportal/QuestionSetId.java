package com.student.onlinestudentportal;

import androidx.annotation.NonNull;

public class QuestionSetId {
    public String QuestionSetId;

    public <T extends QuestionSetId> T withId(@NonNull final String id){
        this.QuestionSetId = id;
        return (T) this;
    }
}
