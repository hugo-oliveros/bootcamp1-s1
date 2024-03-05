package com.pe.nttdata.repository;


import com.pe.nttdata.entity.Moviento;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovimientoRepository extends ReactiveMongoRepository<Moviento, String> {


}
