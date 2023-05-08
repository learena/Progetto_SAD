package com.project.ProgettoSad.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.service.GameService;

//Controller che coordina tutti i metodi disponibili tramite http per l'oggetto Game
@RestController
public class GameController {
	
	//richiama i metodi indicati per l'interfaccia GameService
	@Autowired
	private GameService gameService;
	
	//effettua il mapping per la richiesta http GET e va ad indicare una keyword per il suo utilizzo
	@GetMapping("/games")
	public ResponseEntity <List <Game>> getAllGames(){
		return ResponseEntity.ok().body(gameService.getAllGames());
	}
	
	@GetMapping("/games/{id}")
	public ResponseEntity <Game> getGameById(@PathVariable long id){
		return ResponseEntity.ok().body(gameService.getGameById(id));
	}
	
	@PostMapping("/games")
	public ResponseEntity <Game> createGame(@RequestBody Game game){
		return ResponseEntity.ok().body(this.gameService.createGame(game));
	}
	
	@PutMapping("/games/{id}")
	public ResponseEntity <Game> updateGame(@PathVariable long id, @RequestBody Game game){
		game.setId(id);
		return ResponseEntity.ok().body(this.gameService.updateGame(game));
	}
	
	@DeleteMapping("/games/{id}")
	public HttpStatus deleteGame(@PathVariable long id) {
		this.gameService.deleteGame(id);
		return HttpStatus.OK;
	}
}
