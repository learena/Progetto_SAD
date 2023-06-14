package com.project.ProgettoSad.repository;

import org.bson.types.ObjectId;
//Classe indicata come Repository, permette di accedere alle funzionalità del database
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.ProgettoSad.model.Game;

/**
*Interfaccia publica per la connessione al repository delle partite
*
*/
@Repository
public interface GameRepository extends MongoRepository<Game,ObjectId> {

}
