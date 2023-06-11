package com.project.ProgettoSad.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import com.project.ProgettoSad.model.Result;
import com.project.ProgettoSad.model.Round;
import com.project.ProgettoSad.model.TestCase;

public interface RoundService {
	
	Round updateRoundTest(ObjectId RID,TestCase testCase) throws IOException;
	
	Round updateRoundResult(ObjectId RID, Result result) throws IOException;
	
	Round readRound(ObjectId GID, int roundNumber);
	
	Round getRoundById(ObjectId RID);
	
	List<Round> getRoundByGID(ObjectId GID);
	
	List<Round> getAllRounds();
}
