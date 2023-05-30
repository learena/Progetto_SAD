package com.project.ProgettoSad.service;

import java.util.List;

import com.project.ProgettoSad.model.Game;
import com.project.ProgettoSad.model.Round;

public class POJOResponse {

    private Game game;
    private List<Round> rounds;

    public POJOResponse () {


    }

    public POJOResponse(Game game, List<Round> rounds) {
        this.setGame(game);
        this.rounds= rounds; 
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

}
	
