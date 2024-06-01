package com.student.onlinestudentportal.admin;

import com.student.onlinestudentportal.SubjectId;

public class AdminViewSubjectList  extends com.student.onlinestudentportal.SubjectId {

    String Subject_Name,Course,Department,Semester;

    public AdminViewSubjectList() {

    }

    public AdminViewSubjectList(String subject_Name, String course, String department,String semester) {
        Subject_Name = subject_Name;
        Course = course;
        Department = department;
        Semester=semester;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public void setSubject_Name(String subject_Name) {
        Subject_Name = subject_Name;
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

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }
}
