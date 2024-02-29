package com.pe.nttdata.primer.services.services;

import com.pe.nttdata.primer.entity.Pasivo;
import com.pe.nttdata.primer.repository.PasivoRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PasivoService {

    @Autowired
    private PasivoRepository pasivoRepository;

    public Flux<Pasivo> findAll() {
        return pasivoRepository.findAll();
    }

    public Mono<Pasivo> findById(String id){
        return pasivoRepository.findById(id);
    }

    public Mono<Pasivo> save(Pasivo pasivo) {
        return pasivoRepository.save(pasivo);
    }

    public Mono<Void> deleteById(String id) {
         return pasivoRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return pasivoRepository.deleteAll();
    }
}