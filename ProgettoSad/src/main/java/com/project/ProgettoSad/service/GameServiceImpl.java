package com.project.ProgettoSad.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
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
import com.opencsv.CSVWriter;
import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionMandatoryFields;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.repository.GameRepository;
import com.project.ProgettoSad.repository.RoundRepository;


@Service
@Transactional
public class GameServiceImpl implements GameService {
	 
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void check(Game game) throws ExceptionMandatoryFields, ExceptionIllegalParameters {
		if(game.getHost() == null || game.getClassUt() == null || game.getRobot() == null) {
			throw new ExceptionMandatoryFields("Host, Robot and ClassUT are mandatory!");
		}
		
		if(game.getScenario() != 3 && game.getGuest() != null) {
			throw new ExceptionIllegalParameters("Game doesn't allow for Guests!");
		}
		else if((game.getScenario() == 1 && game.getTotalRoundNumber() != 1)) {
			throw new ExceptionIllegalParameters("First Scenario cannot have more than one round!");
		}
	}
	
	public void checkWinner(String winner, Game game) throws ExceptionIllegalParameters{
		Guest tmp = new Guest(winner);
		if(game.getScenario() != 3) {
			if(!game.getHost().getStudentId().equals(winner) && !game.getRobot().getRobotId().equals(winner)) {
				throw new ExceptionIllegalParameters("Winner hasn't played the game!");
			}
		}
		else {
			if(!game.getHost().getStudentId().equals(winner) && !game.getRobot().getRobotId().equals(winner)) {
				Boolean found = false;
				int i = 0;
				while(found.equals(false) && i < game.getGuest().size()) {
					if(game.getGuest().get(i).getStudentId().equals(winner)) {
						found = true;
					}
					i++;
				}
				if(found.equals(false)) {
					throw new ExceptionIllegalParameters("Winner hasn't played the game!");
				}
			}
		}
	}
	
	@Override
	public String createGame(Game game) throws ExceptionIllegalParameters, ExceptionMandatoryFields {
		
		check(game);
		
		Game GameDB = gameRepository.save(game);
		if (game.getScenario() == 1) {
			Round roundTmp = new Round(GameDB.getId(),1);
			roundTmp.getTurn().put(game.getHost().getStudentId(), "N/A");
			this.roundRepository.save(roundTmp );
		}
		else if(game.getScenario() == 2) {
			for(int i = 1; i <= game.getTotalRoundNumber(); i++) {
				Round roundTmp = new Round(GameDB.getId(),i);
				roundTmp.getTurn().put(game.getHost().getStudentId(), "N/A");
				this.roundRepository.save(roundTmp);
			}
		}
		else {
			for(int i = 1; i <= game.getTotalRoundNumber(); i++) {
				Round roundTmp = new Round(GameDB.getId(),i);
					roundTmp.getTurn().put(game.getHost().getStudentId(), "N/A");
					for(int j = 0; j < game.getGuest().size(); j++) {
						roundTmp.getTurn().put(game.getGuest().get(j).getStudentId(), "N/A");
					}				
					
				this.roundRepository.save(roundTmp);
			}
		}
		
		Path path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getHost());
		if(!Files.exists(path)) {
			File fileHost = new File(path.toString());
			fileHost.mkdirs();
		}
		
		if(game.getScenario() == 3) {
			for(int i = 0; i < game.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getGuest().get(i).getStudentId());
				if(!Files.exists(path)) {
					File fileGuest = new File(path.toString());
					fileGuest.mkdirs();
				}
			}
		}

		
		path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getHost() + "\\" + game.getId().toString());
		File fileHostGame = new File(path.toString());
		fileHostGame.mkdirs();
		
		if(game.getScenario() == 3) {
			for(int i = 0; i < game.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Public\\AUTName\\" + game.getGuest().get(i).getStudentId() + "\\" + game.getId().toString());
				File fileGuestGame = new File(path.toString());
				fileGuestGame.mkdirs();
			}	
		}

		return GameDB.getId().toString();		
	}
	
	@Override
	public Game endGame(ObjectId GID,String winner) throws IOException,ExceptionIllegalParameters {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		if(GameDB.isPresent()) {
			Game gameUpdate = GameDB.get();
			gameUpdate.setId(GID);
			checkWinner(winner,gameUpdate);
			gameUpdate.setWinner(winner);
			gameUpdate.setEndDate(LocalDateTime.now());
			this.gameRepository.save(gameUpdate);
			
			Path path = Paths.get("C:\\Users\\Public\\AUTName\\" + gameUpdate.getHost() + "\\" + gameUpdate.getId().toString()+"\\Game.csv");
			File file = new File(path.toString());
			//file.mkdirs();
			
			try {
				FileWriter writer = new FileWriter(file);
				CSVWriter csvWriter = new CSVWriter(writer);
				
				String[] header = {"ID", "Host", "Guest","Robot","Difficulty","Scenario","Number of Rounds","ClassUT ID","ClassUT Path","Started At","Ended At","Winner"};
				csvWriter.writeNext(header);
				csvWriter.flush();

				if(gameUpdate.getScenario()==3) {
					String[] data = {gameUpdate.getId().toString(), gameUpdate.getHost().getStudentId(), gameUpdate.getGuest().toString(), gameUpdate.getRobot().getRobotId(), gameUpdate.getRobot().getDifficulty(), ""+gameUpdate.getScenario()+"", ""+gameUpdate.getTotalRoundNumber()+"", gameUpdate.getClassUt().getClassId(), gameUpdate.getClassUt().getClassBody(), gameUpdate.getStartDate().toString(), gameUpdate.getEndDate().toString(), gameUpdate.getWinner()};
					csvWriter.writeNext(data);
					csvWriter.flush();				

					for(int i = 0; i < gameUpdate.getGuest().size(); i++) {
						path = Paths.get("C:\\Users\\Public\\AUTName\\" + gameUpdate.getGuest().get(i).getStudentId() + "\\" + gameUpdate.getId().toString()+"\\Game.csv");
						file = new File(path.toString());

						writer = new FileWriter(file);
						csvWriter = new CSVWriter(writer);

						csvWriter.writeNext(header);
						csvWriter.writeNext(data);
					}
				}
				else {
					String[] data = {gameUpdate.getId().toString(), gameUpdate.getHost().getStudentId(), "N/A", gameUpdate.getRobot().getRobotId(), gameUpdate.getRobot().getDifficulty(), ""+gameUpdate.getScenario()+"", ""+gameUpdate.getTotalRoundNumber()+"", gameUpdate.getClassUt().getClassId(), gameUpdate.getClassUt().getClassBody(), gameUpdate.getStartDate().toString(), gameUpdate.getEndDate().toString(), gameUpdate.getWinner()};
					csvWriter.writeNext(data);
					csvWriter.flush();
				}
				csvWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return gameUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("Game document not found with id : " + GID);
		}
	}

	@Override
	public List<Game> getAllGames(){
		return this.gameRepository.findAll();
	}
	
	@Override
	public List<Game> readPlayerHistory(String PID) {
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("host.studentId").is(PID),Criteria.where("guest.studentId").is(PID));
		Query query = new Query(criteria);
		List<Game> playerHistory = mongoTemplate.find(query,Game.class);
		if(playerHistory.isEmpty()) {
			throw new ExceptionResourceNotFound("No player exists within the collection with Id:"+PID);
		}
		return playerHistory;
	}

	
	@Override
	public Game getGameById (ObjectId GID) {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		
		if(GameDB.isPresent()) {
			return GameDB.get();
		}
		else {
			throw new ExceptionResourceNotFound("No Game document exists with Id:" + GID);
		}
	}
}
