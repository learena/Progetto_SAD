package com.project.ProgettoSad.model;


public class Student {
	private String studentId;

	//CONSTRUCTORS
	public Student(String studentId) {
		super();
		this.studentId = studentId;
	}
	
	public Student() {
		super();
	}

	//GETTERSETTER
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	//TOSTRING
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + "]";
	}
	
}
