package com.project.ProgettoSad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.exception.ExceptionGameNotFound;
import com.project.ProgettoSad.repository.GameRepository;


@Service
@Transactional
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Override
	public Game createGame(Game game) {
		return gameRepository.save(game);
	}
	
	@Override
	public Game updateGame(Game game) {
		Optional <Game> GameDB = this.gameRepository.findById(game.getId());
	
		if(GameDB.isPresent()) {
			Game gameUpdate = GameDB.get();
			gameUpdate.setId(game.getId());
			gameUpdate.setDataInizio(game.getDataInizio());
			gameUpdate.setDataFine(game.getDataFine());
			gameUpdate.setScenario(game.getScenario());
			gameRepository.save(gameUpdate);
			return gameUpdate;
		}
		else {
			throw new ExceptionGameNotFound("Record not found with id : " + game.getId());
		}
	}
	
	@Override
	public List<Game> getAllGames(){
		return this.gameRepository.findAll();
	}
	
	@Override
	public Game getGameById (long GID) {
		Optional <Game> GameDB = this.gameRepository.findById(GID);
		
		if(GameDB.isPresent()) {
			return GameDB.get();
		}
		else {
			throw new ExceptionGameNotFound("No Record exists with Id:" + GID);
		}
	}
	
	@Override
	public void deleteGame(long id) {
		Optional <Game> GameDB = this.gameRepository.findById(id);
		
		if(GameDB.isPresent()) {
			this.gameRepository.delete(GameDB.get());
		}
		else {
			throw new ExceptionGameNotFound("No Record exists with Id:" + id);
		}
	}

}
