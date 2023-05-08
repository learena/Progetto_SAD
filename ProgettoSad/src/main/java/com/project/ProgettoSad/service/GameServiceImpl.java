package com.project.ProgettoSad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.exception.ExceptionGameNotFound;
import com.project.ProgettoSad.repository.GameRepository;

//implementazione dei servizi indicati nell'interfaccia, indicato dall'annotazione Service
//l'aggettivo transactional indicato per la classe fa in modo che 
//la transizione faccia roll back in caso di RuntimeException e Error 
@Service
@Transactional
public class GameServiceImpl implements GameService {
	
	//Injection point 
	@Autowired
	private GameRepository gameRepository;
	
	//l'overriding permette l'implementazione di tutti i metodi indicati nell'interfaccia
	//createGame salva l'elemento di tipo Game in ingresso nel database
	@Override
	public Game createGame(Game game) {
		return gameRepository.save(game);
	}
	
	//updateGame controlla che l'elemento di tipo Game in ingresso sia presente nel database:
	//se è presente procede a farne l'update settandone tutti i campi e poi salvando
	//se non è presente lancia un'eccezione
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
	
	//getAllGames crea un oggetto List contenente tutti i Game presenti nel database
	@Override
	public List<Game> getAllGames(){
		return this.gameRepository.findAll();
	}
	
	//getGameById ritorna l'oggetto Game con l'Id corrispondente a quello in ingresso
	//se questo è presente nel database, altrimenti lancia un'eccezione
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
	
	//deleteGame elimina dal database l'oggetto Game con l'Id corrispondente a quello in ingresso
	//se questo è presente nel database, altrimenti lancia un'eccezione
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
