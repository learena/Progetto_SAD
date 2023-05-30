package com.project.ProgettoSad.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.Result;
import com.project.ProgettoSad.model.Round;
import com.project.ProgettoSad.model.TestCase;
import com.project.ProgettoSad.service.FirstScenarioGameServiceImpl;
import com.project.ProgettoSad.service.POJOResponse;
import com.project.ProgettoSad.service.RoundService;
import com.project.ProgettoSad.service.SecondScenarioGameServiceImpl;
import com.project.ProgettoSad.service.ThirdScenarioGameServiceImpl;


@RestController
public class GameController {
	
	@Autowired
	private FirstScenarioGameServiceImpl MainGameService;
	@Autowired
	private SecondScenarioGameServiceImpl SecondGameService;
	@Autowired
	private ThirdScenarioGameServiceImpl ThirdGameService;
	
	@Autowired
	private RoundService roundService;
	
	@GetMapping("/games")
	public ResponseEntity <List <Game>> getAllGames(){
		return ResponseEntity.ok().body(MainGameService.getAllGames());
	}
	
	@GetMapping("/rounds")
	public ResponseEntity <List <Round>> getAllRounds(){
		return ResponseEntity.ok().body(roundService.getAllRounds());
	}
	
	@GetMapping("/games/{GID}")
	public ResponseEntity <Game> getGameById(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(MainGameService.getGameById(GID));
	}
	
	@GetMapping("/rounds/{RID}")
	public ResponseEntity <Round> getRoundById(@PathVariable ObjectId RID){
		return ResponseEntity.ok().body(roundService.getRoundById(RID));
	}
	
	@GetMapping("/games/player/{PID}")
	public ResponseEntity <List<Game>> readPlayerHistory(@PathVariable String PID){
		return ResponseEntity.ok().body(this.ThirdGameService.readPlayerHistory(PID));
	}
	
	@GetMapping("/rounds/find/{GID}")
	public  ResponseEntity<List<Round>> getRoundByGID(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(this.roundService.getRoundByGID(GID));
	}
	
	@GetMapping("/games/rounds/{GID}")
    public ResponseEntity<POJOResponse> readGame(@PathVariable ObjectId GID){
        POJOResponse response = new POJOResponse(); 
        response.setGame(this.MainGameService.getGameById(GID));
        response.setRounds(this.roundService.getRoundByGID(GID));

        return ResponseEntity.ok().body(response);

    }
	
	@PutMapping("/games/end/{GID}")
	public ResponseEntity <Game> endGame(@PathVariable ObjectId GID, @RequestBody String winner){
		return ResponseEntity.ok().body(this.MainGameService.endGame(GID,winner));
	}
	
	@PutMapping("/rounds/test/{RID}")
	public ResponseEntity<Round> updateRoundTest(@PathVariable ObjectId RID, @RequestBody TestCase testcase){
		return ResponseEntity.ok().body(this.roundService.updateRoundTest(RID, testcase));
	}
	
	
	@PutMapping("/rounds/result/{RID}")
	public ResponseEntity<Round> updateRoundTest(@PathVariable ObjectId RID, @RequestBody Result result){
		return ResponseEntity.ok().body(this.roundService.updateRoundResult(RID, result));
	}

}
