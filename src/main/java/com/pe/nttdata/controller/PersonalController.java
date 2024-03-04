package com.pe.nttdata.controller;

import com.pe.nttdata.entity.Persona;
import com.pe.nttdata.services.PersonalService;
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
 *Implement PersonalController. <br/>
 *<b>Class</b>: {@link PersonalController}<br/>
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
@RequestMapping("personal/api/v1")
@CrossOrigin("*")
@Slf4j
public class PersonalController {

    /**
     * <>p</>
     * .
     * PersonalService personalService
     **/
    @Autowired
    private PersonalService personalService;

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Persona> getAll() {
        return personalService.findAll();
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id parameter id
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    @GetMapping(value = "/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Persona> find(final @PathVariable("id") @NotNull String id) {
        return personalService.findById(id);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param persona parameter Persona entity
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Persona> save(final @RequestBody @NotNull Persona persona) {
        return personalService.save(persona);

    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id parameter id
     * @return return Mono Void
     *
     **/
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(final @PathVariable("id") @NotNull String id) {
        return personalService.deleteById(id);
    }

}
