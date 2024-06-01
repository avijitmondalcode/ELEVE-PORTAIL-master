package com.student.onlinestudentportal.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.student.onlinestudentportal.R;

import java.util.Calendar;

public class FacultyCheckAttendance extends AppCompatActivity
{
    Spinner FacultyCheckDepartment,FacultyCheckSemester,FacultyCheckCourse,FacultyCheckSubject;
    TextView etDate;
    int year,month,day;
    Button btnCheckStudentAttendance;
    String course, department, semester,subject,date;
    String[] studentCourse = {"Select Course", "BTech", "Bsc", "Management", "Technology", "Masters"};
    String[] studentBTech = {"Select Department", "CSE", "IT", "ECE", "EIE", "EE", "ME", "CE"};
    String[] studentBsc = {"Select Department", "Chemistry", "Physics", "Mathematics", "BIOLOGY"};
    String[] studentManagement = {"Select Department", "BBA", "BBA(HM)"};
    String[] studentTechnology = {"Select Department", "BCA", "BCS"};
    String[] studentMasters = {"Select Department", "MCA", "MBA"};
    String[] studentSemester = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
    String[] studentCSESubject={"Select Subject","Java","DSA","Software Engineering","Python","DBMS"};
    String[] studentITSubject={"Select Subject","Java","DBMS","Networking","Cyber Security","Operating System"};
    String[] studentBCASubject={"Select Subject","Java","Python","Networking","Computer Architecture","Operating System"};
    String[] studentECESubject={"Select Subject","Circuit Theory","Physics","MicroProcessors and MicroControllers","Computer Architecture","Robotics"};
    /* String[] studentEIESubject={"Select Subject","Network Analysis","Digital Electronics","Industrial Instrumentation","Control System","Mathematics"};
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
        setContentView(R.layout.activity_faculty_check_attendance);

        //Declaring through findviewId
        etDate=findViewById(R.id.etDate);
        FacultyCheckCourse=findViewById(R.id.spinFacultyCheckAttendanceCourse);
        FacultyCheckDepartment=findViewById(R.id.spinFacultyCheckAttendanceDept);
        FacultyCheckSemester=findViewById(R.id.spinFacultyCheckAttendanceSemester);
        FacultyCheckSubject=findViewById(R.id.spinFacultyCheckAttendanceSubject);
        btnCheckStudentAttendance=findViewById(R.id.btnCheckStudentAttendance);

        //Initializing The adapters for Spinner
        ArrayAdapter cour = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentCourse);
        cour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckCourse.setAdapter(cour);

        //Adapter for Spinner Departments
        ArrayAdapter dept1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBTech);
        dept1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckDepartment.setAdapter(dept1);

        ArrayAdapter dept2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBsc);
        dept2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckDepartment.setAdapter(dept2);

        ArrayAdapter dept3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentManagement);
        dept3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckDepartment.setAdapter(dept3);

        ArrayAdapter dept4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentTechnology);
        dept4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckDepartment.setAdapter(dept4);

        ArrayAdapter dept5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentMasters);
        dept5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckDepartment.setAdapter(dept5);
        //Adapter for Spinner Departments

        //Adapter for Spinner Semester
        ArrayAdapter sem = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentSemester);
        sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckSemester.setAdapter(sem);
        //Adapter for Spinner Semester

        //Adapter for Spinner Subject
        ArrayAdapter subCSE = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentCSESubject);
        subCSE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckSubject.setAdapter(subCSE);

        ArrayAdapter subIT = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentITSubject);
        subIT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckSubject.setAdapter(subIT);

        ArrayAdapter subBCA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentBCASubject);
        subBCA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckSubject.setAdapter(subBCA);

        ArrayAdapter subECE = new ArrayAdapter(this, android.R.layout.simple_spinner_item, studentECESubject);
        subBCA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyCheckSubject.setAdapter(subECE);
        //Adapter for Spinner Subject

        //Select Department AdapterView
        FacultyCheckCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = FacultyCheckCourse.getSelectedItem().toString();
                if (FacultyCheckCourse.getSelectedItem().toString().equals("BTech")) {
                    FacultyCheckDepartment.setAdapter(dept1);
                } else if (FacultyCheckCourse.getSelectedItem().toString().equals("Bsc")) {
                    FacultyCheckDepartment.setAdapter(dept2);
                } else if (FacultyCheckCourse.getSelectedItem().toString().equals("Management")) {
                    FacultyCheckDepartment.setAdapter(dept3);
                } else if (FacultyCheckCourse.getSelectedItem().toString().equals("Technology")) {
                    FacultyCheckDepartment.setAdapter(dept4);
                } else if (FacultyCheckCourse.getSelectedItem().toString().equals("Masters")) {
                    FacultyCheckDepartment.setAdapter(dept5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Department AdapterView

        //Select Semester AdapterView
        FacultyCheckSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                semester = FacultyCheckSemester.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Semester AdapterView

        //Select Subject AdapterView
        FacultyCheckDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = FacultyCheckDepartment.getSelectedItem().toString();
                if (FacultyCheckDepartment.getSelectedItem().toString().equals("CSE")) {
                    FacultyCheckSubject.setAdapter(subCSE);
                } else if (FacultyCheckDepartment.getSelectedItem().toString().equals("IT")) {
                    FacultyCheckSubject.setAdapter(subIT);
                } else if (FacultyCheckDepartment.getSelectedItem().toString().equals("BCA")) {
                    FacultyCheckSubject.setAdapter(subBCA);
                }
                else if (FacultyCheckDepartment.getSelectedItem().toString().equals("ECE")) {
                    FacultyCheckSubject.setAdapter(subECE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Select Department AdapterView

        //For Subject Spinner
        FacultyCheckSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = FacultyCheckSubject.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Enter Date for Checking Attendance
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(FacultyCheckAttendance.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if (dayOfMonth>=10 && monthOfYear>=10) {
                                    etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                                else{
                                    etDate.setText("0"+dayOfMonth + "-" +"0"+(monthOfYear + 1) + "-" + year);
                            }


                        }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnCheckStudentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FacultyCheckAttendance.this,course+department+semester+subject+etDate.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(), FacultyViewAttendance.class);

                i.putExtra("Course",course);
                i.putExtra("Department",department);
                i.putExtra("Semester",semester);
                i.putExtra("Subject",subject);
                i.putExtra("Date",etDate.getText().toString());//fetching date

                startActivity(i);
            }
        });



    }

}