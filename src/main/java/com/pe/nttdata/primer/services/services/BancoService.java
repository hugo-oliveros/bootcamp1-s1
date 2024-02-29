package com.pe.nttdata.primer.services.services;

import com.pe.nttdata.primer.entity.Pasivo;
import com.pe.nttdata.primer.repository.PasivoRepository;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BancoService {

    @Autowired
    private PasivoRepository pasivoRepository;

    public Flux<Pasivo> findAllPasivo() {
        return pasivoRepository.findAll();
    }

    public Mono<Pasivo> findById(String id){
        return pasivoRepository.findById(id);
    }

    public Mono<Pasivo> saveWithVerification
            (Pasivo pasivo) {

        Pasivo pasivoReturn = Pasivo.builder().build();

        List<Pasivo> resultPasivo = Flux.just(findAllPasivo()
                .filter(f -> f.getPersona().getDni().equals(pasivo.getPersona().getDni()))
                .onErrorResume((e) -> {
                    log.error("Exception: --> {} ", e);
                    return Flux.just(pasivoReturn);
                }).toStream()
                .findFirst().orElse(
                        pasivoReturn)).collect(Collectors.toList()).block();

        if(resultPasivo.stream().findFirst().get().getId()==null)
            return pasivoRepository.save(pasivo);

        pasivoReturn.setDescrip("Client " +
                pasivo.getPersona().getDni() +
                " is exists type of cta.: " +
                pasivo.getType());

        return Mono.just(pasivoReturn);
    }

    public Mono<Void> deleteById(String id) {
         return pasivoRepository.deleteById(id);
    }



}