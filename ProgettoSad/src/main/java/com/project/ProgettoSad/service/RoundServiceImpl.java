package com.project.ProgettoSad.service;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ProgettoSad.model.*;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.repository.RoundRepository;


@Service
@Transactional
public class RoundServiceImpl implements RoundService {
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Round updateRoundTest(ObjectId RID, TestCase testCase) {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			roundUpdate.setTestCase(testCase);
			this.roundRepository.save(roundUpdate);
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id : " + RID);
		}	
	}
	
	@Override
	public Round updateRoundResult(ObjectId RID, Result result) {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			roundUpdate.setTestResult(result);
			this.roundRepository.save(roundUpdate);
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id: " + RID);
		}	
	}
	
	public Round readRound(ObjectId GID, int roundNumber) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("gameId").is(GID),Criteria.where("roundNumber").is(roundNumber));
		Query query = new Query(criteria);
		Round round = (Round) this.mongoTemplate.find(query, Round.class);
		if(round.getRoundId()== null) {
			throw new ExceptionResourceNotFound("No Round document exists for the Game with id: " + GID + "and with round number: " + roundNumber);
		}
		return round;
		
	}
	
	public List<Round> getRoundByGID(ObjectId GID) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(GID));
		List<Round> round = this.mongoTemplate.find(query, Round.class);
		if(round.isEmpty()) {
			throw new ExceptionResourceNotFound("No Round document exists for the Game with id: " + GID);
		}
		return round;
	}
	
	@Override
	public Round getRoundById(ObjectId RID) {
		Optional<Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			return RoundDB.get();
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id: " + RID);
		}
	}
	
	@Override
	public List<Round> getAllRounds(){
		return this.roundRepository.findAll();
	}
}
