package com.project.ProgettoSad.model;

public class Guest extends Student {
	
	//CONSTRUCTORS
	public Guest(String studentId) {
		super(studentId);
	}

	@Override
	public String toString() {
		return "|["+this.getStudentId()+"]|";
	}
	
	

}
