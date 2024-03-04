package com.pe.nttdata.repository;


import com.pe.nttdata.entity.Activo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivoRepository extends ReactiveMongoRepository<Activo, String> {


}
