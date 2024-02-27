package com.pe.nttdata.s1.dao;


import com.pe.nttdata.s1.entity.Pasivo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasivoRepository extends MongoRepository<Pasivo, String> {


}
