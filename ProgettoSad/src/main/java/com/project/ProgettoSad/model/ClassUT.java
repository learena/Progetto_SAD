package com.project.ProgettoSad.model;

public class ClassUT {
	private String classId;
	private String classBody;

	//CONSTRUCTORS
	public ClassUT() {
		super();
	}

	public ClassUT(String classId, String classBody) {
		super();
		this.classId = classId;
		this.classBody = classBody;
	}

	//GETTERSETTER
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassBody() {
		return classBody;
	}

	public void setClassBody(String classBody) {
		this.classBody = classBody;
	}

	//TOSTRING
	@Override
	public String toString() {
		return "ClassUT [classId=" + classId + ", classBody=" + classBody + "]";
	}
}
