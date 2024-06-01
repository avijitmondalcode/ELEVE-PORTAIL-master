package com.student.onlinestudentportal.faculty;

import com.google.firebase.firestore.FieldValue;
import com.student.onlinestudentportal.MaterialId;

public class FacultyStudyMaterialList extends com.student.onlinestudentportal.MaterialId {
    private String Name,Course,Department,Description,Material_pdf;
    public FacultyStudyMaterialList()
    {

    }

    public FacultyStudyMaterialList(String name, String course, String department, String description, String material_pdf) {
        Name = name;
        Course = course;
        Department = department;
        Description = description;
        Material_pdf = material_pdf;
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

    public String getMaterial_pdf() {
        return Material_pdf;
    }

    public void setMaterial_pdf(String material_pdf) {
        Material_pdf = material_pdf;
    }
}
