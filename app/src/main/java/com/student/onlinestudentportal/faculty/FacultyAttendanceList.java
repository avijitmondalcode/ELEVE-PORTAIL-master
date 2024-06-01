package com.student.onlinestudentportal.faculty;

import com.student.onlinestudentportal.StudentAttendanceId;

public class FacultyAttendanceList extends com.student.onlinestudentportal.StudentAttendanceId {

    private String  Name,Roll,Course,Department,Semester,Subject1,Subject2,Subject3,Subject4,Subject5;//Field value of Student Table

    public FacultyAttendanceList() {

    }

    public FacultyAttendanceList(String name, String roll, String course,
                                 String department, String semester, String subject1,
                                 String subject2, String subject3, String subject4, String subject5) {
        Name = name;
        Roll = roll;
        Course = course;
        Department = department;
        Semester = semester;
        Subject1 = subject1;
        Subject2 = subject2;
        Subject3 = subject3;
        Subject4 = subject4;
        Subject5 = subject5;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoll() {
        return Roll;
    }

    public void setRoll(String roll) {
        Roll = roll;
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

    public String getSubject1() {
        return Subject1;
    }

    public void setSubject1(String subject1) {
        Subject1 = subject1;
    }

    public String getSubject2() {
        return Subject2;
    }

    public void setSubject2(String subject2) {
        Subject2 = subject2;
    }

    public String getSubject3() {
        return Subject3;
    }

    public void setSubject3(String subject3) {
        Subject3 = subject3;
    }

    public String getSubject4() {
        return Subject4;
    }

    public void setSubject4(String subject4) {
        Subject4 = subject4;
    }

    public String getSubject5() {
        return Subject5;
    }

    public void setSubject5(String subject5) {
        Subject5 = subject5;
    }
}
