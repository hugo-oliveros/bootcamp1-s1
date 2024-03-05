package com.pe.nttdata.services;

import com.pe.nttdata.repository.PersonalRepository;
import com.pe.nttdata.entity.Persona;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    public Flux<Persona> findAll() {
        return personalRepository.findAll();
    }

    public Mono<Persona> findById(String id){
        return personalRepository.findById(id);
    }

    public Mono<Persona> save(Persona persona) {
        return personalRepository.save(persona);
    }


    public Mono<Void> deleteById(String id) {
        return personalRepository.deleteById(id);
    }

}