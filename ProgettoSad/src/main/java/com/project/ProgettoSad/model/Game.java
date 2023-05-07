package com.project.ProgettoSad.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "GameDB")
public class Game {
	@Id
	private long id;
	@NotBlank
	private LocalDateTime dataInizio ;
	private LocalDateTime dataFine;
	@NotBlank
	private int scenario;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}
	public LocalDateTime getDataFine() {
		return dataFine;
	}
	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}
	public int getScenario() {
		return scenario;
	}
	public void setScenario(int scenario) {
		this.scenario = scenario;
	}

}
