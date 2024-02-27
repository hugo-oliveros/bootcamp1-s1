package com.pe.nttdata.s1.dao;


import com.pe.nttdata.s1.entity.Activo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivoRepository extends MongoRepository<Activo, String> {


}
