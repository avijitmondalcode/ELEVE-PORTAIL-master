package com.student.onlinestudentportal.Student;

public class StudentSubjectList {
    String Subject_Name,Course,Department;

    public StudentSubjectList() {

    }

    public StudentSubjectList(String subject_Name, String course, String department) {
        Subject_Name = subject_Name;
        Course = course;
        Department = department;
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
}
