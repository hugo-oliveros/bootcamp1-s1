package com.pe.nttdata.controller;


import com.pe.nttdata.entity.Activo;
import com.pe.nttdata.services.ActivoService;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *Implement ActivoController. <br/>
 *<b>Class</b>: {@link ActivoController}<br/>
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
@RequestMapping("activo/api/v1")
@CrossOrigin("*")
@Slf4j
public class ActivoController {

  /**
   * Service {@link ActivoService}.
   * ActivoService activoService
   **/
  @Autowired
  private ActivoService activoService;

  /**
   * Retorna
   * {@link Flux}&lt;{@link Activo}&gt;
   * se ingresan los siguientes paramentro(s).
   *
   * @return {@link Flux}&lt;{@link Activo}&gt; Activo
   * @see Mono
   * @see Flux
   **/
  @GetMapping(value = "/all")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Activo> getAll() {
    return activoService.findAll();
  }

  /**
   * Retorna
   * {@link Mono}&lt;{@link Activo}&gt;
   * se ingresan los siguientes paramentro(s).
   *
   * @return {@link Mono}&lt;{@link Activo}&gt; Activo
   * @see Mono
   * @see Flux
   **/
  @GetMapping(value = "/find/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Activo> find(final @PathVariable("id") @NotNull String id) {
    return activoService.findById(id);
  }

  /**
   * Retorna
   * {@link Mono}&lt;{@link Activo}&gt;
   * se ingresan los siguientes paramentro(s).
   *
   * @return {@link Mono}&lt;{@link Activo}&gt; Activo
   * @see Mono
   * @see Flux
   **/
  @PostMapping(path = "/save",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Activo> save(final @RequestBody @NotNull Activo activo) {
    return activoService.save(activo);
  }

  /**
   * Retorna
   * {@link Mono}&lt;{@link Activo}&gt;
   * se ingresan los siguientes paramentro(s).
   *
   * @return {@link Mono}&lt;{@link Activo}&gt; Activo
   * @see Mono
   * @see Flux
   **/
  @DeleteMapping(value = "/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(final @PathVariable("id") @NotNull String id) {
    return activoService.deleteById(id);
  }

}
