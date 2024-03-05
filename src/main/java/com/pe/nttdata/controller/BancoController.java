package com.pe.nttdata.controller;


import com.pe.nttdata.entity.Activo;
import com.pe.nttdata.entity.Empresa;
import com.pe.nttdata.entity.Pasivo;
import com.pe.nttdata.services.BancoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping(value = "/findByDNI/{dni}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Activo> findByDNI(@PathVariable("dni")
        @NotNull final String dni) {
        return bancoService.findByDNI(dni);
    }

    @GetMapping(value = "/updateStatusByid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Activo> updateStatusByDNI(@PathVariable("id")
                                  @NotNull final String id) {
        return bancoService.updateById(id);
    }

    @PostMapping(path = "/savePersonal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Activo> save(@RequestBody @NotNull Activo activo) {
        return bancoService.savePersonal(activo);
    }

    @PostMapping(path = "/saveBusiness",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Activo> saveBusiness(@RequestBody @NotNull Activo activo) {
        return bancoService.saveBusiness(activo);
    }

}