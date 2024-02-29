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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BancoService {


    private Pasivo pasivoReturn = Pasivo.builder().build();

    @Autowired
    private PasivoRepository pasivoRepository;

    public Flux<Pasivo> findAllPasivo() {
        return pasivoRepository.findAll();
    }

    public Mono<Pasivo> findById(String id){
        return pasivoRepository.findById(id);
    }


    public static Predicate<Pasivo> filterDNI(String dni,String typeCta) {
        return (Pasivo p) -> (p.getPersona().getDni().equals(dni) && p.getType().equals(typeCta));
    }

    public Mono<Pasivo> saveWithVerification
            (Pasivo pasivo) {

        pasivoReturn = Pasivo.builder().build();
        pasivoReturn.setDescrip("Client " +
                pasivo.getPersona().getDni() +
                " is exists type of cta.: " +
                pasivo.getType());

        List<Pasivo> lpasivo = Flux.just(findAllPasivo()
                .filter(filterDNI(pasivo.getPersona().getDni(),pasivo.getType()))
                .onErrorResume((e) -> {
                    log.error("Exception: --> {} ", e);
                    return Flux.just(pasivoReturn);
                }).toStream()
                .findAny()
                .orElse(pasivoReturn)).collectList().block();

        if(lpasivo.stream().findFirst().get().getId()==null)
           pasivoReturn = pasivoRepository.save(pasivo).block();

        return Mono.just(pasivoReturn);
    }

    public Mono<Void> deleteById(String id) {
         return pasivoRepository.deleteById(id);
    }



}