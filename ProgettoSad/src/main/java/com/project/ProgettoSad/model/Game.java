package com.project.ProgettoSad.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.bson.types.ObjectId;

@Document (collection = "GameDB")
public class Game {
	
	@Id
	private ObjectId id; 
	@NotBlank
	private LocalDateTime startDate; 
	private LocalDateTime endDate;
	@NotEmpty
	private Host host;
	private List<Guest> guest;
	@NotEmpty 
	Robot robot;
	@NotBlank
	private int scenario;
	private int totalRoundNumber;
	@NotEmpty
	private ClassUT classUt;
	private String winner;
	

	//CONSTRUCTORS
	public Game(@NotEmpty Host host,
			@NotEmpty Robot robot, @NotBlank int scenario, @NotEmpty ClassUT classUt) {
		super();
		this.id = new ObjectId();
		this.startDate = LocalDateTime.now();
		this.host = host;
		this.robot = robot;
		this.scenario = scenario;
		this.classUt = classUt;
	}

	//GETTERSETTER
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public Host getStudente() {
		return host;
	}
	public void setStudente(Host host) {
		this.host = host;
	}
	public int getScenario() {
		return scenario;
	}
	public void setScenario(int scenario) {
		this.scenario = scenario;
	}
	public ClassUT getClassUt() {
		return classUt;
	}
	public void setClassUt(ClassUT classUt) {
		this.classUt = classUt;
	}
	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}
		
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<Guest> getGuest() {
		return guest;
	}

	public void setGuest(List<Guest> guest) {
		this.guest = guest;
	}

	public int getTotalRoundNumber() {
		return totalRoundNumber;
	}

	public void setTotalRoundNumber(int totalRoundNumber) {
		this.totalRoundNumber = totalRoundNumber;
	}
	
	//TOSTRING
	@Override
	public String toString() {
		return "Game [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", host=" + host + ", guest="
				+ guest + ", robot=" + robot + ", scenario=" + scenario + ", totalRoundNumber=" + totalRoundNumber
				+ ", classUt=" + classUt + ", winner=" + winner + "]";
	}
	
}
