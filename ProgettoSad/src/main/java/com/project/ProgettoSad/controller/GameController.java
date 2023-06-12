package com.project.ProgettoSad.controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.POJOResponse;
import com.project.ProgettoSad.model.Round;
import com.project.ProgettoSad.service.GameService;
import com.project.ProgettoSad.service.RoundService;



@RestController
public class GameController {
	
	@Autowired
	private GameService gameService;

	@Autowired
	private RoundService roundService;
	
	@PostMapping ("/games")
	public ResponseEntity <String> createGame(@RequestBody Game game){
		return ResponseEntity.ok().body(gameService.createGame(game));
	}
	
	@GetMapping("/games")
	public ResponseEntity <List <Game>> getAllGames(){
		return ResponseEntity.ok().body(gameService.getAllGames());
	}
	
	@GetMapping("/rounds")
	public ResponseEntity <List <Round>> getAllRounds(){
		return ResponseEntity.ok().body(roundService.getAllRounds());
	}
	
	@GetMapping("/games/{GID}")
	public ResponseEntity <Game> getGameById(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(gameService.getGameById(GID));
	}
	
	@GetMapping("/rounds/{RID}")
	public ResponseEntity <Round> getRoundById(@PathVariable ObjectId RID){
		return ResponseEntity.ok().body(roundService.getRoundById(RID));
	}
	
	@GetMapping("/games/player/{PID}")
	public ResponseEntity <List<Game>> readPlayerHistory(@PathVariable String PID){
		return ResponseEntity.ok().body(this.gameService.readPlayerHistory(PID));
	}
	
	@GetMapping("/rounds/find/{GID}")
	public  ResponseEntity<List<Round>> getRoundByGID(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(this.roundService.getRoundByGID(GID));
	}
	
	@GetMapping("/games/rounds/{GID}")
    public ResponseEntity<POJOResponse> readGame(@PathVariable ObjectId GID){
        POJOResponse response = new POJOResponse(); 
        response.setGame(this.gameService.getGameById(GID));
        response.setRounds(this.roundService.getRoundByGID(GID));

        return ResponseEntity.ok().body(response);

    }
	
	@GetMapping("/rounds/{GID}/{roundNumber}")
	public ResponseEntity <String> getRoundByNumber(@PathVariable ObjectId GID, @PathVariable int roundNumber){
		return ResponseEntity.ok().body(this.roundService.getRoundByNumber(GID, roundNumber));
	}
	
	@PutMapping("/games/end/{GID}")
	public ResponseEntity <Game> endGame(@PathVariable ObjectId GID, @RequestBody String winner){
		return ResponseEntity.ok().body(this.gameService.endGame(GID,winner));
	}
	
	@PutMapping("/rounds/{RID}/{PID}")
	public ResponseEntity<Round> updateTurnTest(@PathVariable ObjectId RID, @PathVariable String PID, @RequestBody String testcase) throws IOException{
		return ResponseEntity.ok().body(this.roundService.updateTurnTest(RID,PID, testcase));
	}
	
	
	@PutMapping("/rounds/result/{RID}")
	public ResponseEntity<Round> updateRoundResult(@PathVariable ObjectId RID, @RequestBody String results) throws IOException{
		return ResponseEntity.ok().body(this.roundService.updateRoundResult(RID, results));
	}
	
	@PutMapping("/rounds/robot/{RID}")
	public ResponseEntity<Round> joinRobot(@PathVariable ObjectId RID, @RequestBody String robotTest){
		return ResponseEntity.ok().body(this.roundService.joinRobot(RID, robotTest));
	}

}
