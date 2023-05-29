package com.project.ProgettoSad.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.Result;
import com.project.ProgettoSad.model.Round;
import com.project.ProgettoSad.model.TestCase;
import com.project.ProgettoSad.service.FirstScenarioGameServiceImpl;
import com.project.ProgettoSad.service.RoundService;
import com.project.ProgettoSad.service.SecondScenarioGameServiceImpl;
import com.project.ProgettoSad.service.ThirdScenarioGameServiceImpl;


@RestController
public class GameController {
	
	@Autowired
	private FirstScenarioGameServiceImpl FirstGameService;
	@Autowired
	private SecondScenarioGameServiceImpl SecondGameService;
	@Autowired
	private ThirdScenarioGameServiceImpl ThirdGameService;
	
	@Autowired
	private RoundService roundService;
	
	@GetMapping("/games")
	public ResponseEntity <List <Game>> getAllGames(){
		return ResponseEntity.ok().body(FirstGameService.getAllGames());
	}
	
	@GetMapping("/rounds")
	public ResponseEntity <List <Round>> getAllRounds(){
		return ResponseEntity.ok().body(roundService.getAllRounds());
	}
	
	@GetMapping("/games/{GID}")
	public ResponseEntity <Game> getGameById(@PathVariable ObjectId GID){
		return ResponseEntity.ok().body(FirstGameService.getGameById(GID));
	}
	
	@GetMapping("/rounds/{RID}")
	public ResponseEntity <Round> getRoundById(@PathVariable ObjectId RID){
		return ResponseEntity.ok().body(roundService.getRoundById(RID));
	}
	
	@GetMapping("/games/player/{PID}")
	public ResponseEntity <List<Game>> readPlayerHistory(@PathVariable String PID){
		return ResponseEntity.ok().body(this.ThirdGameService.readPlayerHistory(PID));
	}
	
	@GetMapping("/games/rounds/{GID}")
	public ResponseEntity <List<JSONObject>> readGame(ObjectId GID){
		Game gameEntity = this.FirstGameService.getGameById(GID);
		List<Round> roundEntityList = this.roundService.getRoundByGID(GID);
		
		List<JSONObject> entities = new ArrayList<JSONObject>();
		
		JSONObject gEntity = new JSONObject ();
		gEntity.put("game id",gameEntity.getId());
		gEntity.put("Start Date",gameEntity.getStartDate());
		gEntity.put("End Date",gameEntity.getId());
		gEntity.put("Host",gameEntity.getHost());
		gEntity.put("Guests",gameEntity.getGuest());
		gEntity.put("Scenario",gameEntity.getScenario());
		gEntity.put("Total Round Number",gameEntity.getTotalRoundNumber());
		gEntity.put("Class Under Test",gameEntity.getClassUt());
		gEntity.put("Robot",gameEntity.getWinner());
		
		entities.add(gEntity);
		
		for(Round n : roundEntityList) {
			JSONObject rEntity = new JSONObject();
			rEntity.put("round id", n.getGameId());
			rEntity.put("game id", n.getRoundId());
			rEntity.put("round number", n.getRoundNumber());
			rEntity.put("test case", n.getTestCase());
			entities.add(rEntity);
		}
	
		return ResponseEntity.ok().body(entities);
	}
	
	@PostMapping("/games")
	public ResponseEntity<String> createGame(@RequestBody Game game){
		if(game.getScenario() == 1) {
			return ResponseEntity.ok().body(this.FirstGameService.createGame(game));
		}
		else if (game.getScenario() == 2) {
			return ResponseEntity.ok().body(this.SecondGameService.createGame(game));
		}
		else {
			return ResponseEntity.ok().body(this.ThirdGameService.createGame(game));
		}
	}
	
	@PutMapping("/games/end/{GID}")
	public ResponseEntity <Game> endGame(@PathVariable ObjectId GID, @RequestBody String winner){
		return ResponseEntity.ok().body(this.FirstGameService.endGame(GID,winner));
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
