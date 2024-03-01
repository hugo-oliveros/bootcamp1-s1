package com.pe.nttdata.primer.controller;


import com.pe.nttdata.primer.commons.ProductoEnum;
import com.pe.nttdata.primer.entity.Activo;
import com.pe.nttdata.primer.entity.Pasivo;
import com.pe.nttdata.primer.services.services.BancoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.constraints.NotNull;
import java.util.Locale;

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
    private ActivoService activoService;*/

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/allPasivo")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Pasivo> getAllPasivo() {
        return bancoService.findAllPasivo();
    }

    @GetMapping(value = "/allActivo")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Activo> getAllActivo() {
        return bancoService.findAllActivo();
    }

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pasivo> save(@RequestBody @NotNull Pasivo pasivo) {
        return bancoService.saveWithVerification(pasivo);
    }

    @PostMapping(path = "/saveBusiness",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Activo> saveBusiness(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                     @RequestBody @NotNull Activo activo) {


        /*
        if (!pasivo.getType().equals(ProductoEnum.AHORRO.getValue())) {
            Pasivo pasivoReturn = Pasivo.builder().build();
            pasivoReturn.setDescrip(messageSource.getMessage("message.bank.cta.error", null, locale));
            //return Mono.just(pasivoReturn);
        }*/

        return bancoService.saveBusiness(activo);
        }

}