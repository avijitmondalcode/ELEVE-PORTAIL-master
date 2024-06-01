package com.student.onlinestudentportal.Student;

public class StudentViewAttendanceList {
    private String  Name,Roll,Course,Department,Semester,Subject,Time_Stamp,Attendance;//Field value of Student Table

    public StudentViewAttendanceList() {

    }

    public StudentViewAttendanceList(String name, String roll, String course, String department, String semester, String subject, String time_Stamp, String attendance) {
        Name = name;
        Roll = roll;
        Course = course;
        Department = department;
        Semester = semester;
        Subject = subject;
        Time_Stamp = time_Stamp;
        Attendance = attendance;
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

    public String getTime_Stamp() {
        return Time_Stamp;
    }

    public void setTime_Stamp(String time_Stamp) {
        Time_Stamp = time_Stamp;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String attendance) {
        Attendance = attendance;
    }
}
