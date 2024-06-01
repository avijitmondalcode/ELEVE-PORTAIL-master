package com.student.onlinestudentportal.admin;

import com.google.firebase.firestore.FieldValue;
import com.student.onlinestudentportal.NoticeId;

public class AdminNoticeList extends com.student.onlinestudentportal.NoticeId {
    private String Name,Course,Department,Description,Notice_pdf;
    public AdminNoticeList()
    {

    }

    public AdminNoticeList(String name, String course, String department, String description, String notice_pdf) {
        Name = name;
        Course = course;
        Department = department;
        Description = description;
        Notice_pdf = notice_pdf;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNotice_pdf() {
        return Notice_pdf;
    }

    public void setNotice_pdf(String notice_pdf) {
        Notice_pdf = notice_pdf;
    }
}
