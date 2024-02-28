package com.pe.nttdata.s1.controller;


import com.pe.nttdata.s1.commons.Producto;
import com.pe.nttdata.s1.entity.Activo;
import com.pe.nttdata.s1.entity.Pasivo;
import com.pe.nttdata.s1.services.ActivoService;
import com.pe.nttdata.s1.services.PasivoService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("banco/api/v1")
@CrossOrigin("*")
@Slf4j
public class BancoController {


    @Autowired
    private PasivoService pasivoService;

    @Autowired
    private ActivoService activoService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/all")
    public Flux<List<Pasivo>> getAll() {
        return Flux.
                fromIterable(pasivoService
                        .getAll()).buffer();
    }


    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pasivo> save(@RequestBody @NotNull Pasivo pasivo) {

        Pasivo pasivoReturn = Pasivo.builder().build();

        try {
            pasivoReturn = pasivoService
                    .getAll()
                    .stream()
                    .filter(f -> f.getPersona().getDni().equals(pasivo.getPersona().getDni()) &&
                            f.getType().equals(pasivo.getType()))
                    .findFirst()
                    .get();
            pasivoReturn.setDescrip("Client " + pasivoReturn.getPersona().getDni() + " is exists type of cta.: " + pasivoReturn.getType());
        } catch (NullPointerException | NoSuchElementException e) {
        }

        if (ObjectUtils.isEmpty(pasivoReturn.get_id()))
            pasivoReturn = pasivoService.save(pasivo);

        return Mono.just(Objects.requireNonNull(pasivoReturn));

    }


    @PostMapping(path = "/saveBusiness", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pasivo> saveBusiness(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                     @RequestBody @NotNull Pasivo pasivo) {

        Pasivo pasivoReturn = Pasivo.builder().build();

        if (!pasivo.getType().equals(Producto.AHORRO.getValue())) {
            pasivoReturn.setDescrip(messageSource.getMessage("message.bank.cta.error", null, locale));

            return Mono.just(Objects.requireNonNull(pasivoReturn));
        }

        try {
            pasivoReturn = pasivoService
                    .getAll()
                    .stream()
                    .filter(f -> f.getPersona().getDni().equals(pasivo.getPersona().getDni()) &&
                            f.getType().equals(Producto.AHORRO.getValue()))
                    .findFirst()
                    .get();
            pasivoReturn.setDescrip("Client " + pasivoReturn.getPersona().getDni() + " is exists type of cta.: " + pasivoReturn.getType());
        } catch (NullPointerException | NoSuchElementException e) {
        }

        if (ObjectUtils.isEmpty(pasivoReturn.get_id()))
            pasivoReturn = pasivoService.save(pasivo);

        return Mono.just(Objects.requireNonNull(pasivoReturn));
    }


    @PatchMapping(path = "/customerConsume", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Activo> customerConsume(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                        @RequestBody @NotNull Activo activo) {

        Activo activoReturn = Activo.builder().build();

        try {
            activoReturn = activoService
                    .getAll()
                    .stream()
                    .filter(f -> f.getPersona().getDni().equals(activo.getPersona().getDni()) ||
                            f.getEmpresa().getNombre().equals(activo.getEmpresa().getNombre()))
                    // .filter(f -> f.getTarjeta().getMontoTotal().doubleValue()<100)
                    .findFirst()
                    .get();

        } catch (NullPointerException | NoSuchElementException e) {
        }

        if (ObjectUtils.isEmpty(activoReturn.get_id())) {
            activoReturn = activoService.save(activo);
        } else {

            activoReturn.getTarjeta().setMontoConsumed(
                    totalAmount(activoReturn.getTarjeta().getMontoConsumed(),
                            activo.getTarjeta().getMontoConsumed()));
            activoService.update(activoReturn);
        }

        return Mono.just(Objects.requireNonNull(activoReturn));
    }


    private BigDecimal totalAmount(BigDecimal parameter, BigDecimal neValue) {
        var sumTotal = new BigDecimal(String.valueOf(parameter));
        BigDecimal resultSum = sumTotal.add(neValue);
        return resultSum;
    }

}