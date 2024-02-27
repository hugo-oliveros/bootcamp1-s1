package com.pe.nttdata.s1.controller;


import com.pe.nttdata.s1.entity.Pasivo;
import com.pe.nttdata.s1.services.PasivoService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

@RestController
@RequestMapping("banco/api/v1")
@CrossOrigin("*")
@Slf4j
public class BancoController {


    @Autowired
    private PasivoService pasivoService;

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pasivo> save(@RequestBody @NotNull Pasivo pasivo) {

        Pasivo pasivoReturn = Pasivo.builder().build();

        try {
            pasivoReturn = pasivoService
                    .getAll()
                    .stream()
                    .filter(f -> f.getPersona().getDni().equals(pasivo.getPersona().getDni()) &&
                            f.getType().equals(pasivo.getType()))
                    .findAny()
                    .get();
            pasivoReturn.setDescrip("Client "+pasivoReturn.getPersona().getDni()+" is exists type of cta.: "+pasivoReturn.getType());
        } catch (NullPointerException | NoSuchElementException e) {
        }

        if (ObjectUtils.isEmpty(pasivoReturn.get_id()))
            pasivoReturn = pasivoService.save(pasivo);

        return Mono.just(Objects.requireNonNull(pasivoReturn));

    }


}



