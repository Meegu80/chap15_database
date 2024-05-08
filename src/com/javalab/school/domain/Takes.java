package com.javalab.school.domain;

/**
 * 성적 클래스
 */
public class Takes {
	private String studentId;
	private String classId;
	private String score;

	public Takes() {
	}

	public Takes(String studentId, String classId, String score) {
		this.studentId = studentId;
		this.classId = classId;
		this.score = score;
	}

	// Getters and Setters
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Takes{" +
				"studentId='" + studentId + '\'' +
				", classId='" + classId + '\'' +
				", score='" + score + '\'' +
				'}';
	}
}
