package com.project.ProgettoSad.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.ProgettoSad.model.Round;

@Repository
public interface RoundRepository extends MongoRepository<Round, ObjectId> {

}
