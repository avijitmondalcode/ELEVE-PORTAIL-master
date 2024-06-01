package com.student.onlinestudentportal.admin;

public class NotApproveStudentList extends com.student.onlinestudentportal.admin.NotApproveStudentId {

    String Profile_Image,Name,Gender,Phone,Email,Password,Address,Date_Of_Birth,Course,Department,Roll_No,Registration_No,Semester;
    public NotApproveStudentList() {
    }

    public NotApproveStudentList(String profile_Image, String name,
                                 String gender, String phone,
                                 String email, String password,
                                 String address, String date_Of_Birth,
                                 String course, String department, String roll_No,
                                 String registration_No, String semester) {
        Profile_Image = profile_Image;
        Name = name;
        Gender = gender;
        Phone = phone;
        Email = email;
        Password = password;
        Address = address;
        Date_Of_Birth = date_Of_Birth;
        Course = course;
        Department = department;
        Roll_No = roll_No;
        Registration_No = registration_No;
        Semester = semester;
    }

    public String getProfile_Image() {
        return Profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        Profile_Image = profile_Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
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

    public String getRoll_No() {
        return Roll_No;
    }

    public void setRoll_No(String roll_No) {
        Roll_No= roll_No;
    }

    public String getRegistration_No() {
        return Registration_No;
    }

    public void setRegistration_No(String registration_No) {
        Registration_No = registration_No;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }
}

