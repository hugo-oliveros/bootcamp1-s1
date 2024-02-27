package com.pe.nttdata.s1.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.commons.GenericServiceAPI;
import com.pe.nttdata.s1.entity.Empresa;
import reactor.core.publisher.Mono;

public interface EmpresaService extends GenericServiceAPI<Empresa, String> {

    Mono<Empresa> findById(String _id) throws JsonProcessingException;

    Void deleteById(String _id);

}