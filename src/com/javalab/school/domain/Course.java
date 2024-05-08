package com.javalab.school.domain;

/**
 * 강좌(과목) 클래스
 * - 강좌는 학과에 속한 특정 과목을 나타냄.
 * - 강좌는 학과, 교수, 강좌번호, 강좌명 등과 관련이 있음.
 * - 강좌는 특정 학과에 속한 특정 과목을 나타내며, 해당 과목을 가르치는 교수가 존재함.
 */
public class Course {
    private String courseId;
    private String name;
    private int credit;
    private String description;

    public Course() {
    }

    public Course(String courseId, String name, int credit, String description) {
        this.courseId = courseId;
        this.name = name;
        this.credit = credit;
        this.description = description;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                ", description='" + description + '\'' +
                '}';
    }
}
