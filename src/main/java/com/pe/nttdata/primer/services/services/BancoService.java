package com.pe.nttdata.primer.services.services;

import com.pe.nttdata.primer.entity.Activo;
import com.pe.nttdata.primer.entity.Pasivo;
import com.pe.nttdata.primer.repository.ActivoRepository;
import com.pe.nttdata.primer.repository.PasivoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
@Slf4j
@AllArgsConstructor
public class BancoService {

    @Autowired
    private PasivoRepository pasivoRepository;

    @Autowired
    private ActivoRepository activoRepository;

    public Flux<Pasivo> findAllPasivo() {
        return pasivoRepository.findAll();
    }

    public Flux<Activo> findAllActivo() {
        return activoRepository.findAll();
    }

    public Mono<Pasivo> findById(String id){
        return pasivoRepository.findById(id);
    }


    public static Predicate<Pasivo> filterPasivoDNI(String dni,String typeCta) {
        return (Pasivo p) -> (p.getPersona().getDni().equals(dni) && p.getType().equals(typeCta));
    }

    public Mono<Pasivo> saveWithVerification
            (Pasivo pasivo) {
        return Mono.from(findAllPasivo()
                .filter(filterPasivoDNI(pasivo.getPersona().getDni(),pasivo.getType()))
                        .map(req->{
                            req = Pasivo.builder().build();
                            req.setDescrip("Client " +
                                    pasivo.getPersona().getDni() +
                                    " is exists type of cta.: " +
                                    pasivo.getType());
                            return req;
                        })).switchIfEmpty(pasivoRepository.save(pasivo));
    }

    public Mono<Activo> saveBusiness
            (Activo activo) {

        return  Mono.just(Activo.builder().build());
    }


    public Mono<Void> deleteById(String id) {
        return pasivoRepository.deleteById(id);
    }



}