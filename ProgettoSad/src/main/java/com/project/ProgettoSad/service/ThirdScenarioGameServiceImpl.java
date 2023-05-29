package com.project.ProgettoSad.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Qualifier("Third service")
public class ThirdScenarioGameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String createGame(Game game) {
		Game GameDB =this.gameRepository.save(game);
		for(int i = 1; i <= game.getTotalRoundNumber(); i++) {
		this.roundRepository.save(new Round(GameDB.getId(),i));
		}
		return GameDB.getId().toString();
	}

	@Override
	public Game endGame(ObjectId GID, String winner) {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		
		if(GameDB.isPresent()) {
			Game gameUpdate = GameDB.get();
			gameUpdate.setEndDate(LocalDateTime.now());
			gameUpdate.setWinner(winner);
			gameRepository.save(gameUpdate);
			return gameUpdate;
		}
		else {
			throw new ExceptionResourceNotFound("Record not found with id : " + GID);
		}
	}

	@Override
	public List<Game> readPlayerHistory(String PID) {
		Criteria criteria = new Criteria();
		criteria.orOperator(Criteria.where("host.studentId").is(PID),Criteria.where("guest.studentId").is(PID));
		Query query = new Query(criteria);
		List<Game> playerHistory = mongoTemplate.find(query,Game.class);
		return playerHistory;
	}

	@Override
	public List<Game> getAllGames() {
		return this.gameRepository.findAll();
	}

	@Override
	public Game getGameById(ObjectId GID) {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		
		if(GameDB.isPresent()) {
			return GameDB.get();
		}
		else {
			throw new ExceptionResourceNotFound("No Record exists with Id:" + GID);
		}
	}
}
