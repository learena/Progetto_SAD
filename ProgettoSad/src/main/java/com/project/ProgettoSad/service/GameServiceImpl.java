package com.project.ProgettoSad.service;

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
		this.roundRepository.save(new Round(GameDB.getId(),1));
		return GameDB.getId().toString();
		}
		else {
			for(int i = 1; i <= game.getTotalRoundNumber(); i++) {
				this.roundRepository.save(new Round(GameDB.getId(),i));
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
