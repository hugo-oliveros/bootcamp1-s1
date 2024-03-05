package com.pe.nttdata.services;

import com.pe.nttdata.commons.ProductoEnum;
import com.pe.nttdata.entity.Activo;
import com.pe.nttdata.entity.Pasivo;
import com.pe.nttdata.entity.TarjetaCredito;
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
     * @param rucEmpresa parameter RucEmpresa
     * @return Predicate
     *
     **/
    public static Predicate<Activo> filterActivo(final String rucEmpresa) {
        return (Activo req) -> (req.getEmpresa().getRuc().equals(rucEmpresa));
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
        AtomicInteger maxMov = new AtomicInteger(0);
        return Mono.from(findAllPasivo()
                .filter(filterPasivo(pasivo.getPersona().getDni(),
                        pasivo.getType()))
                        .map(req -> {
                            TarjetaCredito tarjetaCredito = TarjetaCredito.builder().build();
                            tarjetaCredito.setMontoTotal(req.getTarjeta().getMontoTotal());
                            String typeCLiente = ProductoEnum.valueOf(req.getTypeCliente()).getValue();
                            maxMov.set(req.getMaxMoviento());//Atomic
                            ObjectId id = req.getId();
                            req = Pasivo.builder().build();
                            req.setId(id);
                            req.setCatalog("402");//isExist
                            req.setTypeCliente(typeCLiente);
                            req.setMontoTotal(tarjetaCredito.getMontoTotal());
                            req.setMaxMoviento(maxMov.get());
                            req.setDescrip("Client "
                                    + pasivo.getPersona().getDni()
                                    + " is exists type of cta.: "
                                    + pasivo.getType());
                            return req;
                        })).switchIfEmpty(pasivoService.save(pasivo))
                .onErrorResume(error->{
                    log.error(error.getMessage());
                    Pasivo pas = Pasivo.builder().build();
                    pas.setDescrip("Finder error.");
                    return Mono.just(pas);
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
        return Mono.from(findAllActivo()
                .filter(filterActivo(activo.getEmpresa().getRuc()))
                .map(req -> {
                    /*activo.getTarjeta().setMontoConsumed(req.getTarjeta()
                            .getMontoConsumed()
                            .add(activo.getTarjeta().getMontoConsumed()));*/
                    String typeCLiente = ProductoEnum.valueOf(req.getTypeCliente()).getValue();
                    ObjectId id = req.getId();
                    req = Activo.builder().build();
                    req.setId(id);
                    req.setCatalog("402");//isExist
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
