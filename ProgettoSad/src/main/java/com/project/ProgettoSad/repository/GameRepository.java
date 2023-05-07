package com.project.ProgettoSad.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.ProgettoSad.model.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, Long> {

}
