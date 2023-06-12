package com.project.ProgettoSad.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import com.project.ProgettoSad.model.*;

public interface RoundService {
	
	Round updateTurnTest(ObjectId RID, String studentId, String testCase) throws IOException;
	
	Round updateRoundResult(ObjectId RID, String result) throws IOException;
	
	Round joinRobot(ObjectId RID, String robotTest);
	
	Round getRoundById(ObjectId RID);
	
	List<Round> getRoundByGID(ObjectId GID);
	
	String getRoundByNumber(ObjectId GID, int roundNumber);
	
	List<Round> getAllRounds();
}
