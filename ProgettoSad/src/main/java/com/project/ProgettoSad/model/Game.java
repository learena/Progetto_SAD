package com.project.ProgettoSad.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import org.bson.types.ObjectId;

@Document (collection = "GameDB")
public class Game implements Persistable<ObjectId> {
	
	@Id
	private ObjectId _id;
	@CreatedDate
	private LocalDateTime startDate; 
	private LocalDateTime endDate;
	private Host host;
	private List<Guest> guest; 
	Robot robot;
	private int scenario;
	private int totalRoundNumber;
	private ClassUT classUt;
	private String winner;
	
	//CONSTRUCTORS
	public Game(ObjectId _id, LocalDateTime startDate, LocalDateTime endDate, Host host,
			List<Guest> guest, Robot robot, int scenario, int totalRoundNumber, ClassUT classUt, String winner) {
		super();
		this._id = ObjectId.get();
		this.endDate = null;
		this.host = host;
		this.guest = guest;
		this.robot = robot;
		this.scenario = scenario;
		this.totalRoundNumber = totalRoundNumber;
		this.classUt = classUt;
		this.winner = null;
	}
	
	//GETTERSETTER
	public ObjectId getId() {
		return _id;
	}
	public void setId(ObjectId id) {
		this._id = id;
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

	@Override
	public boolean isNew() {
		return _id==null;
	}
	
}
