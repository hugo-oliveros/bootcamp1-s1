package com.pe.nttdata.primer.services.services;


import com.pe.nttdata.primer.repository.ActivoRepository;
import com.pe.nttdata.primer.entity.Activo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class ActivoService {

    @Autowired
    private ActivoRepository activoRepository;


    public Flux<Activo> findAll() {
        return activoRepository.findAll();
    }

    public Mono<Activo> findById(String id) {
        return activoRepository.findById(id);
    }

    public Mono<Activo> save(Activo activo) {
        return activoRepository.save(activo);
    }
    public  Mono<Activo> update(String id,Activo activo) {
        return activoRepository.findById(id)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        activo.setId(new ObjectId(id));
                        return activoRepository.save(activo);
                    }
                    return Mono.empty();
                });
    }


    public Mono<Void> deleteById(String id) {
        return activoRepository.deleteById(id);
    }

}