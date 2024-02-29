package com.pe.nttdata.primer.controller;


import com.pe.nttdata.primer.entity.Pasivo;
import com.pe.nttdata.primer.services.services.BancoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.constraints.NotNull;

/**
 *Implement EmpresaRepository. <br/>
 *<b>Class</b>: {@link EmpresaController}<br/>
 *<b>Copyright</b>: &Copy; 2024 NTTDATA Per&uacute;. <br/>
 *<b>Company</b>: NTTDATA del Per&uacute;. <br/>
 *
 *@author NTTDATA Per&uacute;. (EVE) <br/>
 *<u>Developed by</u>: <br/>
 *<ul>
 *<li>Hugo Oliveros Monti</li>
 *</ul>
 *<u>Changes</u>:<br/>
 *<ul>
 *<li>feb. 29, 2024 (acronym) Creation class.</li>
 *</ul>
 *@version 1.0
 */
@RestController
@RequestMapping("banco/api/v1")
@CrossOrigin("*")
@Slf4j
public class BancoController {


    @Autowired
    private BancoService bancoService;

    /*
    @Autowired
    private ActivoService activoService;

    @Autowired
    private MessageSource messageSource;*/

    @GetMapping(value = "/all")
    public Flux<Pasivo> getAll() {
        return bancoService.findAllPasivo();
    }


    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Pasivo> save(@RequestBody @NotNull Pasivo pasivo) {
        return bancoService.saveWithVerification(pasivo);
    }

    /*

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
                    .findFirst()
                    .get();

        } catch (NullPointerException | NoSuchElementException e) {
        }

        if (ObjectUtils.isEmpty(activoReturn.get_id())) {
            activoReturn = activoService.save(activo);
        } else {

            double opcional = activoService
                    .getAll().stream()
                    .filter(f -> f.getPersona().getDni().equals(activo.getPersona().getDni()) ||
                            f.getEmpresa().getNombre().equals(activo.getEmpresa().getNombre()))
                    .mapToDouble(m -> {
                        BigDecimal resultSum = m.getTarjeta().getMontoConsumed().add(activo.getTarjeta().getMontoConsumed());
                        return resultSum.doubleValue();
                    }).findFirst().getAsDouble();

            activoReturn.getTarjeta().setMontoConsumed(new BigDecimal(opcional));
            activoService.update(activoReturn);
        }

        return Mono.just(Objects.requireNonNull(activoReturn));
    }*/


}