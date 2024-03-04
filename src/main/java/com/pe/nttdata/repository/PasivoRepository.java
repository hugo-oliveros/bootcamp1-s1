package com.pe.nttdata.repository;


import com.pe.nttdata.entity.Pasivo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasivoRepository extends ReactiveMongoRepository<Pasivo, String> {


}
