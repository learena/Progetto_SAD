package com.project.ProgettoSad.model;

//formato date supportato da Java
import java.time.LocalDateTime;

//annotation che permette l'implementazione del vincolo Not Blank
import jakarta.validation.constraints.NotBlank;
//annotation che indica che il valore è un identificativo
import org.springframework.data.annotation.Id;
//annotation che identifica un oggetto che deve essere presente nel database
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "GameDB")
public class Game {
	
	@Id
	private long id; //identificativo univoco del documento
	@NotBlank
	private LocalDateTime dataInizio ; 
	private LocalDateTime dataFine;
	@NotBlank
	private int scenario; //da aggiungere constraints sui valori che può assumere
	
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
