package com.javalab.school.domain;

import java.sql.Date;

/**
 * 수업 클래스
 *  - 수업(Class)은 강의(강좌)에 대한 구체적인 수업 정보를 저장하는 역할
 *  - 수업은 특정 강좌가 실제로 언제(학기), 어디서, 누구(교수)에 의해 가르쳐지는지를 구체적으로 나타냄
 *  - 수업은 강좌, 교수, 학생, 강의실 등과 관련이 있음
 *  - 연도와 학기는 수업이 특정 학기에 개설되는 타이밍을 나타내는 중요한 속성
 *    각 학기마다 수업은 다르게 조직될 수 있으며, 동일 강좌라도 다른 교수가 다른
 *    시간에 다른 장소에서 가르칠 수 있다
 *  - 수업 테이블은 강좌(Course) 테이블과 학생의 수강(Enrollment) 테이블 간의 중개 역할을 함.
 *  - 강좌는 학기마다 여러 개의 수업으로 나뉘어 질 수 있고, 각 수업은 특정 연도와 학기에 개설되며,
 *    특정 교수가 강의를 진행하고, 특정 교실에서 수업이 이루어짐.
 */
public class Class {
    private String classId;    // 수업 ID
    private String courseId;    // 강좌 ID
    private int year;           // 연도
    private String semester;    // 학기(1, 2, 여름, 겨울)
    private String professorId; // 교수 ID
    private String classroom;   // 강의실
    private int enroll;         // 수강 인원

    public Class() {
    }

    public Class(String classId, String courseId, int year, String semester, String professorId, String classroom, int enroll) {
        this.classId = classId;
        this.courseId = courseId;
        this.year = year;
        this.semester = semester;
        this.professorId = professorId;
        this.classroom = classroom;
        this.enroll = enroll;
    }

    // Getters and Setters
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getEnroll() {
        return enroll;
    }

    public void setEnroll(int enroll) {
        this.enroll = enroll;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId='" + classId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", professorId='" + professorId + '\'' +
                ", classroom='" + classroom + '\'' +
                ", enroll=" + enroll +
                '}';
    }
}
