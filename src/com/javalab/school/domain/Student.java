package com.javalab.school.domain;

/**
 * 학생클래스
 */
public class Student {
	private String studentId;			//학번
	private String jumin; 		//주민번호
	private String name; 		//이름
	private int year;			//학년
	private String address;		//주소
	private int departmentId;		//학과code

	//기본 생성자
	public Student() {		
	}
	
	//오버로딩 생성자		printStudent(students);
	public Student(String studentId, String jumin, String name, int year, String address, int departmentId) {
		this.studentId = studentId;
		this.jumin = jumin;
		this.name = name;
		this.year = year;
		this.address = address;
		this.departmentId = departmentId;
	}

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}	
	
	// 학생 객체의 내부 정보를 보여주는 메소드
	//public void showStudentInfo() {
	//	System.out.println(this.id + "\t" + this.jumin + "\t" + this.name + "\t" + this.year + "\t" + this.address + "\t" + this.department);
	//}
}
