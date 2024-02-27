package com.pe.nttdata.s1.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.entity.Activo;
import com.pe.nttdata.s1.services.ActivoService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("activo/api/v1")
@CrossOrigin("*")
@Slf4j
public class ActivoController {

    @Autowired
    private ActivoService activoService;

    @GetMapping(value = "/all")
    public Flux<List<Activo>> getAll() {
        return Flux.
                fromIterable(activoService
                        .getAll()).buffer();
    }

    @GetMapping(value = "/find/{id}")
    public Mono<Activo> find(@PathVariable("id") @NotNull String objectId) throws JsonProcessingException, IllegalArgumentException {
        return activoService.findById(objectId)
                .doOnSuccess(res -> log.info("{}", res))
                .onErrorContinue((throwable, o) -> System.out.println("error with " + o));
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Activo> save(@RequestBody @NotNull Activo activo) {
        return Mono.just(activoService.save(activo));

    }

    @DeleteMapping(value = "/delete/{id}")
    public Void delete(@PathVariable("id") @NotNull String objectId) {
        return activoService.deleteById(objectId);
    }

}



