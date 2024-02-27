package com.pe.nttdata.s1.controller;


import com.pe.nttdata.s1.entity.Pasivo;
import com.pe.nttdata.s1.services.PasivoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("pasivo/api/v1")
@CrossOrigin("*")
@Slf4j
public class PasivoController {

    @Autowired
    private PasivoService pasivoService;

    @GetMapping(value = "/all")
    public Flux<List<Pasivo>> getAll() {
        return Flux.
                fromIterable(pasivoService
                        .getAll()).buffer();
    }

    @GetMapping(value = "/find/{id}")
    public Mono<Pasivo> find(@PathVariable("id") @NotNull String objectId) throws JsonProcessingException, IllegalArgumentException {
        return pasivoService.findById(objectId)
                .doOnSuccess(res -> log.info("{}",res))
                .onErrorContinue((throwable, o) -> System.out.println("error with " + o));
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pasivo>  save(@RequestBody @NotNull Pasivo pasivo) {
        return Mono.just(pasivoService.save(pasivo));

    }

    @DeleteMapping(value = "/delete/{id}")
    public Void delete(@PathVariable("id") @NotNull String objectId) {
        return pasivoService.deleteById(objectId);
    }

}



