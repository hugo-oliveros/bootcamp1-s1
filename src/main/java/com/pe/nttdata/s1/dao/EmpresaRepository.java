package com.pe.nttdata.s1.dao;


import com.pe.nttdata.s1.entity.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {


}
