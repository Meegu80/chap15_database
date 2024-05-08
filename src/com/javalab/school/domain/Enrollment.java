package com.javalab.school.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private int enrollId;   // 수강 ID, 자동증가
    private String studentId;   // 학생id
    private String classId;     // 수업id
    private String semester;    // 학기 1, 2, 여름, 겨울
    private String grade;       // 성적
    private LocalDateTime enrollmentDate;   // 수강신청일

    public Enrollment() {
    }

    public Enrollment(int enrollmentId, String studentId, String courseId, String semester, String grade, LocalDateTime registrationDate) {
        this.enrollId = enrollmentId;
        this.studentId = studentId;
        this.classId = courseId;
        this.semester = semester;
        this.grade = grade;
        this.enrollmentDate = registrationDate;
    }

    public int getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(int enrollId) {
        this.enrollId = enrollId;
    }

    // Getter and Setter methods
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollId +
                ", studentId=" + studentId +
                ", courseId=" + classId +
                ", semester='" + semester + '\'' +
                ", grade='" + grade + '\'' +
                ", registrationDate=" + enrollmentDate +
                '}';
    }
}
