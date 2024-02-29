package com.pe.nttdata.primer.controller;


import com.pe.nttdata.primer.entity.Persona;
import com.pe.nttdata.primer.services.services.PersonalService;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("personal/api/v1")
@CrossOrigin("*")
@Slf4j
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Persona> getAll() {
        return personalService.findAll();
    }

    @GetMapping(value = "/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Persona> find(@PathVariable("id") @NotNull String id){
        return personalService.findById(id);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Persona> save(@RequestBody @NotNull Persona persona) {
        return personalService.save(persona);

    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") @NotNull String objectId) {
        return personalService.deleteById(objectId);
    }

}



