package com.pe.nttdata.s1.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.commons.GenericService;
import com.pe.nttdata.s1.entity.Activo;
import reactor.core.publisher.Mono;

public interface ActivoService extends GenericService<Activo, String> {

    Mono<Activo> findById(String _id) throws JsonProcessingException;

    Void update(Activo activo);

    Void deleteById(String _id);

}