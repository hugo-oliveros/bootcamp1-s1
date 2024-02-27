package com.pe.nttdata.s1.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.commons.GenericServiceAPI;
import com.pe.nttdata.s1.entity.Pasivo;
import reactor.core.publisher.Mono;

public interface PasivoService extends GenericServiceAPI<Pasivo, String> {

    Mono<Pasivo> findById(String _id) throws JsonProcessingException;

    Void deleteById(String _id);

}