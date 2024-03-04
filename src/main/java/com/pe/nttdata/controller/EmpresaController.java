package com.pe.nttdata.controller;


import com.pe.nttdata.entity.Empresa;
import com.pe.nttdata.repository.EmpresaRepository;
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
@RequestMapping("empresa/api/v1")
@CrossOrigin("*")
@Slf4j
public class EmpresaController {

   /**
    * <>p</>
    * .
    * EmpresaRepository empresaRepository
    **/
    @Autowired
    private EmpresaRepository empresaRepository;


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
    public Flux<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing for
     * reactivate Flux passing the id as a parameter
     * @param id id is the input parameter and must not be null
     * @return return all elements from Mono passing
     * for reactivate Mono with status OK.
     *
     **/
    @GetMapping(value = "/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Empresa> findById(@PathVariable("id")
                                      @NotNull final String id) {
        return empresaRepository.findById(id);
    }

    /**
     * <>p</>
     * .
     * Save information from the entity Empresa
     * @param empresa the input parameter of the entity
     * @return return all elements from Mono passing
     * for reactivate Mono with status Create.
     *
     **/
    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Empresa> save(@RequestBody @NotNull final Empresa empresa) {
        return empresaRepository.save(empresa);
    }


    /**
     * <>p</>
     * .
     * Delete information from the entity Empresa
     * @param id id is the input parameter and must not be null
     * @return return all elements from Mono passing
     * for reactivate Mono with status Create.
     *
     **/
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") @NotNull final String id) {
        return empresaRepository.deleteById(id);
    }

}

