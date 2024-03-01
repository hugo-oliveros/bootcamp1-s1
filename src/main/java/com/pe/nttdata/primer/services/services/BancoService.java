package com.pe.nttdata.primer.services.services;

import com.pe.nttdata.primer.entity.Activo;
import com.pe.nttdata.primer.entity.Pasivo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;
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
     * @param dni parameter DNI
     * @param typeCta parameter Type Cta.
     * @return Predicate
     *
     **/
    public static Predicate<Pasivo> filterPasivo(final String dni,
                                                 final String typeCta) {
        return (Pasivo req) -> (req.getPersona().getDni().equals(dni)
                && req.getType().equals(typeCta));
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param dni parameter DNI
     * @param nombreEmpresa parameter name Company.
     * @return Predicate
     *
     **/
    public static Predicate<Activo> filterActivo(final String dni,
                                                 final String nombreEmpresa) {
        return (Activo req) -> (req.getPersona().getDni().equals("")
                || req.getEmpresa().getNombre().equals(nombreEmpresa));
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param pasivo parameter PASIVO
     * @return Mono
     *
     **/
    public Mono<Pasivo> saveWithVerification(final Pasivo pasivo) {
        return Mono.from(findAllPasivo()
                .filter(filterPasivo(pasivo.getPersona().getDni(),
                        pasivo.getType()))
                        .map(req -> {
                            req = Pasivo.builder().build();
                            req.setDescrip("Client "
                                    + pasivo.getPersona().getDni()
                                    + " is exists type of cta.: "
                                    + pasivo.getType());
                            return req;
                        })).switchIfEmpty(pasivoService.save(pasivo));
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
        return Mono.from(findAllActivo()
                .filter(filterActivo(activo.getPersona().getDni(),
                        activo.getEmpresa().getNombre()))
                .map(req -> {
                    activo.getTarjeta().setMontoConsumed(req.getTarjeta()
                            .getMontoConsumed()
                            .add(activo.getTarjeta().getMontoConsumed()));
                    return req;
                }))
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        return activoService.update(
                                optional.get().getId().toString(), activo);
                    }
                    return Mono.empty();
                }).switchIfEmpty(activoService.save(activo));
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
