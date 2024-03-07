package com.pe.nttdata.services;

import com.pe.nttdata.repository.ActivoRepository;
import com.pe.nttdata.entity.Activo;
import com.pe.nttdata.util.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.rmi.CORBA.Util;
import java.util.Optional;

/**
 *Implement ActivoService. <br/>
 *<b>Class</b>: {@link ActivoService}<br/>
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
public class ActivoService {

    /**
     * <>p</>
     * .
     * ActivoRepository activoRepository
     **/
    @Autowired
    private ActivoRepository activoRepository;

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Flux<Activo> findAll() {
        return activoRepository.findAll();
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id paramter id
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Mono<Activo> findById(final String id) {
        return activoRepository.findById(id);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param activo paramter activo entity
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Mono<Activo> save(final Activo activo) {
        return activoRepository.save(activo);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id Parameter id
     * @param activo Parameter activo
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public  Mono<Activo> update(final String id, final Activo activo) {
        return activoRepository.findById(id)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        activo.setId(new ObjectId(id));
                        return activoRepository.save(activo);
                    }
                    return Mono.empty();
                })
                .switchIfEmpty(
                        Mono.defer(() ->{
                            Activo actError = Activo.builder().build();
                            actError.setDescrip("The document was not found id document... "+id);
                            return Mono.just(actError);
                        }))
                .onErrorResume(error->{
                    Activo actError = Activo.builder().build();
                    actError.setDescrip("Error found...: "+error);
                    return Mono.just(actError);
                });
    }


    public  Mono<Activo> findByDNI(final String dni) {
        return Mono.from(activoRepository.findAll()
                        .filter(f->f.getPersona().getDni().equals(dni))
                        .map(m -> MapperUtils.mapper(Activo.class, m)))
                .switchIfEmpty(
                        Mono.defer(() ->{
                            Activo actError = Activo.builder().build();
                            actError.setDescrip("The document was not found... "+dni);
                            return Mono.just(actError);
                        }))
                .onErrorResume(error->{
                    Activo actError = Activo.builder().build();
                    actError.setDescrip("Error found...: "+error);
                    return Mono.just(actError);
                    });

    }


    public  Mono<Activo> findByRUC(final String ruc) {
        return Mono.from(activoRepository.findAll()
                        .filter(f->f.getEmpresa().getRuc().equals(ruc))
                        .map(m -> MapperUtils.mapper(Activo.class, m)))
                .switchIfEmpty(
                        Mono.defer(() ->{
                            Activo actError = Activo.builder().build();
                            actError.setDescrip("The document was not found... "+ruc);
                            return Mono.just(actError);
                        }))
                .onErrorResume(error->{
                    Activo actError = Activo.builder().build();
                    actError.setDescrip("Error found...: "+error);
                    return Mono.just(actError);
                });

    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id Parameter id
     * @return return all elements from Mono Void
     *
     **/
    public Mono<Void> deleteById(final String id) {
        return activoRepository.deleteById(id);
    }

}
