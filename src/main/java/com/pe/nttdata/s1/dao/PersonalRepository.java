package com.pe.nttdata.s1.dao;


import com.pe.nttdata.s1.entity.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalRepository extends MongoRepository<Persona, String> {


}
