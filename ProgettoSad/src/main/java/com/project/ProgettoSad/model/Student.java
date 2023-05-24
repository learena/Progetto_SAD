package com.project.ProgettoSad.model;

import jakarta.validation.constraints.NotEmpty;

public class Student {
	@NotEmpty
	private String studentId;

	//CONSTRUCTORS
	public Student(@NotEmpty String studentId) {
		super();
		this.studentId = studentId;
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
