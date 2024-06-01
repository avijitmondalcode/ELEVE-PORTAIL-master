package com.student.onlinestudentportal.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.student.onlinestudentportal.R;

public class FacultyAttendance extends AppCompatActivity {

    Spinner FacultyDepartment,FacultySemester,FacultyCourse,FacultySubject;
    Button btnSearchStudent;
    String course, department, semester,subject;
    String[] studentCourse = {"Select Course", "BTech", "Bsc", "Management", "Technology", "Masters"};
    String[] studentBTech = {"Select Department", "CSE", "IT", "ECE", "EIE", "EE", "ME", "CE"};
    String[] studentBsc = {"Select Department", "Chemistry", "Physics", "Mathematics", "BIOLOGY"};
    String[] studentManagement = {"Select Department", "BBA", "BBA(HM)"};
    String[] studentTechnology = {"Select Department", "BCA", "BCS"};
    String[] studentMasters = {"Select Department", "MCA", "MBA"};
    String[] studentSemester = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
    String[] studentCSESubject={"Select Subject","Java","DSA","Software Engineering","Python","DBMS"};
    String[] studentITSubject={"Select Subject","OOPs","Computer Architecture","Networking","Cyber Security","IOT"};
    String[] studentBCASubject={"Select Subject","Java","Python","Networking","C++","OS"};
    String[] studentECESubject={"Select Subject","Circuit Theory","Physics","MicroProcessors and MicroControllers","Computer Architecture","Robotics"};
    /*String[] studentEIESubject={"Select Subject","Network Analysis","Digital Electronics","Industrial Instrumentation","Control System","Mathematics"};
    String[] studentEESubject={"Select Subject", "Electronic Circruit","Thermal Power","Control System","Power Electronics","Electric Drive"};
    String[] studentMESubject={"Select Subject", "Physics","Engineering Drawing","Chemistry","Engineering Mechanics","Thermodynamics"};
    String[] studentCESubject={"Select Subject", "Kinetics","Building Material and Construction","Concrete Technology","Soil Mechanics","Steel Structure"};
    String[] studentChemistrySubject={"Select Subject", "Oraganic","Non-Organic","Physical","Physics","Maths"};
    String[] studentPhysicsSubject={"Select Subject", "Thermodynamics","Kinetics","Motion","Chemistry","Maths"};
    String[] studentMathsSubject={"Select Subject", "Statistics","Calculus","Applied Mathematics","Coordinate Geometry","Physics"};
    String[] studentBiologySubject={"Select Subject", "Zoology","Botany","Human Anatomy","Genetics","Animal Kingdom"};
    String[] studentBBASubject={"Select Subject","Business Economics","Principle of Management","Financial Accounting","Marketing Management","Digital Marketing"};
    String[] studentBBAHMSubject={"Select Subject","Hospital and Health System","Medical Terminology","Health Economics","Accounts","Principle of Management"};
    String[] studentBCSSubject={"Select Subject","DSA","Python","DBMS","Computer Graphics","C++"};
    String[] studentMCASubject={"Select Subject","RDBMS","Artificial Intelligence","Data Science","DSA","Image Processing"};
    String[] studentMTechSubject={"Select Subject","Taxation","Digital Marketing","E-Commerece","Supply Chain Management","Human Resources"};
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance);

        //Declaring through findviewId
        FacultyCourse=findViewById(R.id.spinFacultyAttendanceCourse);
        FacultyDepartment=findViewById(R.id.spinFacultyAttendanceDept);
        FacultySemester=findViewById(R.id.spinFacultyAttendanceSemester);
        FacultySubject=findViewById(R.id.spinFacultyAttendanceSubject);
        btnSearchStudent=findViewById(R.id.btnSearchStudent);

        //Initializing The adapters for Spinner
        ArrayAdapter cour = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentCourse);
        cour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCourse.setAdapter(cour);

        //Adapter for Spinner Departments
        ArrayAdapter dept1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBTech);
        dept1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyDepartment.setAdapter(dept1);

        ArrayAdapter dept2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBsc);
        dept2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyDepartment.setAdapter(dept2);

        ArrayAdapter dept3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentManagement);
        dept3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyDepartment.setAdapter(dept3);

        ArrayAdapter dept4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentTechnology);
        dept4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyDepartment.setAdapter(dept4);

        ArrayAdapter dept5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentMasters);
        dept5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyDepartment.setAdapter(dept5);
        //Adapter for Spinner Departments

        //Adapter for Spinner Semester
        ArrayAdapter sem = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentSemester);
        sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySemester.setAdapter(sem);
        //Adapter for Spinner Semester

        //Adapter for Spinner Subject
        ArrayAdapter subCSE = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentCSESubject);
        subCSE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySubject.setAdapter(subCSE);

        ArrayAdapter subIT = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentITSubject);
        subIT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySubject.setAdapter(subIT);

        ArrayAdapter subBCA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBCASubject);
        subBCA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySubject.setAdapter(subBCA);

        ArrayAdapter subECE = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentECESubject);
        subBCA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultySubject.setAdapter(subECE);
        //Adapter for Spinner Subject

        //Select Department AdapterView
        FacultyCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = FacultyCourse.getSelectedItem().toString();
                if (FacultyCourse.getSelectedItem().toString().equals("BTech")) {
                    FacultyDepartment.setAdapter(dept1);
                } else if (FacultyCourse.getSelectedItem().toString().equals("Bsc")) {
                    FacultyDepartment.setAdapter(dept2);
                } else if (FacultyCourse.getSelectedItem().toString().equals("Management")) {
                    FacultyDepartment.setAdapter(dept3);
                } else if (FacultyCourse.getSelectedItem().toString().equals("Technology")) {
                    FacultyDepartment.setAdapter(dept4);
                } else if (FacultyCourse.getSelectedItem().toString().equals("Masters")) {
                    FacultyDepartment.setAdapter(dept5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Department AdapterView

        //Select Semester AdapterView
        FacultySemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                semester = FacultySemester.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Semester AdapterView

        //Select Subject AdapterView
        FacultyDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = FacultyDepartment.getSelectedItem().toString();
                if (FacultyDepartment.getSelectedItem().toString().equals("CSE")) {
                    FacultySubject.setAdapter(subCSE);
                } else if (FacultyDepartment.getSelectedItem().toString().equals("IT")) {
                    FacultySubject.setAdapter(subIT);
                } else if (FacultyDepartment.getSelectedItem().toString().equals("BCA")) {
                    FacultySubject.setAdapter(subBCA);
                }
                else if (FacultyDepartment.getSelectedItem().toString().equals("ECE")) {
                    FacultySubject.setAdapter(subECE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Department AdapterView

        //For Subject Spinner
        FacultySubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = FacultySubject.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

       btnSearchStudent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(FacultyAttendance.this,course+department+semester+subject, Toast.LENGTH_SHORT).show();
               Intent i=new Intent(getApplicationContext(), FacultyTakeAttendance.class);
               i.putExtra("Course",course);
               i.putExtra("Department",department);
               i.putExtra("Semester",semester);
               i.putExtra("Subject",subject);
               startActivity(i);
           }
       });



    }
}