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
	//TODO
	public Round updateTurnTest(ObjectId RID, String studentId, String testCase) throws IOException {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			//TODO CONTROLLO SU STUDENT ID
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			roundUpdate.getTurn().replace(studentId, testCase);
			this.roundRepository.save(roundUpdate);
			
			Optional <Game> GameDB = this.gameRepository.findById(roundUpdate.getGameId());
			Game gameTmp = GameDB.get();
			
			Path path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + studentId + "\\" + gameTmp.getId().toString() + "\\Round " + roundUpdate.getRoundNumber() + "\\Test Source Code");
			File fileHost = new File(path.toString());
			fileHost.mkdirs();
			
			  try { 
				  String fileName = new String("Test.class"); 
				  File test = new File(path.toString(),fileName); 
				  test.createNewFile();
				  
				  String fileToWrite = new String(path+fileName); 
				  Writer writer = new BufferedWriter (new FileWriter(fileToWrite)); 
				  writer.write(testCase);
				  
				  writer.flush(); 
				  writer.close();
			  
			  } catch (Exception e) { e.printStackTrace(); }
			  
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id : " + RID);
		}	
	}
	
	@Override
	public Round updateRoundResult(ObjectId RID, String results) throws IOException {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			Optional <Game> GameDB = this.gameRepository.findById(roundUpdate.getGameId());
			Game gameTmp = GameDB.get();
			
			roundUpdate.setRoundId(RID);
			roundUpdate.setResults(results);
			this.roundRepository.save(roundUpdate);
			
			Path path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + gameTmp.getHost().getStudentId()+"\\"+ gameTmp.getId().toString() + "\\Round " + roundUpdate.getRoundNumber() + "\\Test Report");
			File file = new File(path.toString());
			file.mkdirs();
			
			try {
				String fileName = new String("Result.txt");
				File report = new File(path.toString(),fileName);
				report.createNewFile();
				
				String fileToWrite = new String(path+"\\Result.txt");
				Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
				writer.write(results);
				
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < gameTmp.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + gameTmp.getGuest().get(i).getStudentId()+"\\"+ gameTmp.getId().toString() + "\\Round " + roundUpdate.getRoundNumber() + "\\Test Report");
				file = new File(path.toString());
				file.mkdirs();
				try {
					String fileName = new String("Result.txt");
					File report = new File(path.toString(),fileName);
					report.createNewFile();
					
					String fileToWrite = new String(path+"\\Result.txt");
					Writer writer = new BufferedWriter (new FileWriter(fileToWrite));
					writer.write(results);
					
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
	
	@Override
	public Round joinRobot(ObjectId RID, String robotTest) {
		Optional <Round> RoundDB = this.roundRepository.findById(RID);
		
		if(RoundDB.isPresent()) {
			Round roundUpdate = RoundDB.get();
			roundUpdate.setRoundId(RID);
			roundUpdate.setRobotTest(robotTest);
			this.roundRepository.save(roundUpdate);
			
			return roundUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("No Round document exists with id:" + RID);
		}
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
	public String getRoundByNumber(ObjectId GID, int roundNumber) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("gameId").is(GID),Criteria.where("roundNumber").is(roundNumber));
		Query query = new Query(criteria);
		Round round = (Round) this.mongoTemplate.find(query, Round.class);
		if(round.getRoundId()== null) {
			throw new ExceptionResourceNotFound("No Round document exists for the Game with id: " + GID + "and with round number: " + roundNumber);
		}
		return round.getRoundId().toString();
	}
	
	@Override
	public List<Round> getAllRounds(){
		return this.roundRepository.findAll();
	}
}
