package com.pe.nttdata.primer.controller;

import com.pe.nttdata.primer.entity.Pasivo;

import com.pe.nttdata.primer.services.services.PasivoService;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("pasivo/api/v1")
@CrossOrigin("*")
@Slf4j
public class PasivoController {

    @Autowired
    private PasivoService pasivoService;

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Pasivo> getAll() {
        return pasivoService.findAll();
    }

    @GetMapping(value = "/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Pasivo> find(@PathVariable("id") @NotNull String id){
        return pasivoService.findById(id);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pasivo>  save(@RequestBody @NotNull Pasivo pasivo) {
        return pasivoService.save(pasivo);

    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") @NotNull String id) {
        return pasivoService.deleteById(id);
    }

    @DeleteMapping(value = "/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAll() {
        return pasivoService.deleteAll();
    }

}



