package com.student.onlinestudentportal.Student;

import com.google.firebase.firestore.FieldValue;
import com.student.onlinestudentportal.NoticeId;

public class StudentNoticeList extends com.student.onlinestudentportal.NoticeId {
    private String Name,Course,Department,pdf_Path;
    public StudentNoticeList()
    {

    }

    public StudentNoticeList(String name, String course, String department, String pdf_Path) {
        Name = name;
        Course = course;
        Department = department;
        this.pdf_Path = pdf_Path;
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

    public String getPdf_Path() {
        return pdf_Path;
    }

    public void setPdf_Path(String pdf_Path) {
        this.pdf_Path = pdf_Path;
    }
}
