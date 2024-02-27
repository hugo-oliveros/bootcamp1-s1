package com.pe.nttdata.s1.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.commons.GenericService;
import com.pe.nttdata.s1.entity.Persona;
import reactor.core.publisher.Mono;

public interface PersonalService extends GenericService<Persona, String> {

    Mono<Persona> findById(String _id) throws JsonProcessingException;

    Void deleteById(String _id);

}