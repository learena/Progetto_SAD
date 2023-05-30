package com.project.ProgettoSad.model;

import java.util.List;

public class TestCase {
	private List<String> studentTest;
	private String robotTest;
	Result result;
	
	//CONSTRUCTORS
	public TestCase() {
		super();
	}
	
	public TestCase(List<String> studentTest, String robotTest) {
		super();
		this.studentTest = studentTest;
		this.robotTest = robotTest;
	}

	public TestCase(List<String> studentTest, String robotTest, Result result) {
		super();
		this.studentTest = studentTest;
		this.robotTest = robotTest;
		this.result = result;
	}

	//GETTERSETTER
	public List<String> getStudentTest() {
		return studentTest;
	}

	public void setStudentTest(List<String> studentTest) {
		this.studentTest = studentTest;
	}

	public String getRobotTest() {
		return robotTest;
	}

	public void setRobotTest(String robotTest) {
		this.robotTest = robotTest;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	//TOSTRING
	@Override
	public String toString() {
		return "TestCase [studentTest=" + studentTest + ", robotTest=" + robotTest + ", result=" + result + "]";
	}

}
