package com.javalab.school.domain;

//교수 클래스
public class Professor {

	//필드
	private String professorId;	//교수번호
	private String jumin;	//주민번호
	private String name;	//이름
	private int departmentId;	//학과code
	private String grade;	// 직급(교수, 조교수, 부교수, 강사)
	private String hiredate;	//입사년도
	
	//기본생성자
	public Professor() {}
	
	//오버로딩 생성자
	public Professor(String professorId, String jumin, String name, int departmentId, String grade, String hiredate) {
		this.professorId = professorId;
		this.jumin = jumin;
		this.name = name;
		this.departmentId = departmentId;
		this.grade = grade;
		this.hiredate = hiredate;
	}

	//getter/setter 메소드
	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	@Override
	public String toString() {
		return "Professor [id=" + professorId + ", jumin=" + jumin + ", name=" + name + ", department=" + departmentId + ", grade="
				+ grade + ", hiredate=" + hiredate + "]";
	}	
	
	
	
}