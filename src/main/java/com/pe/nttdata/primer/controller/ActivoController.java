package com.pe.nttdata.primer.controller;

import com.pe.nttdata.primer.entity.Activo;
import com.pe.nttdata.primer.services.services.ActivoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("activo/api/v1")
@CrossOrigin("*")
@Slf4j
public class ActivoController {

    @Autowired
    private ActivoService activoService;

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Activo> getAll() {
        return activoService.findAll();
    }

    @GetMapping(value = "/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Activo> find(@PathVariable("id") @NotNull String id){
        return activoService.findById(id);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Activo> save(@RequestBody @NotNull Activo activo) {
        return activoService.save(activo);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") @NotNull String id) {
        return activoService.deleteById(id);
    }

}