package com.pe.nttdata.primer.repository;

import com.pe.nttdata.primer.entity.Persona;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends ReactiveMongoRepository<Persona, String> {


}
