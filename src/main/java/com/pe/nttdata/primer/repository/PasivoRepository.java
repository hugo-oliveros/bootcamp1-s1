package com.pe.nttdata.primer.repository;


import com.pe.nttdata.primer.entity.Pasivo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasivoRepository extends ReactiveMongoRepository<Pasivo, String> {


}
