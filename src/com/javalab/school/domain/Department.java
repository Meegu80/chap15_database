package com.javalab.school.domain;

public class Department {

	private int departmentId;			//학과 코드
	private String name;		//학과명
	private String office;		//교실
	
	public Department() {		
	}	
	public Department(int departmentId, String name, String office) {
		this.departmentId = departmentId;
		this.name = name;
		this.office = office;
	}

	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", name=" + name + ", office=" + office + "]";
	}
}