package com.project.ProgettoSad.service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.*;
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
import com.project.ProgettoSad.repository.GameRepository;
import com.project.ProgettoSad.repository.RoundRepository;


@Service
@Transactional
public class RoundServiceImpl implements RoundService {
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Round updateRoundTest(ObjectId RID, TestCase testCase) throws IOException {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			//TODO MODIFICA IL PATH
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			roundUpdate.setTestCase(testCase);
			this.roundRepository.save(roundUpdate);
			
			Optional <Game> GameDB = this.gameRepository.findById(roundUpdate.getGameId());
			Game gameTmp = GameDB.get();
			
			Path path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + gameTmp.getHost() + "\\" + gameTmp.getId().toString() + "\\Round_" + roundUpdate.getRoundNumber());
			File fileHost = new File(path.toString());
			fileHost.mkdirs();
			
			try {
				String fileName = new String("TestCase.java");
				File test = new File(path.toString(),fileName);
				test.createNewFile();
				
				String fileToWrite = new String(path+fileName);
				Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
				writer.write(testCase.getStudentTest().get(0));
				writer.flush();
				writer.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < gameTmp.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + gameTmp.getGuest().get(i).getStudentId() + "\\" + gameTmp.getId().toString() + "\\Round_" + roundUpdate.getRoundNumber());
				File fileGuest = new File(path.toString());
				fileGuest.mkdirs();
				
				try {
					String fileName = new String("TestCase.java");
					File test = new File(path.toString(),fileName);
					test.createNewFile();
					
					String fileToWrite = new String(path+fileName);
					Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
					writer.write(testCase.getStudentTest().get(i+1));
					writer.flush();
					writer.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id : " + RID);
		}	
	}
	
	@Override
	public Round updateRoundResult(ObjectId RID, Result result) throws IOException {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			Optional <Game> GameDB = this.gameRepository.findById(roundUpdate.getGameId());
			Game gameTmp = GameDB.get();
			
			roundUpdate.setRoundId(RID);
			roundUpdate.setTestResult(result);
			this.roundRepository.save(roundUpdate);
			
			Path path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + gameTmp.getHost()+"\\"+ gameTmp.getId().toString() + "\\Round_" + roundUpdate.getRoundNumber());
			File file = new File(path.toString());
			file.mkdir();
			
			try {
				String fileName = new String("Result.txt");
				File report = new File(path.toString(),fileName);
				report.createNewFile();
				
				String fileToWrite = new String(path+"\\Result.txt");
				Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
				if(result.getCompilationResult().get(0)) {
					writer.write("Host Compilation Result (TRUE=PASS/FALSE=FAIL):"+result.getCompilationResult().get(0).toString()+ "\nHost Score:" + result.getStudentScore().get(0).toString());
				}
				else {
					writer.write("Host Compilation Result (TRUE=PASS/FALSE=FAIL):"+result.getCompilationResult().get(0).toString()+ "\nStudent did not pass the test!");
				}
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < gameTmp.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + gameTmp.getGuest().get(i).getStudentId()+"\\"+ gameTmp.getId().toString() + "\\Round_" + roundUpdate.getRoundNumber());
				file = new File(path.toString());
				file.mkdirs();
				try {
					String fileName = new String("Result.txt");
					File report = new File(path.toString(),fileName);
					report.createNewFile();
					
					String fileToWrite = new String(path+"\\Result.txt");
					Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
					if(result.getCompilationResult().get(i+1)) {
						writer.write("Guest Compilation Result (TRUE=PASS/FALSE=FAIL):"+result.getCompilationResult().get(i+1).toString()+ "\nGuest Score:" + result.getStudentScore().get(i+1).toString());
					}
					else {
						writer.write("Guest Compilation Result (TRUE=PASS/FALSE=FAIL):"+result.getCompilationResult().get(i+1).toString()+ "\nStudent did not pass the test!");
					}
					writer.flush();
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
