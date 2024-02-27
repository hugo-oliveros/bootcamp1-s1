package com.pe.nttdata.s1.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pe.nttdata.s1.entity.Empresa;
import com.pe.nttdata.s1.services.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("empresa/api/v1")
@CrossOrigin("*")
@Slf4j
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping(value = "/all")
    public Flux<List<Empresa>> getAll() {
        return Flux.
                fromIterable(empresaService
                        .getAll()).buffer();
    }

    @GetMapping(value = "/find/{id}")
    public Mono<Empresa> find(@PathVariable("id") @NotNull String objectId) throws JsonProcessingException, IllegalArgumentException {
        return empresaService.findById(objectId)
                .doOnSuccess(res -> log.info("{}",res))
                .onErrorContinue((throwable, o) -> System.out.println("error with " + o));
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Empresa>  save(@RequestBody @NotNull Empresa empresa) {
        return Mono.just(empresaService.save(empresa));

    }

    @DeleteMapping(value = "/delete/{id}")
    public Void delete(@PathVariable("id") @NotNull String objectId) {
        return empresaService.deleteById(objectId);
    }

}



