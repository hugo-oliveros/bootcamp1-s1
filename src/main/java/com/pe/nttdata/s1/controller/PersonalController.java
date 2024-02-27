package com.pe.nttdata.s1.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.entity.Persona;
import com.pe.nttdata.s1.services.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("personal/api/v1")
@CrossOrigin("*")
@Slf4j
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping(value = "/all")
    public Flux<List<Persona>> getAll() {
        return Flux.
                fromIterable(personalService
                        .getAll()).buffer();
    }

    @GetMapping(value = "/find/{id}")
    public Mono<Persona> find(@PathVariable("id") @NotNull String objectId) throws JsonProcessingException, IllegalArgumentException {
        return personalService.findById(objectId)
                .doOnSuccess(res -> log.info("{}", res))
                .onErrorContinue((throwable, o) -> System.out.println("error with " + o));
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Persona> save(@RequestBody @NotNull Persona persona) {
        return Mono.just(personalService.save(persona));

    }

    @DeleteMapping(value = "/delete/{id}")
    public Void delete(@PathVariable("id") @NotNull String objectId) {
        return personalService.deleteById(objectId);
    }

}



