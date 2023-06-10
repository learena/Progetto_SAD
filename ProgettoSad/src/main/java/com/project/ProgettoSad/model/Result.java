package com.project.ProgettoSad.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.ArrayList;

public class Result {
	@JsonSerialize(using= ToStringSerializer.class)
	private List<Boolean> compilationResult;
	@JsonSerialize(using= ToStringSerializer.class)
	private List<Integer> studentScore;
	@JsonSerialize(using= ToStringSerializer.class)
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
		return "Result [ \nCompilation Results (True=Pass/False=Fail):\n" + compilationResult + "\nStudents' Scores:\n" + studentScore + "\nRobot's Score:"
				+ robotScore + "\n]";
	}
	
	
	
	
}
