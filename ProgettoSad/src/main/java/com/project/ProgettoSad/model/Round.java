package com.project.ProgettoSad.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "RoundDB")
public class Round {
	@Id
	private ObjectId roundId;
	private ObjectId gameId;
	private int roundNumber;
	private TestCase testCase;
	
	//CONSTRUCTORS
	public Round() {
		super();
		this.roundId = new ObjectId();
	}
	
	public Round(int roundNumber) {
		super();
		this.roundId = new ObjectId();
		this.roundNumber = roundNumber;
	}
	
	public Round(ObjectId gameId, int roundNumber) {
		super();
		this.gameId = gameId;
		this.roundNumber = roundNumber;
	}

	public Round(ObjectId roundId, ObjectId gameId, int roundNumber, TestCase testCase) {
		super();
		this.roundId = roundId;
		this.gameId = gameId;
		this.roundNumber = roundNumber;
		this.testCase = testCase;
	}
	//GETTERSETTER
	public ObjectId getRoundId() {
		return roundId;
	}

	public void setRoundId(ObjectId roundId) {
		this.roundId = roundId;
	}
	
	public ObjectId getGameId() {
		return gameId;
	}

	public void setGameId(ObjectId gameId) {
		this.gameId = gameId;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public void setTestResult(Result result) {
		this.testCase.result = result;
	}
	//TOSTRING
	@Override
	public String toString() {
		return "Round [roundId=" + roundId + ", gameId=" + gameId + ", roundNumber=" + roundNumber + ", testCase="
				+ testCase + "]";
	}

	
	
	
}
