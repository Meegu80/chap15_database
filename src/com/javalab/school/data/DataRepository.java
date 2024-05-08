package com.javalab.school.data;


import com.javalab.school.domain.*;

import java.util.ArrayList;

public class DataRepository {
    private ArrayList<Student> students = new ArrayList<>();    // 학생 데이터를 ArrayList에 추가
    private ArrayList<Professor> professors = new ArrayList<>();    // 교수 데이터를 ArrayList에 추가
    private ArrayList<Department> departments = new ArrayList<>();  // 학과 데이터를 ArrayList에 추가
    private ArrayList<Takes> takes = new ArrayList<>();     // 성적 데이터를 ArrayList에 추가
    private ArrayList<Course> courses = new ArrayList<>(); // 강의 데이터를 ArrayList에 추가
    private ArrayList<Enrollment> enrollments = new ArrayList<>(); // 수강 데이터를 ArrayList에 추가


    public DataRepository(){
        initializeData();
    }

    public void initializeData(){
        // 학생 데이터를 ArrayList에 추가
        students.add(new Student("1292001", "900424-1825409", "김광식", 3, "서울", 920));
        students.add(new Student("1292002", "900305-2730021", "김정현", 3, "서울", 923));
        students.add(new Student("1292003", "891021-2308302", "김현정", 4, "대전", 925));
        students.add(new Student("1292301", "880902-2704012", "김정숙", 2, "대구", 923));
        students.add(new Student("1292303", "910715-1524390", "박광수", 3, "광주", 920));
        students.add(new Student("1292305", "921011-1809003", "김우주", 4, "부산", 925));
        students.add(new Student("1292501", "900825-1506390", "박철수", 3, "대전", 923));
        students.add(new Student("1292502", "911011-1809003", "백태성", 3, "서울", 925));

        // 교수 데이터를 ArrayList에 추가
        professors.add(new Professor("92001", "590327-1839240", "이태규", 920, "교수", "1997"));
        professors.add(new Professor("92002", "690702-1350026", "고희석", 923, "교수", "2003"));
        professors.add(new Professor("92301", "741011-2765501", "최성희", 920, "부교수", "2005"));
        professors.add(new Professor("92302", "750728-1102458", "김태석", 923, "부교수", "1999"));
        professors.add(new Professor("92501", "620505-1200546", "박철재", 925, "교수", "2007"));
        professors.add(new Professor("92502", "740101-1830264", "장민석", 920, "조교수", "2005"));

        // 학과 데이터를 ArrayList에 추가
        departments.add(new Department(920, "컴퓨터공학과", "201호"));
        departments.add(new Department(923, "산업공학과", "207호"));
        departments.add(new Department(925, "전자공학과", "308호"));

        // 성적 데이터를 ArrayList에 추가
        takes.add(new Takes("1292001", "C101-01", "B+"));
        takes.add(new Takes("1292001", "C103-01", "A+"));
        takes.add(new Takes("1292001", "C301-01", "A"));
        takes.add(new Takes("1292002", "C102-01", "A"));
        takes.add(new Takes("1292002", "C103-01", "B+"));
        takes.add(new Takes("1292002", "C502-01", "C+"));
        takes.add(new Takes("1292003", "C103-02", "B"));
        takes.add(new Takes("1292003", "C501-02", "A+"));
        takes.add(new Takes("1292301", "C102-01", "C+"));
        takes.add(new Takes("1292303", "C102-01", "C"));
        takes.add(new Takes("1292303", "C103-02", "B+"));
        takes.add(new Takes("1292303", "C501-01", "A+"));

    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public ArrayList<Takes> getTakes() {
        return takes;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }
}
