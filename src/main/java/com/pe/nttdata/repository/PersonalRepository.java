package com.pe.nttdata.repository;

import com.pe.nttdata.entity.Persona;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends ReactiveMongoRepository<Persona, String> {


}
