package com.student.onlinestudentportal.admin;

public class NotApproveFacultyList extends com.student.onlinestudentportal.admin.NotApproveFacultyId {

    String Name,Email,Password,Phone,Address,Date_Of_Birth,Gender,Profile_Image;

    public NotApproveFacultyList() {

    }

    public NotApproveFacultyList(String name, String email, String password,
                                 String phone, String address, String date_Of_Birth,
                                 String gender, String profile_Image)
    {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
        Date_Of_Birth = date_Of_Birth;
        Gender = gender;
        Profile_Image = profile_Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getProfile_Image() {
        return Profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        Profile_Image = profile_Image;
    }
}
