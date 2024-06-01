package com.student.onlinestudentportal.Student;

import com.student.onlinestudentportal.QuestionSetId;

public class StudentQuestionSetList  {
    String Subject_Name,Course,Department,Exam_Heading,Semester;

    public StudentQuestionSetList() {

    }

    public StudentQuestionSetList(String subject_Name, String course, String department, String exam_Heading, String semester) {
        Subject_Name = subject_Name;
        Course = course;
        Department = department;
        Exam_Heading = exam_Heading;
        Semester = semester;
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

    public String getExam_Heading() {
        return Exam_Heading;
    }

    public void setExam_Heading(String exam_Heading) {
        Exam_Heading = exam_Heading;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }
}
