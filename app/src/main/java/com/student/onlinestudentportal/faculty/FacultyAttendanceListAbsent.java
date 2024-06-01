package com.student.onlinestudentportal.faculty;

public class FacultyAttendanceListAbsent {
    private String  Name,Roll,Course,Department,Semester,Subject;//Field value of Student Table

    public FacultyAttendanceListAbsent() {

    }

    public FacultyAttendanceListAbsent(String name, String roll, String course, String department, String semester, String subject) {
        Name = name;
        Roll = roll;
        Course = course;
        Department = department;
        Semester = semester;
        Subject = subject;
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

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
