package com.project.ProgettoSad.model;

import java.util.List;
import java.util.ArrayList;

public class Result {
	private List<Boolean> compilationResult;
	private List<Integer> studentScore;
	private Integer robotScore;
	
	//CONSTRUCTORS
	public Result() {
		super();
		this.compilationResult = new ArrayList<>();
		this.studentScore = new ArrayList<>();
	}

	public Result(List<Boolean> compilationResult, List<Integer> studentScore, Integer robotScore) {
		super();
		this.compilationResult = compilationResult;
		this.studentScore = studentScore;
		this.robotScore = robotScore;
	}
	
	//GETTERSETTER
	public List<Boolean> getCompilationResult() {
		return compilationResult;
	}

	public void setCompilationResult(List<Boolean> compilationResult) {
		this.compilationResult = compilationResult;
	}

	public List<Integer> getStudentScore() {
		return studentScore;
	}

	public void setStudentScore(List<Integer> studentScore) {
		this.studentScore = studentScore;
	}

	public Integer getRobotScore() {
		return robotScore;
	}

	public void setRobotScore(Integer robotScore) {
		this.robotScore = robotScore;
	}

	//TOSTRING
		@Override
	public String toString() {
		return "Result [compilationResult=" + compilationResult + ", studentScore=" + studentScore + ", robotScore="
				+ robotScore + "]";
	}
	
	
	
	
}
