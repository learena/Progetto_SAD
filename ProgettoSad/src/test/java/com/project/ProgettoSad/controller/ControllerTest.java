package com.project.ProgettoSad.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;


import com.project.ProgettoSad.exception.ExceptionIllegalParameters;
import com.project.ProgettoSad.exception.ExceptionMandatoryFields;
import com.project.ProgettoSad.exception.ExceptionResourceNotFound;
import com.project.ProgettoSad.model.ClassUT;
import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.Guest;
import com.project.ProgettoSad.model.Host;
import com.project.ProgettoSad.model.Robot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.Argument;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import groovy.json.*;

class ControllerTest {

	@Test
	void givenEverythingIsOk_WhenGameIsCreated_Then200StatusCodeIsReceived()throws ExceptionMandatoryFields {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(200);
	}
	
	@Test
	void givenHostIsNotInserted_WhenGameIsCreated_Then500CodeIsReceived()throws ExceptionMandatoryFields {
		
		
		Game game = new Game();
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);
		
		
		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	@Test
	void givenClassUTIsNotInserted_WhenGameIsCreated_Then500CodeIsReceived()throws ExceptionMandatoryFields {
	
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		Robot robot = new Robot("Evosuite");
		game.setRobot(robot);
		
		
		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	@Test
	void givenRobotIsNotInserted_WhenGameIsCreated_Then500CodeIsReceived()throws ExceptionMandatoryFields {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		game.setClassUt(classUt);
		
		
		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	@Test
	void givenScenarioOutOfBoundary_WhenGameIsCreated_Then400StatusCodeIsReceived()throws ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(5);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(400);
	}

	@Test
	void givenNegativeTotalRoundNumber_WhenGameIsCreated_Then400StatusCodeIsReceived()throws ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(2);
		game.setTotalRoundNumber(-1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(400);
	}
	
	@Test
	void givenIllegalGuests_WhenGameIsCreated_Then500StatusCodeIsReceived()throws ExceptionMandatoryFields,ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		List<Guest> guest = new ArrayList<Guest>();
		Guest boh = new Guest("heh");
		guest.add(boh);
		game.setGuest(guest);
		game.setTotalRoundNumber(1);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	@Test
	void givenIncorrectTotalRoundNumber_WhenGameIsCreated_Then500StatusCodeIsReceived()throws ExceptionIllegalParameters {
		
		Game game = new Game();
		game.setHost(new Host("Ciccio"));
		game.setScenario(1);
		game.setTotalRoundNumber(2);
		ClassUT classUt = new ClassUT("boh","bohhhh");
		Robot robot = new Robot("Evosuite");
		game.setClassUt(classUt);
		game.setRobot(robot);

		given().contentType("application/json").body(game).when().post("/games").then().assertThat().statusCode(500);
	}
	
	@Test
	void GivenEverythingIsOk_WhenGameIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6488d08bff95c849a16d1f96").when().get("/games/{GID}").then()
		.assertThat()
		.body("host.studentId", equalTo("Ciccio Cecchin"))
		.body("robot.difficulty", equalTo("Normal"))
		.body("robot.robotId", equalTo("Evosuite"))
		.body("classUt.classId", equalTo("boh"))
		.body("classUt.classBody", equalTo("heh"))
		.body("scenario", equalTo(1))
		.statusCode(200);
	}

	@Test
	void GivenGameIsNotPresent_WhenGameIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").when().get("/games/{GID}").then().assertThat().statusCode(500);
	}

	@Test
	void GivenEverythingIsOk_WhenRoundIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("RID","6488d08bff95c849a16d1f97").when().get("/rounds/{RID}").then().assertThat().statusCode(200);
	}
	
	@Test
	void GivenRoundIsNotPresent_WhenRoundIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("RID","123456789012345678901234").when().get("/rounds/{RID}").then().assertThat().statusCode(500);
	}
	
	@Test
	void GivenEverythingIsOk_WhenRoundByGameIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6488d08bff95c849a16d1f96").when().get("/rounds/find/{GID}").then().assertThat().statusCode(200);
	}
	
	@Test
	void GivenRoundIsNotPresent_WhenRoundByGameIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").when().get("/rounds/find/{GID}").then().assertThat().statusCode(500);
	}

	@Test
	void GivenEverythingIsOk_WhenGameWithRoundsIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","6489c8c7eb21cc25289e6f2e").when().get("/games/rounds/{GID}").then()
		.assertThat()
		.body("game.host.studentId", equalTo("Ciccio_Cecchin"))
		.body("game.robot.robotId", equalTo("Evosuite"))
		.body("game.classUt.classId", equalTo("boh"))
		.body("game.scenario", equalTo(1))
		.body("rounds[0].turn.Ciccio_Cecchin", equalTo("N/A"))
		.statusCode(200);
	}
	
	@Test
	void GivenRoundIsNotPresent_WhenGameWithRoundsIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").when().get("/games/rounds/{GID}").then().assertThat().statusCode(500);
	}
	
	@Test
	void GivenEverythingIsOk_WhenRoundByNumberIsRequested_Then200StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().get("/rounds/{GID}/{roundNumber}","6489c8c7eb21cc25289e6f2e",1).then().assertThat().statusCode(200);
	}
	
	@Test
	void GivenRoundIsNotPresent_WhenRoundByNumberIsRequested_Then500StatusCodeIsReceived() throws ExceptionResourceNotFound {
		given().pathParam("GID","123456789012345678901234").pathParam("roundNumber", 1).when().get("/rounds/{GID}/{roundNumber}").then().assertThat().statusCode(500);
	}

	@Test
	void GivenEverythingIsOk_WhenPlayerHistoryIsRequested_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound {
		given().pathParam("PID","Ciccio_Cecchin").when().get("/games/player/{PID}").then().assertThat()
		.body("_id.toString()",equalTo("[6489c8c7eb21cc25289e6f2e]"))
		.statusCode(200);
	}
	
	@Test
	void GivenPlayerDoesNotExist_WhenPlayerHistoryIsRequested_ThenStatusCode500IsReceived() throws ExceptionResourceNotFound {
		given().pathParam("PID","Ciccio_Cecchini").when().get("/games/player/{PID}").then().assertThat().statusCode(500);
	}

	@Test
	void GivenEverythingIsOk_WhenGameEnds_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound {
		String winner = new String("Ciccio_Cecchin");
		given().contentType("application/json").body(winner).pathParam("GID","6489c8c7eb21cc25289e6f2e").when().put("/games/end/{GID}").then().assertThat()
		.body("winner", equalTo(winner))
		.statusCode(200);
	}
	
	@Test
	void GivenEverythingIsOk_WhenGameEnds_ThenStatusCode200IsReceived() throws ExceptionResourceNotFound {
		String winner = new String("Ciccio_Cecchin");
		given().contentType("application/json").body(winner).pathParam("GID","6489c8c7eb21cc25289e6f2e").when().put("/games/end/{GID}").then().assertThat()
		.body("winner", equalTo(winner))
		.statusCode(200);
	}


	@Test
	void testUpdateTurnTest() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateRoundResult() {
		fail("Not yet implemented");
	}

	@Test
	void testJoinRobot() {
		fail("Not yet implemented");
	}

}
