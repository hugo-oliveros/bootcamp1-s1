package com.pe.nttdata.repository;


import com.pe.nttdata.entity.Empresa;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends ReactiveMongoRepository<Empresa, String> {


}
