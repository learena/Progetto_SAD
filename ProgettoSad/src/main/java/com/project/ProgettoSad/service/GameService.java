package com.project.ProgettoSad.service;

import java.util.List;
import com.project.ProgettoSad.model.Game;

//interfaccia relativa ai servizi messi a disposizione riguardo la classe Game
public interface GameService {
	
	Game createGame(Game game);
	
	Game updateGame (Game game);
	
	List<Game> getAllGames();
	
	Game getGameById(long GID);
	
	void deleteGame(long id);
}
