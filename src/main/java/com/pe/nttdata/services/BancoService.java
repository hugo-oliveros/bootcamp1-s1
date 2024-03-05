package com.pe.nttdata.services;

import com.pe.nttdata.commons.ProductoEnum;
import com.pe.nttdata.entity.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 *Implement BancoService. <br/>
 *<b>Class</b>: {@link BancoService}<br/>
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
@Service
@Slf4j
@AllArgsConstructor
public class BancoService {

    /**
     * <>p</>
     * .
     * PasivoService pasivoService
     **/
    @Autowired
    private PasivoService pasivoService;

    /**
     * <>p</>
     * .
     * ActivoService activoService
     **/
    @Autowired
    private ActivoService activoService;

    /**
     * <>p</>
     * .
     * MessageSource messageSource
     **/
    @Autowired
    private MessageSource messageSource;


    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Flux<Pasivo> findAllPasivo() {
        return pasivoService.findAll();
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Flux<Activo> findAllActivo() {
        return activoService.findAll();
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id parameter ID
     * @return return all elements from Mono passing
     * for reactivate Mono and return Status OK.
     *
     **/
    public Mono<Pasivo> findPasivoById(final String id) {
        return pasivoService.findById(id);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id parameter ID
     * @return return all elements from Mono passing
     * for reactivate Mono and return Status OK.
     *
     **/
    public Mono<Activo> findActivoById(final String id) {
        System.out.println("ID"+id);
        return activoService.findById(id);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param dni parameter ID
     * @return return all elements from Mono passing
     * for reactivate Mono and return Status OK.
     *
     **/
    public Mono<Activo> findByDNI(final String dni) {
        return activoService.findByDNI(dni);
    }


    public Mono<Activo> updateById(final String id) {
        return findActivoById(id).map(m->{
            m.setStatus("PERSONAL-ON");
            return m;
        }).flatMap(req->{
            return activoService.update(id, req);
        });
    }


    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param dni parameter DNI
     * @return Predicate
     *
     **/
    public static Predicate<Activo> filterPersonal(final String dni) {
        return (Activo req) -> (req.getPersona().getDni().equals(dni) );
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param rucEmpresa parameter RucEmpresa
     * @return Predicate
     *
     **/
    public static Predicate<Activo> filterEmpresa(final String rucEmpresa) {
        return (Activo req) -> (req.getEmpresa().getRuc().equals(rucEmpresa));
    }


    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param activo parameter PASIVO
     * @return Mono
     *
     **/
    public Mono<Activo> savePersonal(final Activo activo) {
        AtomicInteger maxMov = new AtomicInteger(0);

        Empresa empresa = Empresa.builder().build();
        empresa.setNombre("");
        empresa.setDireccion("");
        empresa.setRuc("");
        activo.setEmpresa(empresa);

        activo.setType("STAFF");//Personal
        activo.setStatus("PERSONAL-OFF");

        return Mono.from(findAllActivo()
                .filter(filterPersonal(activo.getPersona().getDni()))
                        .map(req -> {
                            TarjetaCredito tarjetaCredito = TarjetaCredito.builder().build();
                            tarjetaCredito.setMontoTotal(req.getTarjeta().getMontoTotal());
                            String typeCLiente = ProductoEnum.valueOf(req.getTypeCliente()).getValue();
                            maxMov.set(req.getMaxMoviento());//Atomic
                            ObjectId id = req.getId();
                            req = Activo.builder().build();
                            req.setId(id);
                            req.setTypeCliente(typeCLiente);
                            req.setMaxMoviento(maxMov.get());
                            req.setDescrip("Client "
                                    + activo.getPersona().getDni()
                                    + " is exists type of cta.: "
                                    + activo.getType());
                            return req;
                        })).switchIfEmpty(activoService.save(activo))
                .onErrorResume(error->{
                    log.error(error.getMessage());
                    Activo act = Activo.builder().build();
                    act.setDescrip("Finder error."+error.getCause().getMessage());
                    return Mono.just(act);
                });
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Mono
     * @param activo parameter ACTIVO
     * @return Mono
     *
     **/
    public Mono<Activo> saveBusiness(final Activo activo) {

        Persona persona = Persona.builder().build();
        persona.setNombre("");
        persona.setApellido("");
        persona.setDni("");
        activo.setPersona(persona);

        activo.setType("BUSINESS");
        activo.setStatus("BUSSNESS-OFF");

        return Mono.from(findAllActivo()
                .filter(filterEmpresa(activo.getEmpresa().getRuc()))
                .map(req -> {
                    /*activo.getTarjeta().setMontoConsumed(req.getTarjeta()
                            .getMontoConsumed()
                            .add(activo.getTarjeta().getMontoConsumed()));*/
                    String typeCLiente = ProductoEnum.valueOf(req.getTypeCliente()).getValue();
                    ObjectId id = req.getId();
                    req = Activo.builder().build();
                    req.setId(id);
                    req.setTypeCliente(typeCLiente);
                    req.setDescrip("Client "
                            + activo.getEmpresa().getRuc()
                            + " is exists type of cta.: "
                            + activo.getType());
                    return req;
                })).switchIfEmpty(activoService.save(activo))
                /*.map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        return activoService.update(
                                optional.get().getId().toString(), activo);
                    }
                    return Mono.empty();
                }).switchIfEmpty(activoService.save(activo))*/
                .onErrorResume(error->{
                    log.error(error.getMessage());
                    Activo act = Activo.builder().build();
                    act.setDescrip("Finder error.");
                    return Mono.just(act);
                });
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Mono
     * @param id parameter id
     * @return Mono Void
     *
     **/
    public Mono<Void> deleteById(final String id) {
        return pasivoService.deleteById(id);
    }

}
