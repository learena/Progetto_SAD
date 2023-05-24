package com.project.ProgettoSad.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.project.ProgettoSad.model.Result;
import com.project.ProgettoSad.model.Round;
import com.project.ProgettoSad.model.TestCase;

public interface RoundService {
	
	Round updateRoundTest(ObjectId RID,TestCase testCase);
	
	Round updateRoundResult(ObjectId RID, Result result);
	
	Round readRound(ObjectId GID, int roundNumber);
	
	Round getRoundById(ObjectId RID);
	
	List<Round> getRoundByGID(ObjectId GID);
	
	List<Round> getAllRounds();
}
