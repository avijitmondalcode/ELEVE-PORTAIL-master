package com.student.onlinestudentportal.admin;

public class AdminViewCourseList extends com.student.onlinestudentportal.CourseId {
    String Course;

    public AdminViewCourseList() {

    }

    public AdminViewCourseList(String course) {
        Course = course;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }
}
