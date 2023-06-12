package com.project.ProgettoSad.service;

import java.io.File;
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
	
	@Override
	public String createGame(Game game) {
		
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
		
		Path path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + game.getHost());
		if(!Files.exists(path)) {
			File fileHost = new File(path.toString());
			fileHost.mkdirs();
		}
		
		if(game.getScenario() == 3) {
			for(int i = 0; i < game.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + game.getGuest().get(i).getStudentId());
				if(!Files.exists(path)) {
					File fileGuest = new File(path.toString());
					fileGuest.mkdirs();
				}
			}
		}

		
		path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + game.getHost() + "\\" + game.getId().toString());
		File fileHostGame = new File(path.toString());
		fileHostGame.mkdirs();
		
		if(game.getScenario() == 3) {
			for(int i = 0; i < game.getGuest().size(); i++) {
				path = Paths.get("C:\\Users\\Volgani\\Desktop\\AUTName\\" + game.getGuest().get(i).getStudentId() + "\\" + game.getId().toString());
				File fileGuestGame = new File(path.toString());
				fileGuestGame.mkdirs();
			}	
		}

		return GameDB.getId().toString();		
	}
	
	@Override
	public Game endGame(ObjectId GID,String winner) {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		if(GameDB.isPresent()) {
			Game gameUpdate = GameDB.get();
			gameUpdate.setId(GID);
			gameUpdate.setWinner(winner);
			gameUpdate.setEndDate(LocalDateTime.now());
			this.gameRepository.save(gameUpdate);
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
